package com.example.demo.java8;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LambdaSample {
    public static List<StudentDto> lambdaSample() {
        StudentDto s1 = new StudentDto("asish", 1000);
        StudentDto s2 = new StudentDto("test", 10);
        List<StudentDto> students = Arrays.asList(s1, s2);


        Collections.sort(students, (o1, o2)->{
            if(o1.getMark() == o2.getMark())
                return 0;
            else if(o1.getMark() < o2.getMark())
                return 1;
            else
                return -1;
        });
        return students;
    }
}
