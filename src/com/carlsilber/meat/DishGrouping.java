package com.carlsilber.meat;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

public class DishGrouping extends DishData {

    public enum CaloricLevel { DIET, NORMAL, FAT }

    @Test
    public void groupByType(){
        Map<Dish.Type, List<Dish>> dishesByType =
                menu.stream().collect(groupingBy(Dish::getType));

        //OTHER: [french fries, rice, season fruit, pizza] FISH: [prawns, salmon] MEAT: [pork, beef, chicken]
        dishesByType.forEach((type, dishes) -> System.out.println(type + ": " + dishes));
    }

    @Test
    public void getCaloricLevel() {

    Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = menu.stream().collect(
            groupingBy(dish -> {
                if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                else return CaloricLevel.FAT;
            } ));

        // NORMAL: [beef, french fries, pizza, salmon] DIET: [chicken, rice, season fruit, prawns] FAT: [pork]
        dishesByCaloricLevel.forEach((caloricLevel, dishes) -> System.out.println(caloricLevel + ": " + dishes));
    }

    @Test
    public void fileringCaloricDishesByType() {
        Map<Dish.Type, List<Dish>> caloricDishesByType =
                menu.stream()
                        .collect(groupingBy(Dish::getType,
                                filtering(dish -> dish.getCalories() > 500, toList())));

        // MEAT: [pork, beef] FISH: [] OTHER: [french fries, pizza]
        caloricDishesByType.forEach((type, dishes) -> System.out.println(type+ ": " + dishes));
    }
}
