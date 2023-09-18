package com.codingplayground.springvertx.controller;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.mutiny.core.http.HttpServerResponse;
import io.vertx.mutiny.ext.web.Router;
import io.vertx.mutiny.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.function.Consumer;

@Controller
public class HelloController implements Consumer<RoutingContext> {

    @Autowired
    public HelloController(Router router) {
        router.get("/").handler(this);
    }

    @Override
    public void accept(RoutingContext routingContext) {
        HttpServerResponse serverResponse = routingContext.response();
        serverResponse.setStatusCode(HttpResponseStatus.OK.code())
                .end("Vert.x successfully integrated with springboot").subscribeAsCompletionStage();
    }

}