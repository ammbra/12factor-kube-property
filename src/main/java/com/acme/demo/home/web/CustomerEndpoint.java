package com.acme.demo.home.web;

import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.demo.home.model.Customer;
import com.acme.demo.home.repo.DatabaseConnection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Ana Maria
 */
@Path("customer")
@RequestScoped
public class CustomerEndpoint {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerEndpoint.class);

	public static final String CLOUDANT_DATABASE_CANNOT_BE_FOUND = "The cloudant database cannot be found. ";
	public static final String CLOUDANT_SERVICE_IS_DOWN = "Cloudant Service is down.";

	public static final String RETRIEVED_SUCCESSFULLY = "Customer Data has been retrieved successfully.";
	public static final String SELECTOR_STREET_ADDRESS_IS_NOT_NULL = " \"selector\": { \"streetAddress\": {\"$ne\": \"null\"} }";

	@Inject
	private DatabaseConnection databaseConnection;

	@Timeout(value = 2, unit = ChronoUnit.SECONDS)
	@Retry(maxRetries = 2, maxDuration = 2000)
	@Fallback(fallbackMethod = "fallbackService")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@APIResponses(value = {
			@APIResponse(responseCode = "404", description = CLOUDANT_DATABASE_CANNOT_BE_FOUND, content = @Content(mediaType = MediaType.TEXT_PLAIN)),
			@APIResponse(responseCode = "200", description = RETRIEVED_SUCCESSFULLY, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Customer.class))) })
	public Response getAllOwners() throws Exception {
		List<Customer> customers = databaseConnection.getCloudantDatabase()
				.findByIndex(SELECTOR_STREET_ADDRESS_IS_NOT_NULL, Customer.class);

		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		try {
			return Response.ok().entity(gson.toJson(customers)).build();
		} catch (Exception e) {
			LOGGER.error("Error while retriewing customers {}",
					javax.ws.rs.core.Response.status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR)
							.entity(e.getStackTrace().toString()).build());
			throw new Exception(e);
		}
	}

	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public javax.ws.rs.core.Response fallbackService() {
		return javax.ws.rs.core.Response.status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR)
				.entity(CLOUDANT_SERVICE_IS_DOWN).build();
	}
}
