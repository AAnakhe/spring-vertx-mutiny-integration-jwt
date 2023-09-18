package com.codingplayground.springvertx.handler;

import io.smallrye.jwt.build.Jwt;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class JwtBuilderHandler {

    public String buildToken(String subject) {
        return Jwt.issuer("aspacelife-technology")
                .subject(subject)
                .audience("aspace-tech")
                .groups("admin")
                .expiresIn(Duration.ofDays(3))
                .sign();
    }
}
