package com.example.java;

import java.util.function.Predicate;

public class PredicateSample {
    public static void predicateSample() {
        Predicate<Integer> testPre = n-> n>10;
        System.out.println(testPre.test(213));

        Predicate<Integer> predicate1 = i -> i > 100;
        Predicate<Integer> predicate2 = i -> i < 300;

        Predicate<Integer> andPredicate = predicate1.or(predicate2);
        boolean rangeCheck = andPredicate.test(200);

        System.out.println(rangeCheck);

    }
}
