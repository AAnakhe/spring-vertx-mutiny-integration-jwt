package com.codingplayground.springvertx.model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeData {

    private final List<Employee> employeeList = new ArrayList<>();

    public void add(Employee employee) {
        employeeList.add(employee);
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }



}
