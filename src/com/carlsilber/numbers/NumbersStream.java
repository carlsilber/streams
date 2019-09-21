package com.carlsilber.numbers;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumbersStream {

    List<Integer> numbers1 = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
    List<Integer> numbers2 = Arrays.asList(2, 3, 5, 7);

    int[] intArray1 = new int[] {1,2,3};
    int[] intArray2 = new int[] {4,5,6,7,8};

    @Test
    public void distinctNumbers() {
        numbers1.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .forEach(System.out::println);

        assertEquals(7, numbers1.size());
    }

    @Test   //partitioningBy
    public void streamPartitionBy_thenGetMap() {
        Map<Boolean, List<Integer>> isEven = numbers1.stream().collect(
                Collectors.partitioningBy(i -> i % 2 == 0));

        assertEquals(isEven.get(true).size(), 3);   //3
        assertEquals(isEven.get(false).size(), 4);  //4
    }

    @Test
    public void squareOfNumbers() {
        List<Integer> squares =
                numbers1.stream()
                        .map(n -> n * n)
                        .collect(toList());
        System.out.println(squares);

        assertEquals(7, squares.size());
        assertEquals(Arrays.asList(1, 4, 1, 9, 9, 4, 16), squares);
    }

    @Test
    public void pairsFromTwoLists(){
        List<int[]> pairs = numbers1.stream()
                .flatMap(i-> numbers2.stream()
                .map(j-> new int[]{i, j}))
                .collect(toList());

      for (int[] pair : pairs) {
            System.out.println(Arrays.deepToString(new int[][]{pair}));
        }
      //OR
        for (int[] pair : pairs) {
            System.out.println(Arrays.toString(pair));
        }
    }

    @Test
    public void pairsFromTwoListsSumIsDivisibleByThree(){
        List<int[]> pairs = numbers1.stream()
                .flatMap(i-> numbers2.stream()
                        .filter(j -> (i + j) % 3 == 0)
                        .map(j-> new int[]{i, j}))
                .collect(toList());

        for (int[] pair : pairs) {
            System.out.println(Arrays.deepToString(new int[][]{pair}));
        }
    }

    @Test
    public void streamFindAny() {
        Optional<Integer> anySquareDivisibleByTwo =
                numbers1.stream()
                        .map(n -> n * n)
                        .filter(n -> n % 2 == 0)
                        .findAny(); // 9
        System.out.println(anySquareDivisibleByTwo);
    }

    @Test
    public void streamFindFirst() {
        Optional<Integer> firstSquareDivisibleByThree =
                numbers1.stream()
                        .map(n -> n * n)
                        .filter(n -> n % 3 == 0)
                        .findFirst(); // 9
        System.out.println(firstSquareDivisibleByThree);
    }

    @Test
    public void summingAllElements() {
        int sum = numbers1.stream()
                .reduce(0, (a, b) -> a + b);

    //    int sum = numbers1.stream().reduce(0, Integer::sum);                  //Using method reference

    //    Optional<Integer> sum = numbers1.stream().reduce((a, b) -> (a + b));  //Optional variant

        System.out.println(sum);    //16
    }

    @Test
    public void findMaxValue() {
        Optional<Integer> max = numbers1.stream()
                .reduce(Integer::max);

        System.out.println(max);    //Optional[4]
    }

    @Test
    public void findMinValue() {
        Optional<Integer> min = numbers1.stream()
                .reduce(Integer::min);

        System.out.println(min);    //Optional[1]
    }


    public static int summingAllElementsOfTwoArrays(int[] arr1, int[] arr2){
        return Stream.of(arr1, arr2).flatMapToInt(Arrays::stream).sum();

    //  return concat(stream(arr1),stream(arr2)).sum();

    /*  return Stream.of(arr1, arr2)
                .flatMapToInt(Arrays::stream)
                .sum();     */

    /*  return concat(Arrays.stream(arr1), Arrays.stream(arr2))
                .reduce(0, (a, b) -> a + b);  */
    }

    public static char findMissingLetter(char[] array) {
        return IntStream.range(0, array.length - 1)
                .filter(i -> array[i + 1] != array[i] + 1)
                .mapToObj(i -> (char) (array[i] + 1))
                .findAny().orElseThrow(IllegalArgumentException::new);
    }



    @Test
    public void findMissingLetterTest() {
        assertEquals('e', findMissingLetter(new char[] { 'a','b','c','d','f' }));
        assertEquals('P', findMissingLetter(new char[] { 'O','Q','R','S' }));
    }
}
