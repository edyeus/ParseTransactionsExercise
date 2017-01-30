package com.edy.interview.model;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ArgParamForCrystalBallTest {

    private ArgParamForCrystalBall argParamForCrystalBall;
    private Integer integer;
    private int year;
    private int month;

    @Before
    public void setup(){
        integer = 0;
        year = 1;
        month = 2;
        argParamForCrystalBall = new ArgParamForCrystalBall(integer, year, month);
    }

    @Test
    public void createJsonFromObjectTest() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String jsonInString = mapper.writeValueAsString(argParamForCrystalBall);
        assertThat(jsonInString, is("{\"args\":"+integer+",\"year\":"+ year+",\"month\":"+month+"}"));
    }
}
