package com.codingplayground.springvertx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringVertxApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication springApplication = new SpringApplication(SpringVertxApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.setAdditionalProfiles("prod");
        springApplication.run(args);
    }

}
