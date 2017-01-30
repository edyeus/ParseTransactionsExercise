package com.edy.interview.report;

import com.edy.interview.model.DefaultUser;
import com.edy.interview.model.Transaction;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IgnoreCCPaymentTest {

    @Test
    public void notUsingOptionTest(){
        IgnoreCCPayment ignoreCCPayment = new IgnoreCCPayment();
        List<Transaction> dummyList = new ArrayList<>();

        List<Transaction> afterProcess = ignoreCCPayment.filter(dummyList, new String[]{"-abc"}, DefaultUser.getDefaultUser());

        assertThat(dummyList, is(afterProcess));
    }

    @Test
    public void lessThanTwoElementsInListTest(){
        IgnoreCCPayment ignoreCCPayment = new IgnoreCCPayment();
        List<Transaction> dummyList = new ArrayList<>();
        dummyList.add(new Transaction());

        List<Transaction> afterProcess = ignoreCCPayment.applyFilter(dummyList);

        assertThat(dummyList, is(afterProcess));
    }

    @Test
    public void sortListByTransactionTimeTest(){
        Transaction transaction1 = new Transaction();
        transaction1.setTransactionTime("2016-11-01T00:00:00.000Z");

        Transaction transaction2 = new Transaction();
        transaction2.setTransactionTime("2016-11-02T00:00:00.000Z");

        Transaction transaction3 = new Transaction();
        transaction3.setTransactionTime("2016-10-01T00:00:00.000Z");

        Transaction transaction4 = new Transaction();
        transaction4.setTransactionTime("2014-11-01T00:00:00.000Z");

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);
        transactions.add(transaction4);

        IgnoreCCPayment ignoreCCPayment = new IgnoreCCPayment();
        List<Transaction> newList = ignoreCCPayment.sortList(transactions);

        assertThat(newList.size(),is(4));
        assertThat(newList.get(0),is(transaction4));
        assertThat(newList.get(1),is(transaction3));
        assertThat(newList.get(2),is(transaction1));
        assertThat(newList.get(3),is(transaction2));
    }

    @Test
    public void findCCPaymentTest(){
        Transaction transaction1 = new Transaction();
        transaction1.setTransactionTime("2016-11-01T00:00:00.000Z");
        transaction1.setAmount(100);

        Transaction transaction2 = new Transaction();
        transaction2.setTransactionTime("2016-11-01T01:00:00.000Z");
        transaction2.setAmount(200);

        Transaction transaction3 = new Transaction();
        transaction3.setTransactionTime("2016-11-01T10:00:00.000Z");
        transaction3.setAmount(-100);

        Transaction transaction4 = new Transaction();
        transaction4.setTransactionTime("2016-11-03T00:00:00.000Z");
        transaction4.setAmount(-200);

        Transaction transaction5 = new Transaction();
        transaction5.setTransactionTime("2016-11-10T00:00:00.000Z");
        transaction5.setAmount(-200);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);
        transactions.add(transaction4);
        transactions.add(transaction5);

        List<Transaction> ccs = new ArrayList<>();

        IgnoreCCPayment ignoreCCPayment = new IgnoreCCPayment();
        ignoreCCPayment.findCCPayments(transactions, ccs);

        assertThat(ccs.size(),is(2));
        assertThat(ccs.get(0),is(transaction1));
        assertThat(ccs.get(1),is(transaction3));

        ignoreCCPayment.removeCCPayments(transactions, ccs);

        assertThat(transactions.size(),is(3));
        assertThat(transactions.get(0),is(transaction2));
        assertThat(transactions.get(1),is(transaction4));
        assertThat(transactions.get(2),is(transaction5));
    }
}
