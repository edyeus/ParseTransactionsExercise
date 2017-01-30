package com.edy.interview.report;

import com.edy.interview.model.DefaultUser;
import com.edy.interview.model.Transaction;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class IgnoreDonutsTest {

    @Test
    public void notUsingOptionTest(){
        IgnoreDonuts ignoreDonuts = new IgnoreDonuts();
        List<Transaction> dummyList = new ArrayList<>();

        List<Transaction> afterProcess = ignoreDonuts.filter(dummyList, new String[]{"-abc"}, DefaultUser.getDefaultUser());

        assertThat(dummyList, is(afterProcess));
    }

    @Test
    public void isDonutTest(){
        IgnoreDonuts ignoreDonuts = new IgnoreDonuts();

        Transaction transaction1 = new Transaction();
        transaction1.setMerchant("DUNKIN #336784");
        assertTrue(ignoreDonuts.isDonut(transaction1));

        Transaction transaction2 = new Transaction();
        transaction2.setMerchant("Krispy Kreme Donuts");
        assertTrue(ignoreDonuts.isDonut(transaction2));

        Transaction transaction3 = new Transaction();
        transaction3.setMerchant("not donut");
        assertFalse(ignoreDonuts.isDonut(transaction3));
    }

    @Test
    public void applyFilterTest(){
        IgnoreDonuts ignoreDonuts = new IgnoreDonuts();

        Transaction transaction1 = new Transaction();
        transaction1.setMerchant("DUNKIN #336784");

        Transaction transaction2 = new Transaction();
        transaction2.setMerchant("Krispy Kreme Donuts");

        Transaction transaction3 = new Transaction();
        transaction3.setMerchant("not donut");

        List<Transaction> list = new ArrayList<>();
        list.add(transaction1);
        list.add(transaction2);
        list.add(transaction3);

        List<Transaction> newList = ignoreDonuts.applyFilter(list);

        assertFalse(newList.equals(list));
        assertThat(newList.size(),is(1));
        assertThat(newList.get(0),is(transaction3));
    }

    @Test
    public void runWithOptionTest(){
        IgnoreDonuts ignoreDonuts = new IgnoreDonuts();

        Transaction transaction1 = new Transaction();
        transaction1.setMerchant("DUNKIN #336784");

        Transaction transaction2 = new Transaction();
        transaction2.setMerchant("Krispy Kreme Donuts");

        Transaction transaction3 = new Transaction();
        transaction3.setMerchant("not donut");

        List<Transaction> list = new ArrayList<>();
        list.add(transaction1);
        list.add(transaction2);
        list.add(transaction3);

        List<Transaction> newList = ignoreDonuts.filter(list, new String[]{"-d", "-e"}, DefaultUser.getDefaultUser());

        assertFalse(newList.equals(list));
        assertThat(newList.size(),is(1));
        assertThat(newList.get(0),is(transaction3));
    }
}
