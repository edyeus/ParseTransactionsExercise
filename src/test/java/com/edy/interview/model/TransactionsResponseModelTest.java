package com.edy.interview.model;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;

public class TransactionsResponseModelTest {

    @Test
    public void unmarshalNullTest() throws IOException {

        String response = "{}";

        ObjectMapper mapper = new ObjectMapper();
        TransactionsResponseModel model = mapper.readValue(response, TransactionsResponseModel.class);

        assertNull(model.getError());
        assertNull(model.getTransactions());
    }

    @Test
    public void unmarshalErrorTest() throws IOException {

        String response = "{\"error\":\"HasError\"}";

        ObjectMapper mapper = new ObjectMapper();
        TransactionsResponseModel model = mapper.readValue(response, TransactionsResponseModel.class);

        assertThat(model.getError(), is("HasError"));
        assertNull(model.getTransactions());
    }

    @Test
    public void unmarshalNoErrorTest() throws IOException {

        String response = "{\"error\":\"no-error\",\"transactions\":[{},{}]}";

        ObjectMapper mapper = new ObjectMapper();
        TransactionsResponseModel model = mapper.readValue(response, TransactionsResponseModel.class);

        assertThat(model.getError(), is("no-error"));
        assertThat(model.getTransactions().size(),is(2));
    }

}
