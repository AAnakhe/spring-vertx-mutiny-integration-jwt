package com.codingplayground.springvertx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor public class LoginData {
    private String username;
    private String password;
}
