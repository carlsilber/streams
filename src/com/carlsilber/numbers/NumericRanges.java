package com.carlsilber.numbers;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NumericRanges {

    @Test
    public void countEvenNumbers() {
        IntStream evenNumbers = IntStream.rangeClosed(1, 100)
                .filter(n -> n % 2 == 0);

        evenNumbers.forEach(System.out::println); //2 4 6 , and so on
    }

    @Test
    public void pythagoreanTriple() {   // a * a + b * b = c * c
        Stream<int[]> pythagoreanTriples = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                        .mapToObj(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)})
                );

        pythagoreanTriples.limit(2).forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));
    }


    @Test
    public void pythagoreanTriple2() {   // a * a + b * b = c * c
        Stream<double[]> pythagoreanTriples2 = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .mapToObj(b -> new double[]{a, b, Math.sqrt(a * a + b * b)})
                        .filter(t -> t[2] % 1 == 0));

        pythagoreanTriples2.limit(3).forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));
    }

    @Test
    public void fibonacciTuple() { // (0,1) (1,1) (1,2) (2,3) (3,5) (5,8) (8,13) ...
        Stream.iterate(new int[]{0, 1},
                t -> new int[]{t[1], t[0] + t[1]})
                .limit(20)
                .forEach(t -> System.out.println("(" + t[0] + "," + t[1] + ")"));    //(0,1) (1,1) (1,2) (2,3) (3,5) (5,8) (8,13) ...
    }

    @Test
    public void fibonacci() { // 0, 1, 1, 2, 3, 5, 8, 13, 21, 34 . . .
        Stream.iterate(new int[]{0, 1},
                t -> new int[]{t[1], t[0] + t[1]})
                .limit(10)
                .map(t -> t[0])
                .forEach(System.out::println);   //0, 1, 1, 2, 3, 5, 8, 13, 21, 34
    }

    @Test
    public void iterateNumbers() {
        IntStream.iterate(0, n -> n < 100, n -> n + 4)
                .forEach(System.out::print);    //0 4 8 12 16 20 24 ...
    }

    @Test
    public void iterateNumbersUsingTakeWhile() {
        IntStream.iterate(0, n -> n + 4)
                .takeWhile(n -> n < 100)
                .forEach(System.out::print);    //0 4 8 12 16 20 24 ...
    }

    @Test
    public void generateRandomNumbers() {
        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);  //like: 0.7726721377794001 0.977695734765629 0.2107489564480738 0.7350041983765707 ...
    }
}
