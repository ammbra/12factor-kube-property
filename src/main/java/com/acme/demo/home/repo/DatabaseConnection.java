package com.acme.demo.home.repo;

import java.net.URL;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

@ApplicationScoped
public class DatabaseConnection {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConnection.class);
    public static final String URL = "url";

    private Database cloudant;

    @Inject
    @ConfigProperty(name = "database", defaultValue = "property")
    String database;

    @Inject
    @ConfigProperty(name = "DATABASE_CREDENTIALS")
    String secret;

    public Database getCloudantDatabase() {
        try {
            JSONObject credentials = new JSONObject(secret);
            LOGGER.info("Connecting to cloudant at {}", credentials);
            final CloudantClient cloudantClient = ClientBuilder.url(new URL(credentials.get(URL).toString())).disableSSLAuthentication().build();
            cloudant = cloudantClient.database(database, true);
        } catch (Exception e) {
            LOGGER.error("Exception caught {}", e);
        }
        return cloudant;
    }

    public Database getCloudant() {
        return cloudant;
    }
}
