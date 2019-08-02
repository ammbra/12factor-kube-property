package com.acme.demo.home.web;

import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.demo.home.model.Property;
import com.acme.demo.home.repo.DatabaseConnection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Ana Maria
 */
@Path("property")
@RequestScoped
public class PropertyEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyEndpoint.class);

    public static final String CLOUDANT_DATABASE_CANNOT_BE_FOUND = "The cloudant database cannot be found. ";
    public static final String CLOUDANT_SERVICE_IS_DOWN = "Cloudant Service is down.";

    public static final String RETRIEVED_SUCCESSFULLY = "The data about a property has been retrieved successfully.";
    public static final String PROPERTIES_HAVE_BEEN_RETRIEVED_SUCCESSFULLY = "The data about properties has been retrieved successfully.";
    public static final String SELECTOR_ADDRESS_IS_NOT_NULL = " \"selector\": { \"address\": {\"$ne\": \"null\"} }";

    @Inject
    private DatabaseConnection databaseConnection;

    @Timeout(value = 2, unit = ChronoUnit.SECONDS)
    @Retry(maxRetries = 2, maxDuration = 2000)
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(responseCode = "404", description = CLOUDANT_DATABASE_CANNOT_BE_FOUND, content = @Content(mediaType = MediaType.TEXT_PLAIN)),
            @APIResponse(responseCode = "200", description = RETRIEVED_SUCCESSFULLY, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Property.class)))})
    public Response getProperty(@PathParam("id") String id) throws Exception {
        Property property = databaseConnection.getCloudant().find(Property.class, id);
        Response response = Response.ok(property).build();
        return response;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProperty(Object property) throws Exception {
        int status = databaseConnection.getCloudant().update(property).getStatusCode();
        return Response.ok(status).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProperty(Object property) throws Exception {
        int status = databaseConnection.getCloudant().post(property).getStatusCode();
        return Response.ok(status).build();
    }

    @DELETE
    @Path("{id}/{rev}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProperty(@PathParam("id") String id, @PathParam("rev") String rev) throws Exception {
        int status = databaseConnection.getCloudant().remove(id, rev).getStatusCode();
        return Response.ok(status).build();
    }

    @Timeout(value = 5, unit = ChronoUnit.SECONDS)
    @Retry(maxRetries = 5, maxDuration = 2000)
    @Fallback(fallbackMethod = "fallbackService")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = PROPERTIES_HAVE_BEEN_RETRIEVED_SUCCESSFULLY, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Property.class)))})
    public Response getAllProperties() throws Exception {
        List<Property> properties = databaseConnection.getCloudant()
                .findByIndex(SELECTOR_ADDRESS_IS_NOT_NULL, Property.class);

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        try {
            return Response.ok().entity(gson.toJson(properties)).build();
        } catch (Exception e) {
            LOGGER.error("Error while retriewing properties {}",
                    javax.ws.rs.core.Response.status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR)
                            .entity(e.getStackTrace().toString()).build());
            throw new Exception(e);
        }
    }

    public javax.ws.rs.core.Response fallbackService() {
        return javax.ws.rs.core.Response.status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR)
                .entity(CLOUDANT_SERVICE_IS_DOWN).build();
    }

}
