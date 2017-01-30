package com.edy.interview.model;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UserTest {

    private long uid;
    private String token;
    private String apiToken;
    private boolean jsonStrictMode;
    private boolean jsonVerboseResponse;

    @Before
    public void setup(){
        uid = 1;
        token = "token";
        apiToken = "apiToken";
        jsonStrictMode = false;
        jsonVerboseResponse = true;
    }

    @Test
    public void createJsonFromObjectTest() throws IOException {
        User user = new User(uid, token, apiToken, jsonStrictMode, jsonVerboseResponse);

        ObjectMapper mapper = new ObjectMapper();

        String jsonInString = mapper.writeValueAsString(user);
        assertThat(jsonInString, is("{\"uid\":"+uid+",\"token\":\""+token+"\",\"api-token\":\""+apiToken+"\",\"json-strict-mode\":"+jsonStrictMode+",\"json-verbose-response\":"+jsonVerboseResponse+"}"));
    }
}
