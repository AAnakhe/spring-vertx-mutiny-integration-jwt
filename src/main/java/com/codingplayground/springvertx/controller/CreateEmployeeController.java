package com.codingplayground.springvertx.controller;

import com.codingplayground.springvertx.model.Employee;
import com.codingplayground.springvertx.model.dal.DalEmployee;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.ext.web.Router;
import io.vertx.mutiny.ext.web.RoutingContext;
import io.vertx.mutiny.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.function.Function;

@Controller
public class CreateEmployeeController implements Function<RoutingContext, Uni<Void>> {

    private static final String CONTENT = "content-type";
    private static final String JSON_TYPE = "application/json";
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateEmployeeController.class);

    private final DalEmployee dalEmployee;

    @Autowired
    public CreateEmployeeController(Router router, DalEmployee dalEmployee) {
        this.dalEmployee = dalEmployee;
        router.post("/addEmployee")
                .handler(BodyHandler.create())
                .respond(this);
    }

    @Override
    public Uni<Void> apply(RoutingContext ctx) {
        Employee emp = ctx.body().asPojo(Employee.class);
        return dalEmployee.add(emp)
                .flatMap(employee -> {
                    if (employee == null) {
                        return ctx.response().setStatusCode(400)
                                .putHeader(CONTENT, JSON_TYPE)
                                .end(new JsonObject().put("message", "Error: Unable to insert record!")
                                        .encode());
                    } else {
                        return ctx.response()
                                .putHeader(CONTENT, JSON_TYPE)
                                .end(JsonObject.mapFrom(emp)
                                        .put("message", "Inserted Successful").encode());
                    }
                });
    }

}
