package com.edy.interview.model;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class YearAndMonthTest {

    @Test
    public void parseYearAndMonthTest(){
        String str = "2014-10-07T12:59:00.000Z";
        YearAndMonth yearAndMonth = new YearAndMonth(str);

        assertThat(yearAndMonth.isInvalid(), is(false));
        assertThat(yearAndMonth.getMonth(), is(10));
        assertThat(yearAndMonth.getYear(), is(2014));
        assertThat(yearAndMonth.getYearAndMonth(), is("2014-10"));
        assertThat(yearAndMonth.toString(), is("2014-10"));
    }

    @Test
    public void parseInvalidTest(){
        String str = "invalid";
        YearAndMonth yearAndMonth = new YearAndMonth(str);

        assertThat(yearAndMonth.isInvalid(), is(true));
        assertThat(yearAndMonth.getMonth(), is(0));
        assertThat(yearAndMonth.getYear(), is(0));
        assertNull(yearAndMonth.getYearAndMonth());
        assertNull(yearAndMonth.toString());
    }

    @Test
    public void equalObjectsTest(){
        String str1 = "2014-10-08";
        YearAndMonth yearAndMonth1 = new YearAndMonth(str1);

        String str2 = "2014-10-10";
        YearAndMonth yearAndMonth2 = new YearAndMonth(str2);

        String str3 = "2014-11-10";
        YearAndMonth yearAndMonth3 = new YearAndMonth(str3);

        assertTrue(yearAndMonth1.equals(yearAndMonth2));
        assertFalse(yearAndMonth1.equals(yearAndMonth3));
        assertThat(yearAndMonth1.hashCode(), is(yearAndMonth2.hashCode()));
    }

    @Test
    public void compareObjectsTest(){
        String str1 = "2014-10-08";
        YearAndMonth yearAndMonth1 = new YearAndMonth(str1);

        String str2 = "2014-10-10";
        YearAndMonth yearAndMonth2 = new YearAndMonth(str2);

        String str3 = "2014-11-10";
        YearAndMonth yearAndMonth3 = new YearAndMonth(str3);

        String str4 = "2014-09-10";
        YearAndMonth yearAndMonth4 = new YearAndMonth(str4);

        String str5 = "2013-10-08";
        YearAndMonth yearAndMonth5 = new YearAndMonth(str5);

        assertThat(yearAndMonth1.compareTo(yearAndMonth2), is(0));
        assertThat(yearAndMonth1.compareTo(yearAndMonth3), is(-1));
        assertThat(yearAndMonth1.compareTo(yearAndMonth4), is(1));
        assertThat(yearAndMonth1.compareTo(yearAndMonth5), is(1));
    }

}
