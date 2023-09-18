package com.codingplayground.springvertx.controller;

import com.codingplayground.springvertx.model.Employee;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.ext.web.Router;
import io.vertx.mutiny.ext.web.RoutingContext;
import io.vertx.mutiny.ext.web.handler.BodyHandler;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;



import org.springframework.stereotype.Controller;

import java.util.function.Consumer;

@Controller
public class CreateEmployeeController implements Consumer<RoutingContext> {

    private final PgPool pgPool;

    public CreateEmployeeController(Router router, PgPool pgPool) {
        this.pgPool = pgPool;
        router.post("/addemployee")

                .handler(BodyHandler.create())
                .handler(this);
    }

    @Override
    public void accept(RoutingContext ctx) {
        JsonObject jsonObject = ctx.body().asJsonObject();
        Employee emp = jsonObject.mapTo(Employee.class);


        pgPool.withConnection(conn -> conn.prepare("INSERT INTO Employee (name, department, salary) VALUES ($1, $2, $3) return Id "))
                .flatMap(preparedStatement -> preparedStatement
                        .query()
                        .execute(Tuple.of(emp.getName(), emp.getDepartment(), emp.getSalary()))
                        .map(RowSet::iterator)
                        .flatMap(rowRowIterator -> {
                            if (rowRowIterator.hasNext()) {
                                Row row = rowRowIterator.next();
                                return Uni.createFrom().item(new JsonObject().put("emp", row));
                            } else {
                                return Uni.createFrom().failure(new RuntimeException("Error: Unable to insert record!"));
                            }
                        }));

   /* Uni.createFrom().completionStage( pgPool.getDelegate().preparedQuery("INSERT INTO users (first_name, last_name) VALUES ($1, $2)")
                .execute(Tuple.of("Julien", "Viet"))
                .onComplete(ar -> {
                    if (ar.succeeded()) {
                        RowSet<Row> rows = ar.result();
                        System.out.println(rows.rowCount());
                    } else {
                        System.out.println("Failure: " + ar.cause().getMessage());
                    }
                }).toCompletionStage()); */



        ctx.response()
                .putHeader("content-type", "application/json")
                .end(jsonObject.encode()).subscribeAsCompletionStage();
    }
}
