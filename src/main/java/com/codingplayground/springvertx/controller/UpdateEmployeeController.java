//package com.codingplayground.springvertx.controller;
//
//
//import com.codingplayground.springvertx.model.Employee;
//import com.codingplayground.springvertx.model.EmployeeData;
//import io.smallrye.mutiny.Uni;
//import io.vertx.core.json.JsonObject;
//import io.vertx.mutiny.ext.web.Router;
//import io.vertx.mutiny.ext.web.RoutingContext;
//import io.vertx.mutiny.ext.web.handler.BodyHandler;
//import org.springframework.stereotype.Controller;
//
//import java.util.function.Consumer;
//
//@Controller
//public class AddEmployeeController implements Consumer<RoutingContext> {
//
//    private final EmployeeData employeeData;
//
//    public AddEmployeeController(Router router, EmployeeData employeeData) {
//        this.employeeData = employeeData;
//        router.post("/addemployee")
//                .handler(BodyHandler.create())
//                .handler(this);
//    }
//
//    @Override
//    public void accept(RoutingContext ctx) {
//        JsonObject jsonObject = ctx.body().asJsonObject();
//        Employee employee = jsonObject.mapTo(Employee.class);
//        employeeData.add(employee);
//        ctx.response()
//                .putHeader("content-type", "application/json")
//                .end(jsonObject.encode()).subscribeAsCompletionStage();
//    }
//
//
//}
