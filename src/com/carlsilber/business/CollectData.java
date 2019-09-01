package com.carlsilber.business;

//Collecting data with streams

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class CollectData {

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
    public void transactionToList() {
        List<Transaction> transactionsSream = transactions.stream()
                .collect(Collectors.toList());

        transactionsSream.forEach(t -> System.out.println(t.getValue()));    // 300 1000 400 710 700 950
    }

    @Test
    public void countHowManyTraders() {
        long howManyTraders = transactions.stream()
                .map(t -> t.getTrader())
                .distinct()
                //.collect(Collectors.counting());
                .count();   // far more directly

        System.out.println(howManyTraders); // 4
    }

    @Test
    public void findMaxAndMinValue() {
        Comparator<Transaction> transactionsValuesComparator = Comparator.comparingInt(Transaction::getValue);
        Optional<Transaction> minValueTransaction = transactions.stream()
                .collect(minBy(transactionsValuesComparator));
        Optional<Transaction> maxValueTransaction = transactions.stream()
                .collect(maxBy(transactionsValuesComparator));

        System.out.println(minValueTransaction);    // Optional[{Trader:Brian in Cambridge, year: 2011, value:300}]
        System.out.println(maxValueTransaction);    // Optional[{Trader:Raoul in Cambridge, year: 2012, value:1000}]
    }

    @Test
    public void sumValues() {
        int totalValues = transactions.stream().collect(summingInt(Transaction::getValue));

        System.out.println(totalValues);    // 4060
    }

    @Test
    public void averageTransactions() {
        double avgTransactions = transactions.stream().collect(averagingInt(Transaction::getValue));

        System.out.println(avgTransactions);    // 676.6666666666666
    }

    @Test
    public void summaryStatistics() {
        IntSummaryStatistics transactionStatistics = transactions.stream().collect(summarizingInt(Transaction::getValue));

        System.out.println(transactionStatistics);  // IntSummaryStatistics{count=6, sum=4060, min=300, average=676,666667, max=1000}
    }

}
