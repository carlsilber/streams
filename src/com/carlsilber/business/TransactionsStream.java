package com.carlsilber.business;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionsStream {

    Trader raoul = new Trader("Raoul", "Cambridge");
    Trader mario = new Trader("Mario", "Milan");
    Trader alan = new Trader("Alan", "Cambridge");
    Trader brian = new Trader("Brian", "Cambridge");

    List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
    );

    @Test
    public void findAllTransactionsByYearAndSort() {
        List<Transaction> tr2011 = transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(comparing(Transaction::getValue))
                .collect(toList());
        System.out.println(tr2011);

        assertEquals(2, tr2011.size());
    }

    @Test
    public void uniqueCitiesToList() {
        List<String> getCities = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(toList());

        System.out.println(getCities);  //[Cambridge, Milan]
        assertEquals(2, getCities.size());
    }

    @Test
    public void uniqueCitiesToSet() {
        Set<String> getCities = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .collect(toSet());

        System.out.println(getCities);  //[Cambridge, Milan]
        assertEquals(2, getCities.size());
    }

    @Test
    public void findAllTradersFromCityAndSortAlphabetically() {
        List<Trader> getTraders = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .distinct()
                .sorted(comparing(Trader::getName))
                .collect(toList());

        System.out.println(getTraders);
        assertEquals(3, getTraders.size());
        assertEquals("Alan", getTraders.get(0).getName());
    }

    @Test
    public void stringOfAllTradersByNameAndSortAlphabetically() {
        String traderStr = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                //  .reduce("", (n1, n2) -> n1 + n2);   // AlanBrianMarioRaoul
                .collect(joining());

        System.out.println(traderStr);
    }

    @Test
    public void isAnyTradersFromGivenCity() {
        boolean milanBased = transactions.stream()
                .anyMatch(transaction -> transaction.getTrader()
                        .getCity()
                        .equals("Milan"));

        System.out.println(milanBased); //true
        assertTrue(milanBased);         //true
    }

    @Test
    public void printAllTransactionsValuesFromTradersFromCity() {
        transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .forEach(System.out::println);  //300 1000 400 950
    }

    @Test
    public void highestValueOfTransactions() {
        Optional<Integer> highestValue = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);

        System.out.println(highestValue);   //Optional[1000]
    }

    @Test
    public void findTransactionWithSmallestValue() {
        Optional<Transaction> smallestTransaction = transactions.stream()
         //     .reduce((t1, t2) -> t1.getValue() < t2.getValue() ? t1 : t2);
                .min(comparing(Transaction::getValue));     //better solution
        System.out.println(smallestTransaction); //Optional[{Trader:Brian in Cambridge, year: 2011, value:300}]
    }
}
