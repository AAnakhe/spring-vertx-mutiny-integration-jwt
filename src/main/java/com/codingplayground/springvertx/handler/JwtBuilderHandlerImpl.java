package com.codingplayground.springvertx.handler;

import com.codingplayground.springvertx.service.JwtBuilderHandler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.mutiny.ext.auth.jwt.JWTAuth;
import io.vertx.mutiny.ext.web.Router;
import io.vertx.mutiny.ext.web.handler.JWTAuthHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class JwtBuilderHandlerImpl implements JwtBuilderHandler {

    private final JWTAuth jwtAuth;

    @Value("${smallrye.jwt.new-token.signature-algorithm}")
    private String algorithm;
    @Value("${smallrye.jwt.sign.key.location}")
    private String location;
    @Value("${smallrye.jwt.new-token.issuer}")
    private String issuer;


    @Autowired
    public JwtBuilderHandlerImpl(JWTAuth jwtAuth) {
        this.jwtAuth = jwtAuth;
    }

    @Override
    public String buildToken(String subject) {
       return jwtAuth.generateToken(new JsonObject()
                       .put("message", "token-data"),
               new JWTOptions()
                       .setHeader(new JsonObject().put("Authorization", "Bearer"))
                       .setSubject(subject)
                       .setIssuer(issuer)
                       .setAlgorithm(algorithm)
                       .setExpiresInMinutes(2840));

    }
}
