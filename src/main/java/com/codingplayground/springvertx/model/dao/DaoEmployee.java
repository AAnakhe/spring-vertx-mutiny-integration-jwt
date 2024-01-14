package com.codingplayground.springvertx.model.dao;

import com.codingplayground.springvertx.handler.PasswordHashingHandler;
import com.codingplayground.springvertx.model.Employee;
import com.codingplayground.springvertx.model.dal.DalEmployee;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Iterator;

@Repository
public class DaoEmployee implements DalEmployee {

    private static final Logger LOGGER = LoggerFactory.getLogger(DaoEmployee.class);

    private final PgPool pgPool;

    private final PasswordHashingHandler encode;


    @Autowired
    public DaoEmployee(PgPool pgPool, PasswordHashingHandler encode) {
        this.pgPool = pgPool;
        this.encode = encode;
    }

    @Override
    public Uni<Employee> add(Employee emp) {
        return pgPool.withConnection(conn -> conn.prepare("SELECT u.username FROM Employee u WHERE u.username = $1")
                .flatMap(preparedStatement -> preparedStatement
                        .query()
                        .execute(Tuple.of(emp.getUsername()))
                        .onFailure().invoke(Throwable::printStackTrace)
                        .flatMap(result -> {
                            if (result.iterator().hasNext()) {
                                return Uni.createFrom().failure(new RuntimeException("Username already exists"));
                            } else {
                                return conn.prepare("INSERT INTO Employee (name, username, password, department, salary) VALUES ($1, $2, $3, $4, $5) returning id")
                                        .flatMap(insertStatement -> insertStatement
                                                .query()
                                                .execute(Tuple.of(emp.getName(), emp.getUsername(), encode.hash(emp.getPassword()), emp.getDepartment(), emp.getSalary()))
                                                .onFailure()
                                                .invoke(Throwable::printStackTrace)
                                                .flatMap(rows -> {
                                                    if (rows == null || !rows.iterator().hasNext()) {
                                                        return Uni.createFrom().nullItem();
                                                    } else {
                                                        Row insertedRows = rows.iterator().next();
                                                        Long id = insertedRows.getLong("id");
                                                        emp.setId(id);
                                                        return Uni.createFrom().item(emp);
                                                    }
                                                }));
                            }
                        })));
    }





    @Override
    public Uni<JsonObject> find(Long id) {
        return pgPool.withConnection(conn -> conn.prepare("SELECT * FROM Employee WHERE id=$1"))
                .flatMap(preparedStatement -> preparedStatement
                        .query()
                        .execute(Tuple.of(id)))
                .onFailure()
                .invoke(Throwable::printStackTrace)
                .flatMap(rows -> {
                    if (rows == null) {
                        return Uni.createFrom().nullItem();
                    }else {
                        Iterator<Row> rowIterator = rows.iterator();
                        if (rowIterator.hasNext()){
                            return Uni.createFrom().item(rowIterator.next().toJson());
                        } else {
                            return Uni.createFrom().nullItem();
                        }
                    }
                });
    }

    @Override
    public Uni<JsonObject> findByUsername(String username) {
        return pgPool.withConnection(conn -> conn.prepare("SELECT * FROM Employee WHERE username = $1"))
                .flatMap(preparedStatement -> preparedStatement
                        .query()
                        .execute(Tuple.of(username)))
                .onFailure().invoke(Throwable::printStackTrace)
                .flatMap(rows -> {
                    if (rows == null) {
                        return Uni.createFrom().nullItem();
                    } else {
                        Iterator<Row> rowIterator = rows.iterator();
                        if (rowIterator.hasNext()){
                            return Uni.createFrom().item(rowIterator.next().toJson());
                        } else {
                          return Uni.createFrom().nullItem();
                        }
                    }
                });

    }


    @Override
    public Uni<JsonArray> findAll() {
      return   pgPool.withConnection(conn -> conn.prepare("SELECT * FROM Employee"))
                .flatMap(preparedStatement -> preparedStatement
                        .query()
                        .execute()
                        .flatMap(rows -> {
                            JsonArray data = new JsonArray();
                            for (Row row : rows) {
                                data.add(row.toJson());
                            }
                            return Uni.createFrom().item(data);

                        }).onFailure()
                        .invoke(err -> LOGGER.error("Error occurred while adding a new Employee", err)));
    }

}
