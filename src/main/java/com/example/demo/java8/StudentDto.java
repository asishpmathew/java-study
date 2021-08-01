package com.example.demo.java8;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StudentDto implements Comparable<StudentDto>{
    private String name;
    private Integer mark;

    @Override
    public int compareTo(StudentDto o) {
        return this.getName().compareTo(o.getName());
    }
}
