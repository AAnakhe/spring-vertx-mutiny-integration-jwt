package com.codingplayground.springvertx.controller;

import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.ext.web.Router;
import io.vertx.mutiny.ext.web.RoutingContext;
import io.vertx.mutiny.pgclient.PgPool;
import org.springframework.stereotype.Controller;

import java.util.function.Consumer;

@Controller
public class ListAllEmployeeController implements Consumer<RoutingContext> {
    private final PgPool pgPool;

    public ListAllEmployeeController(Router router, PgPool pgPool) {
        this.pgPool = pgPool;
        router.get("/list_all_employee").handler(this);
    }

    @Override
    public void accept(RoutingContext ctx) {

      var pool =  pgPool.withConnection(conn -> conn.prepare("SELECT * FROM employee"))
                .flatMap(preparedStatement -> preparedStatement.query()
                        .execute());

        ctx.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json")
                .end(new JsonObject().put("employees", pool).encode())
                .subscribeAsCompletionStage();
    }

}
