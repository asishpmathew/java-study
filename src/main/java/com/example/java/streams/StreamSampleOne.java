package com.example.java.streams;

import com.example.java.models.Employee;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamSampleOne {

    private Employee[] arrayOfEmps = {
            new Employee(1, "Jeff Bezos", 100000.0),
            new Employee(2, "Bill Gates", 200000.0),
            new Employee(3, "Mark Zuckerberg", 300000.0)
    };

    public static void main(String args[]) {
        StreamSampleOne streamSampleOne = new StreamSampleOne();
        //streamSampleOne.SampleOne();
        streamSampleOne.SampleTwo();
    }

    private void SampleOne() {
        Stream<Employee> employeeStream = Stream.of(arrayOfEmps);
        employeeStream.forEach(System.out::println);
    }

    private void SampleTwo() {
        Integer [] ids = {2,3};

        List<Optional<Employee>>  emps = Stream.of(ids).flatMap(x-> Arrays.stream(arrayOfEmps).map(y-> y.findById(x))).filter(x-> x.isPresent()).collect(Collectors.toList());

        emps.stream().forEach(System.out::println);


    }

}
