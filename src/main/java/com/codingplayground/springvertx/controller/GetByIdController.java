package com.codingplayground.springvertx.controller;

import com.codingplayground.springvertx.model.dal.DalEmployee;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.ext.web.Router;
import io.vertx.mutiny.ext.web.RoutingContext;
import org.springframework.stereotype.Controller;

import java.util.function.Function;

@Controller
public class GetByIdController implements Function<RoutingContext, Uni<Void>> {

    private static final String CONTENT = "content-type";
    private static final String JSON_TYPE = "application/json";
    private final DalEmployee dalEmployee;

    public GetByIdController(Router router, DalEmployee dalEmployee) {
        this.dalEmployee = dalEmployee;
        router.get("/api/getById/:id").respond(this);
    }

    @Override
    public Uni<Void> apply(RoutingContext ctx) {
        long id = toLong(ctx.pathParam("id"));
     return   dalEmployee.find(id).flatMap(employee -> {
           if (employee == null){
               return ctx.response().setStatusCode(400)
                       .putHeader(CONTENT, JSON_TYPE)
                       .end(new JsonObject().put("message", "Employee with id not found!")
                               .encode());
           }else {
             return   ctx.response()
                       .setStatusCode(200)
                       .putHeader(CONTENT, JSON_TYPE)
                       .end(new JsonObject().put("employee", employee)
                               .encode());
           }
       });


    }

    private Long toLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (Exception e) {
            return 0L;
        }
    }

}
