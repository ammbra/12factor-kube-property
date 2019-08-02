package com.acme.demo.home.web;


import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ana Maria
 */
@Health
@WebListener
@ApplicationScoped
public class HealthEndpoint implements HealthCheck, ServletContextListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyEndpoint.class);

    static boolean healthy = true;

    /*
     *  If non-zero, this is how many seconds before we start reporting "DOWN"
     */
    @Inject
    @ConfigProperty(name = "lifetime", defaultValue = "0")
    private int lifetime;

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponse hcr;

        if (healthy) {
            hcr = HealthCheckResponse.named("property")
                    .withData("lifetime", lifetime)
                    .up().build();
        } else {
            hcr = HealthCheckResponse.named("property")
                    .withData("lifetime", lifetime)
                    .down().build();
        }
        LOGGER.debug("Health endpoint called: {}", hcr);
        return hcr;
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
    }

    @Override
    /* Set a timer to go unhealthy after lifetime seconds */
    public void contextInitialized(ServletContextEvent arg0) {
        if (lifetime > 0) {
            Date timeToRun = new Date(System.currentTimeMillis() + lifetime * 1000);
            Timer timer = new Timer();

            timer.schedule(new TimerTask() {
                public void run() {
                    healthy = false;
                }
            }, timeToRun);
        }
    }
}

