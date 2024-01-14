package com.codingplayground.springvertx.service;


public interface JwtBuilderHandler {
    String buildToken(String subject);
}
