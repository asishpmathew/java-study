package com.example.java.optional;

import com.example.java.models.Employee;


import java.util.List;
import java.util.Optional;

/***
 * Reference : https://www.oracle.com/technical-resources/articles/java/java8-optional.html
 */
public class OptionalSample {

    static Employee[] arrayOfEmps = {
            new Employee(1, "Jeff Bezos", 100000.0),
            new Employee(2, "Bill Gates", 200000.0),
            new Employee(3, "Mark Zuckerberg", 300000.0)
    };

    public static void main(String args[]) {
        OptionalSample optionalSample = new OptionalSample();
        Optional<String> name = Optional.ofNullable(null);

        System.out.println(name.orElse("test"));

        //Case-2
        Optional<Employee> employee = Optional.of(new Employee(1, "Jeff Bezos", 100000.0));
        Employee findOne = employee.filter(x -> x.getId()==2).orElse(new Employee());
        System.out.println(findOne);

        //case -3 : orElse, orElseThrow, map, flatMap, filter
    }
}
