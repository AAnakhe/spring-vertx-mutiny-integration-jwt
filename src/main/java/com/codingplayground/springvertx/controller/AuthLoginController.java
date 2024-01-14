package com.codingplayground.springvertx.controller;

import com.codingplayground.springvertx.handler.PasswordHashingHandler;
import com.codingplayground.springvertx.model.LoginData;
import com.codingplayground.springvertx.model.dal.DalEmployee;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.ext.auth.authentication.AuthenticationProvider;
import io.vertx.mutiny.ext.web.Router;
import io.vertx.mutiny.ext.web.RoutingContext;
import io.vertx.mutiny.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.function.Function;

@Controller
public class AuthLoginController  implements Function<RoutingContext, Uni<Void>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateEmployeeController.class);
    private final PasswordHashingHandler encode;
    private static final String CONTENT = "content-type";
    private static final String JSON_TYPE = "application/json";
    private final DalEmployee dalEmployee;

    private final AuthenticationProvider authprovider;



    public AuthLoginController(Router router, PasswordHashingHandler encode, DalEmployee dalEmployee, AuthenticationProvider authprovider) {
        this.encode = encode;
        this.dalEmployee = dalEmployee;
        this.authprovider = authprovider;

        router.post("/auth_login")
                .handler(BodyHandler.create())
                .respond(this);
    }


    @Override
    public Uni<Void> apply(RoutingContext ctx) {

        LoginData loginData = ctx.body().asPojo(LoginData.class);
        String username = ctx.pathParam("username");

        return null;


    }
}
