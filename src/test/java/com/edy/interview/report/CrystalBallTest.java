package com.edy.interview.report;

import com.edy.interview.model.DefaultUser;
import com.edy.interview.model.Transaction;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class CrystalBallTest {
    @Test
    public void notUsingOptionTest(){
        CrystalBall crystalBall = new CrystalBall();
        List<Transaction> dummyList = new ArrayList<>();

        List<Transaction> afterProcess = crystalBall.filter(dummyList, new String[]{"-abc"}, DefaultUser.getDefaultUser());

        assertThat(dummyList, is(afterProcess));
    }

    @Test
    public void addProjectedTransactionsTest(){
        CrystalBall crystalBall = new CrystalBall();

        Transaction transaction1 = new Transaction();
        transaction1.setTransactionId("abc");

        Transaction transaction2 = new Transaction();
        transaction2.setTransactionId("efg");

        Transaction transaction3 = new Transaction();
        transaction3.setTransactionId("abc");

        Transaction transaction4 = new Transaction();
        transaction4.setTransactionId("hij");

        List<Transaction> list = new ArrayList<>();
        list.add(transaction1);
        list.add(transaction2);

        List<Transaction> projections = new ArrayList<>();
        projections.add(transaction3);
        projections.add(transaction4);

        List<Transaction> newList = crystalBall.addToNewList(list, projections);

        assertFalse(newList.equals(list));
        assertThat(newList.size(),is(3));
        assertThat(newList.get(0),is(transaction1));
        assertThat(newList.get(1),is(transaction2));
        assertThat(newList.get(2),is(transaction4));
    }

}
