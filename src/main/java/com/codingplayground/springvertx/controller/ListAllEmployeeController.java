package com.codingplayground.springvertx.controller;

import com.codingplayground.springvertx.model.dal.DalEmployee;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.ext.web.Router;
import io.vertx.mutiny.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.function.Function;

@Controller
public class ListAllEmployeeController implements Function<RoutingContext, Uni<Void>> {

    private static final String CONTENT = "content-type";
    private static final String JSON_TYPE = "application/json";
    private final DalEmployee dalEmployee;

    public ListAllEmployeeController(Router router, DalEmployee dalEmployee) {
        this.dalEmployee = dalEmployee;
        router.get("/list_all_employee").respond(this);
    }

    @Override
    public Uni<Void> apply(RoutingContext ctx) {
        return dalEmployee.findAll()
                .flatMap(employees -> ctx.response()
                        .putHeader(CONTENT, JSON_TYPE)
                        .end(new JsonObject().put("employees", employees)
                                .encode()));
    }
}
