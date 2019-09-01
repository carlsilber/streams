package com.carlsilber.meat;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DishStreams {

    public static void main(String[] args) {
        // System.out.println(threeHighCaloricDishNames);
    }

    private static List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH));

    @Test
    public void streamFiltering_thenLimiting() {
        List<String> threeHighCaloricDishNames = menu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .map(Dish::getName)
                .limit(3)
                .collect(toList());

        threeHighCaloricDishNames.forEach(s -> System.out.println(s));      // pork, beef, chicken
        threeHighCaloricDishNames.forEach(System.out::println);             // pork, beef, chicken

        assertEquals(3, threeHighCaloricDishNames.size());         // 3
    }

    @Test
    public void streamPrintingWhileFilteringAndLimiting() {
        List<String> threeHighCaloricDishNamesToPrint =
                menu.stream()
                        .filter(dish -> {
                            System.out.println("filtering:" + dish.getName());
                            return dish.getCalories() > 300;
                        })
                        .map(dish -> {
                            System.out.println("mapping:" + dish.getName());
                            return dish.getName();
                        })
                        .limit(3)
                        .collect(toList());

        assertEquals(3, threeHighCaloricDishNamesToPrint.size());
    }

    @Test
    public void streamdistinctAndCounting() {
        long count = menu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .distinct()
                .limit(3)
                .count();
        System.out.println(count);

        assertEquals(9, menu.size());
    }

    @Test
    public void streamAnyMatch() {
        if (menu.stream().anyMatch(Dish::isVegetarian)) {      //true
            System.out.println("The menu is (somewhat) vegetarian friendly!!");
        }
    }

    @Test
    public void streamAllMatch() {
        boolean isHealthy = menu.stream()
                .allMatch(dish -> dish.getCalories() < 1000); //check to see if all the elements of the stream match the given predicate

        System.out.println(isHealthy); //true
    }

    @Test
    public void streamNoneMatch() {
        boolean isHealthy = menu.stream()
                .noneMatch(d -> d.getCalories() >= 1000);   //ensures that no elements in the stream match the given predicate

        System.out.println(isHealthy);                      //true
    }

    @Test
    public void streamFindAny() {
        Optional<Dish> dish = menu.stream()
                .filter(Dish::isVegetarian)
                .findAny();                                 //returns an arbitrary element of the current stream
        System.out.println(dish);                           //Optional[french fries]
    }

    @Test
    public void streamOptionalExamples() {
        Optional<Dish> dish = menu.stream()
                .filter(Dish::isVegetarian)
                .findAny();
        // .ifPresent(System.out::println); //NOT WORKING YET !

    }

    @Test
    public void countDishes() {
        int count = menu.stream()
                .map(dish -> 1)
                .reduce(0, (a, b) -> a + b);

        System.out.println(count);  //9
        assertEquals(9, count);
    }

    @Test
    public void getTotalCaloriesUsingReducing() {
        int totalCalories = menu.stream().collect(reducing(
                0, Dish::getCalories, (i, j) -> i + j));    //first argument is the starting value of the reduction operation and will also be the value returned in the case of a stream with no elements

        System.out.println(totalCalories);  //4200
    }

    @Test
    public void getTotalCalories2() {
        int calories = menu.stream()
                .mapToInt(Dish::getCalories)
                .sum();

        System.out.println(calories);   //4200
        assertEquals(4200, calories);
    }

    @Test
    public void optionalMaxCalories() {
        OptionalInt maxCalories = menu.stream()
                .mapToInt(Dish::getCalories)
                .max();

        int max = maxCalories.orElse(1);                  //define a default value if there’s no maximum
        System.out.println(max);
        System.out.println(maxCalories);                        //OptionalInt[800]
        assertEquals(800, maxCalories.getAsInt());     //800
    }

    @Test
    public void optionalMaxCalorieDishUsingReducing() {
        Optional<Dish> mostCalorieDish = menu.stream()
                .collect(reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));

        System.out.println(mostCalorieDish);
    }

    @Test
    public void convertingBackToStreamOfObjects() {
        IntStream intStream = menu.stream().mapToInt(Dish::getCalories);    //Converts a Stream to a numeric stream
        Stream<Integer> stream = intStream.boxed();                         //Converts the numeric stream to a Stream
    }

    @Test
    public void joiningStrings() {
        String shortMenu = menu.stream().map(Dish::getName).collect(joining());
        String shortMenu2 = menu.stream().map(Dish::getName).collect(joining(", "));

        System.out.println(shortMenu);  //porkbeefchickenfrench friesriceseason fruitpizzaprawnssalmon
        System.out.println(shortMenu2); //pork, beef, chicken, french fries, rice, season fruit, pizza, prawns, salmon
    }

}
