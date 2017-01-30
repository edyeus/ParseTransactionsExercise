package com.edy.interview.model;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SpendAndIncomeTest {

    private int income;
    private int spend;
    private int factor;

    @Before
    public void setup(){
        income = 10;
        spend = 20;
        factor = 10000;
    }

    @Test
    public void createJsonFromObjectTest() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String jsonInString = mapper.writeValueAsString(new SpendAndIncome());
        assertThat(jsonInString, is("{\"income\":\"$0.00\",\"spent\":\"$0.00\"}"));
    }

    @Test
    public void createJsonFromObjectWithIncomeAndSpendInCentoCentsTest() throws IOException {
        SpendAndIncome spendAndIncome = new SpendAndIncome();
        spendAndIncome.addToIncome(income);
        spendAndIncome.addToSpend(spend);

        ObjectMapper mapper = new ObjectMapper();

        String jsonInString = mapper.writeValueAsString(spendAndIncome);
        assertThat(jsonInString, is("{\"income\":\"$0.00\",\"spent\":\"$0.00\"}"));
    }

    @Test
    public void createJsonFromObjectWithIncomeAndSpendTest() throws IOException {
        SpendAndIncome spendAndIncome = new SpendAndIncome();
        spendAndIncome.addToIncome(income*factor);
        spendAndIncome.addToSpend(spend*factor);

        ObjectMapper mapper = new ObjectMapper();

        String jsonInString = mapper.writeValueAsString(spendAndIncome);
        assertThat(jsonInString, is("{\"income\":\"$"+income+".00\",\"spent\":\"$" + spend + ".00\"}"));
    }
}
