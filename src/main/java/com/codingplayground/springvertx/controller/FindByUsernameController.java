package com.codingplayground.springvertx.controller;

import com.codingplayground.springvertx.model.dal.DalEmployee;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.ext.web.Router;
import io.vertx.mutiny.ext.web.RoutingContext;
import org.springframework.stereotype.Controller;

import java.util.function.Function;

@Controller
public class FindByUsernameController implements Function<RoutingContext, Uni<Void>> {

    private static final String CONTENT = "content-type";
    private static final String JSON_TYPE = "application/json";
   private final DalEmployee dalEmployee;

    public FindByUsernameController(Router router, DalEmployee dalEmployee) {
        this.dalEmployee = dalEmployee;
        router.get("/findByUsername/:username")
                .respond(this);
    }

    @Override
    public Uni<Void> apply(RoutingContext ctx) {
        String username = ctx.pathParam("username");

        return dalEmployee.findByUsername(username).flatMap(employee -> {
            if (employee == null) {
                return ctx.response().setStatusCode(200)
                        .putHeader(CONTENT, JSON_TYPE)
                        .end(new JsonObject().put("Oops!", "employee with username not found").encode());
            } else {
               return ctx.response().setStatusCode(200)
                        .putHeader(CONTENT, JSON_TYPE)
                        .end(new JsonObject().put("employee", employee).encode());
            }
        });
    }
}
