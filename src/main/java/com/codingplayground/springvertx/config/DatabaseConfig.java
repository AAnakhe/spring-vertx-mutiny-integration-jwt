package com.codingplayground.springvertx.config;


import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.sqlclient.PoolOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

    @Bean
    @Autowired
    public PgPool pgPool(Vertx vertx) {
        PgConnectOptions options = new PgConnectOptions()
                .setUser("postgres")
                .setPassword("password")
                .setDatabase("postgres")
                .setPort(5432);

        PoolOptions poolOptions = new PoolOptions()
                .setMaxSize(5);
        return PgPool.pool(vertx, options, poolOptions);
    }

}
