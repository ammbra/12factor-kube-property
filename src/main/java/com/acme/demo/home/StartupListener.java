package com.acme.demo.home;

import java.net.MalformedURLException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.demo.home.repo.DatabaseConnection;

/**
 * @author Ana Maria
 */
@ApplicationScoped
public class StartupListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(StartupListener.class);

    @Inject
    private DatabaseConnection databaseConnection;

    public void init(@Observes @Initialized(ApplicationScoped.class) ServletContext context)
            throws MalformedURLException {
        LOGGER.debug("Initializing the db connection");
        databaseConnection.getCloudantDatabase();
        LOGGER.debug("End initialization of the db connection {}", databaseConnection.getCloudant().info());

    }

    public void destroy(@Observes @Destroyed(ApplicationScoped.class) ServletContext context) {
        // Perform action during application's shutdown
    }
}