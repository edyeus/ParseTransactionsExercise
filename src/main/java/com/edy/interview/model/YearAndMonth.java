package com.edy.interview.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public class YearAndMonth implements Comparable<YearAndMonth>{

    private static Logger logger = LoggerFactory.getLogger(YearAndMonth.class);
    private static final String REGEX = "(\\d\\d\\d\\d)(-)(\\d\\d)(-)(.*)";
    private static Pattern pattern;

    static {
        pattern = Pattern.compile(REGEX);
    }

    private int year;
    private int month;

    private boolean invalid;
    private String yearAndMonth;

    /**
     * Class to represent year and month only, helping to compare and use as map keys
     * @param transactionTime, time in format 1970-01-01T00:00:00.000Z
     */
    public YearAndMonth(String transactionTime){
        if (match(transactionTime)){
            String[] strs = transactionTime.split("-");
            year = Integer.parseInt(strs[0]);
            month = Integer.parseInt(strs[1]);
            yearAndMonth = strs[0]+"-"+strs[1];

            invalid = !checkYearAndMonth(year, month);
        }
        else {
            invalid = true;
        }

        if (invalid) logger.error("Unable to parse transaction time: "+transactionTime+", expected format: 1970-01-01T00:00:00.000Z");
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public String getYearAndMonth() {
        return yearAndMonth;
    }

    public boolean isInvalid() {
        return invalid;
    }

    static boolean checkYearAndMonth(int year, int month){
        if (year>=0 && year<=9999 && month>=1 && month<=12) return true;
        else return false;
    }

    static boolean match(String transactionTime){
        return pattern.matcher(transactionTime).matches();
    }

    @Override
    public boolean equals(Object object){
        if (object==null || !object.getClass().equals(YearAndMonth.class)) return false;

        YearAndMonth ym2 = (YearAndMonth) object;
        if (ym2.year==this.year && ym2.month==this.month) return true;
        else return false;
    }

    @Override
    public int hashCode() {
        return this.year*100+this.month;
    }

    public int compareTo(YearAndMonth o) {
        if (this.year==o.year) return this.month-o.month;
        else return this.year-o.year;
    }

    @Override
    public String toString(){
        return getYearAndMonth();
    }
}
