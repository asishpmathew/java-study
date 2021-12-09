package com.example.java.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private Integer id;
    private String name;
    private Double rank;

    public Optional<Employee> findById(Integer id) {
        Employee emp = null;
        if(this.id == id) {
            emp =  this;
        }
        return Optional.ofNullable(emp);
    }
}
