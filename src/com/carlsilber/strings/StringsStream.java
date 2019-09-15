package com.carlsilber.strings;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class StringsStream {

    List<String> words = Arrays.asList("Modern", "Java", "In", "Action");

    @Test
    public void getWordsLength() {
        List<Integer> wordLengths = words.stream()
                .map(String::length)
                .collect(toList());

        System.out.println(wordLengths);    // [6, 4, 2, 6]
    }

    @Test
    public void findUniqueCharacters() {
        List<String> uniqueCharacters =
                words.stream()
                        .map(word -> word.split(""))
                        .flatMap(Arrays::stream)
                        .distinct()
                        .collect(toList());

        System.out.println(uniqueCharacters);   //[M, o, d, e, r, n, J, a, v, I, A, c, t, i]
    }

    @Test
    public void findWordsThatStartsWithGivenChar() {
        words.stream()
                .filter(s -> s.startsWith("J"))
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::println);
    }

    @Test
    public void nullableStream() {
        Stream<String> values = Stream.of("config", "home", "user")
                .flatMap(key -> Stream.ofNullable(System.getProperty(key)));    //Nullable since Java 9
        System.out.println(values.toArray().length);
    }

    @Test
    public void removeDuplicateWords(){
        String s = "alpha gamma beta gamma delta";
        s = Arrays.stream( s.split("\\s+")).distinct().collect(joining(" ") );
        System.out.println(s);  //alpha gamma beta delta
    }

    public static String removeDuplicateWords2(String s) {
        return String.join(" ", new LinkedHashSet<>(Arrays.asList(s.split(" "))));
    }

    public static String removeDuplicateWords3(String s){
        return Arrays.stream(s.split(" ")).distinct().collect(joining(" "));
    }

    @Test
    public void abbreviateName() {
        String name = "Tom Jones";
        name = Arrays.stream(name.split(" "))
                .map(String::toUpperCase)
                .map(word -> word.substring(0, 1))
                .collect(joining("."));

        System.out.println(name);   //T.J
    }


}
