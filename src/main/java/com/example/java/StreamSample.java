package com.example.java;

import java.util.List;

public class StreamSample {

    public static void streamSample(List<StudentDto> students) {
        students.stream().map((s)-> s.getMark()).
                forEach(System.out::println);

        java.util.stream.Stream.generate(()->1+1).limit(5)
                .forEach(System.out::println);

        java.util.stream.Stream.iterate(100, n->n+1)
                .limit(100).forEach(System.out::println);
        ;


        java.util.stream.Stream<String> nameStream = java.util.stream.Stream.of("mohan","john","vaibhav","amit");
        java.util.stream.Stream<String> nameStartJ = nameStream.map(String::toUpperCase)
                .peek( e -> System.out.println(e))
                .filter(s -> s.startsWith("J"));
        System.out.println(nameStartJ.count());

        System.out.println("------");



        students.stream().sorted().forEach(System.out::println);



    }


}
