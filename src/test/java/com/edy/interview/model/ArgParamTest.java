package com.edy.interview.model;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ArgParamTest {

    private ArgParam argParam;
    private Integer integer;

    @Before
    public void setup(){
        integer = 0;
        argParam = new ArgParam(integer);
    }

    @Test
    public void createJsonFromObjectTest() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String jsonInString = mapper.writeValueAsString(argParam);
        assertThat(jsonInString, is("{\"args\":"+integer+"}"));
    }
}
