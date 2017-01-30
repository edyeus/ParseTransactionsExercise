package com.edy.interview.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class SpendAndIncome {

    private static final int CENTOCENTS=10000;
    private static final int CENTS=100;

    private long spend;
    private long income;

    /**
     * Class representing one set of spend and income
     */
    public SpendAndIncome(){
        spend = 0;
        income = 0;
    }

    public void addToSpend(long delta){
        spend+=delta;
    }

    public void addToIncome(long delta){
        income+=delta;
    }

    public long retrieveSpendInLong(){
        return spend;
    }

    public long retrieveIncomeInLong(){
        return income;
    }

    @JsonProperty("spent")
    public String getSpendInStr() {
        long cent = (spend%CENTOCENTS)/CENTS;
        String centStr = cent<10? "0"+String.valueOf(cent): String.valueOf(cent);

        return "$"+String.valueOf(spend/CENTOCENTS)+"."+centStr;
    }

    @JsonProperty("income")
    public String getIncomeInStr() {
        long cent = (spend%CENTOCENTS)/CENTS;
        String centStr = cent<10? "0"+String.valueOf(cent): String.valueOf(cent);

        return "$"+String.valueOf(income/CENTOCENTS)+"."+centStr;
    }
}
