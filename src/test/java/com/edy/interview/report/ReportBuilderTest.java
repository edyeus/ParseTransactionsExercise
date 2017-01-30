package com.edy.interview.report;

import com.edy.interview.model.SpendAndIncome;
import com.edy.interview.model.Transaction;
import com.edy.interview.model.YearAndMonth;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ReportBuilderTest {

    @Test
    public void buildMapTest(){
        ReportBuilder builder = new ReportBuilder(Collections.emptyList());

        Transaction transaction1 = new Transaction();
        transaction1.setYearAndMonth("2016-11-01T00:00:00.000Z");
        transaction1.setAmount(10*10000);

        Transaction transaction2 = new Transaction();
        transaction2.setYearAndMonth("2016-11-01T00:00:00.000Z");
        transaction2.setAmount(10*10000);

        Transaction transaction3 = new Transaction();
        transaction3.setYearAndMonth("2016-11-01T00:00:00.000Z");
        transaction3.setAmount(-10*10000);

        Transaction transaction4 = new Transaction();
        transaction4.setYearAndMonth("2016-10-01T00:00:00.000Z");
        transaction4.setAmount(10*10000);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);
        transactions.add(transaction4);

        Map<YearAndMonth, SpendAndIncome> map = builder.buildMap(transactions);

        assertThat(map.size(), is(2));

        assertTrue(map.containsKey(new YearAndMonth("2016-11-01T00:00:00.000Z")));
        assertThat(map.get(new YearAndMonth("2016-11-01T00:00:00.000Z")).getIncomeInStr(), is("$20.00"));
        assertThat(map.get(new YearAndMonth("2016-11-01T00:00:00.000Z")).getSpendInStr(), is("$10.00"));

        assertTrue(map.containsKey(new YearAndMonth("2016-10-01T00:00:00.000Z")));
        assertThat(map.get(new YearAndMonth("2016-10-01T00:00:00.000Z")).getIncomeInStr(), is("$10.00"));
    }

    @Test
    public void generateResultTest(){
        ReportBuilder builder = new ReportBuilder(Collections.emptyList());

        Transaction transaction1 = new Transaction();
        transaction1.setYearAndMonth("2016-11-01T00:00:00.000Z");
        transaction1.setAmount(10*10000);

        Transaction transaction3 = new Transaction();
        transaction3.setYearAndMonth("2016-11-01T00:00:00.000Z");
        transaction3.setAmount(-10*10000);

        Transaction transaction4 = new Transaction();
        transaction4.setYearAndMonth("2016-10-01T00:00:00.000Z");
        transaction4.setAmount(10*10000);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction3);
        transactions.add(transaction4);

        String result = builder.generateResult(transactions);
        assertThat(result, is("{\"2016-10\":{\"income\":\"$10.00\",\"spent\":\"$0.00\"},\"2016-11\":{\"income\":\"$10.00\",\"spent\":\"$10.00\"},\"average\":{\"income\":\"$10.00\",\"spent\":\"$5.00\"}}"));
    }

}
