package com.carlsilber.employee;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeStreams {

    private static Employee[] arrayOfEmps = {
            new Employee(1, "Donald Trump", 100000.0),
            new Employee(2, "Bill Gates", 200000.0),
            new Employee(3, "Jeff Bezos", 300000.0)
    };

    private static List<Employee> empList = Arrays.asList(arrayOfEmps);

    @Test   //groupingBy
    public void streamGroupingBy_thenGetMap() {
        Map<Character, List<Employee>> groupByAlphabet = empList.stream().collect(
                Collectors.groupingBy(e -> e.getName().charAt(0)));

        // groupByAlphabet.forEach((character, employees) -> System.out.println(character.toString() + " : " + employees.get(0).getName()));
        // System.out.println(groupByAlphabet.get('B').get(0).getSalary());

        assertEquals(groupByAlphabet.get('B').get(0).getName(), "Bill Gates");
        assertEquals(groupByAlphabet.get('D').get(0).getName(), "Donald Trump");
        assertEquals(groupByAlphabet.get('J').get(0).getName(), "Jeff Bezos");
    }

    @Test //mapping
    public void streamMapping_thenGetMap() {
        Map<Character, List<Integer>> idGroupedByAlphabet = empList.stream().collect(
                Collectors.groupingBy(e -> e.getName().charAt(0),
                        Collectors.mapping(Employee::getId, Collectors.toList())));

        assertEquals(idGroupedByAlphabet.get('B').get(0), Integer.valueOf(2));
        assertEquals(idGroupedByAlphabet.get('D').get(0), Integer.valueOf(1));
        assertEquals(idGroupedByAlphabet.get('J').get(0), Integer.valueOf(3));
    }
}
