package com.codingplayground.springvertx.controller;

import com.codingplayground.springvertx.handler.JwtBuilderHandlerImpl;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.ext.web.Router;
import io.vertx.mutiny.ext.web.RoutingContext;

import org.springframework.stereotype.Controller;
import java.util.function.Function;

@Controller
public class AuthController implements Function<RoutingContext, Uni<Void>> {

    JwtBuilderHandlerImpl jwtBuilderHandler;


    public AuthController(Router router, JwtBuilderHandlerImpl jwtBuilderHandler) {
        this.jwtBuilderHandler = jwtBuilderHandler;
        router.get("/getJwt").respond(this);
    }

    @Override
    public Uni<Void> apply(RoutingContext ctx) {
        return ctx.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json")
                .end(new JsonObject().put("jwt", jwtBuilderHandler.buildToken("aspacelife-tech"))
                        .encode()).onFailure().invoke(Throwable::printStackTrace);
    }

}
