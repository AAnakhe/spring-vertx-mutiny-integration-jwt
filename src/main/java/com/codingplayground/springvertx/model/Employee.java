package com.codingplayground.springvertx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Employee {
    private Long id;
    private String name;
    private String username;
    private String password;
    private String department;
    private double salary;
}
