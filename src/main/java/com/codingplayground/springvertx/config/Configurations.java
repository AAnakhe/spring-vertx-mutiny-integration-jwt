package com.codingplayground.springvertx.config;

import io.vertx.core.spi.VerticleFactory;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.Router;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
public class Configurations {

    private final Logger LOGGER = Logger.getLogger(Configurations.class.getName());

    public Configurations() {
        LOGGER.log(Level.INFO, "Starting...");
    }

    @Bean
    public Vertx vertx(VerticleFactory verticleFactory) {
        Vertx vertx = Vertx.vertx();
        vertx.registerVerticleFactory(verticleFactory);
        return vertx;
    }

    @Bean
    @Autowired
    public Router router(Vertx vertx) {
        return Router.router(vertx);
    }

}
