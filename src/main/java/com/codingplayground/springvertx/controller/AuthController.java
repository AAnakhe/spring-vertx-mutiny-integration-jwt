package com.codingplayground.springvertx.controller;

import com.codingplayground.springvertx.handler.JwtBuilderHandler;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.ext.web.Router;
import io.vertx.mutiny.ext.web.RoutingContext;

import org.springframework.stereotype.Controller;

import java.util.function.Consumer;

@Controller
public class AuthController implements Consumer<RoutingContext> {

    JwtBuilderHandler jwtBuilderHandler;


    public AuthController(Router router, JwtBuilderHandler jwtBuilderHandler) {
        this.jwtBuilderHandler = jwtBuilderHandler;
        router.get("/getJwt").handler(this);
    }


    @Override
    public void accept(RoutingContext ctx) {

        ctx.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json")
                .end(new JsonObject().put("jwt", jwtBuilderHandler.buildToken("aspacelife-tech"))
                        .encode()).subscribeAsCompletionStage();
    }
}
