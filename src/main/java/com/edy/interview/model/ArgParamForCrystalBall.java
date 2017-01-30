package com.edy.interview.model;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

public class ArgParamForCrystalBall {

    private Object args;
    private int year;
    private int month;

    /**
     * Class to represent request to prediction end point
     * @param args, argument payload
     * @param year, year to predict
     * @param month, month to predict
     */
    @JsonCreator
    public ArgParamForCrystalBall(@JsonProperty("args") Object args, @JsonProperty("year") int year, @JsonProperty("month") int month){
        this.args = args;
        this.year = year;
        this.month = month;
    }

    public Object getArgs() {
        return args;
    }

    public void setArgs(Object args) {
        this.args = args;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
