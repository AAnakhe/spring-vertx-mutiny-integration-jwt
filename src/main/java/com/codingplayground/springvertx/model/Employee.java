package com.codingplayground.springvertx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Employee {

    private UUID id;
    private String name;
    private String department;
    private double salary;
}
