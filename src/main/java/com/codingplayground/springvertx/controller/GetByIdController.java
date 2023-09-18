package com.codingplayground.springvertx.controller;

import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.ext.web.Router;
import io.vertx.mutiny.ext.web.RoutingContext;
import org.springframework.stereotype.Controller;

import java.util.function.Consumer;

@Controller
public class GetByIdController implements Consumer<RoutingContext> {


    public GetByIdController(Router router) {
        router.get("/getById/:id").handler(this);
    }

    @Override
    public void accept(RoutingContext ctx) {
        long id = toLong(ctx.pathParam("id"));
        ctx.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json")
                .end(new JsonObject().put("employee", "")
                        .encode()).subscribeAsCompletionStage();
    }

    private Long toLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (Exception e) {
            return 0L;
        }
    }
}
