package com.codingplayground.springvertx.model.dal;

import com.codingplayground.springvertx.model.Employee;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public interface DalEmployee {

    Uni<Employee> add(Employee emp);
    Uni<JsonArray> findAll();
    Uni<JsonObject> find(Long id);
    Uni<JsonObject> findByUsername(String username);

}
