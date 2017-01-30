package com.edy.interview.model;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TransactionTest {

    @Test
    public void unmarshalNullTest() throws IOException {
        String response = "{}";

        ObjectMapper mapper = new ObjectMapper();
        Transaction model = mapper.readValue(response, Transaction.class);

        assertNull(model.getAccountId());
        assertThat(model.getAggregationTime(), is(0l));
        assertThat(model.getAmount(), is(0l));
        assertFalse(model.isPending());
        assertNull(model.getCategorization());
        assertThat(model.getClearDate(), is(0l));
        assertNull(model.getTransactionId());
        assertNull(model.getRawMerchant());
        assertNull(model.getMerchant());
        assertNull(model.getTransactionTime());
        assertNull(model.getTransactionTimeAsDateTime());
        assertNull(model.getPreviousTransactionId());
        assertNull(model.getPayeeNameOnlyForTesting());
        assertNull(model.getMemoOnlyForTesting());
    }

    @Test
    public void unmarshalAccountIdTest() throws IOException {
        String response = "{\"account-id\": \"nonce:comfy-cc\\/hdhehe\"}";

        ObjectMapper mapper = new ObjectMapper();
        Transaction model = mapper.readValue(response, Transaction.class);

        assertThat(model.getAccountId(), is("nonce:comfy-cc/hdhehe"));
    }

    @Test
    public void unmarshalAggregationTest() throws IOException {
        String response = "{\"aggregation-time\": 1412686740000}";

        ObjectMapper mapper = new ObjectMapper();
        Transaction model = mapper.readValue(response, Transaction.class);

        assertThat(model.getAggregationTime(), is(1412686740000l));
    }

    @Test
    public void unmarshalAmountTest() throws IOException {
        String response = "{\"amount\": -34300}";

        ObjectMapper mapper = new ObjectMapper();
        Transaction model = mapper.readValue(response, Transaction.class);

        assertThat(model.getAmount(), is(-34300l));
    }

    @Test
    public void unmarshalIsPendingTest() throws IOException {
        String response = "{\"is-pending\": true}";

        ObjectMapper mapper = new ObjectMapper();
        Transaction model = mapper.readValue(response, Transaction.class);

        assertTrue(model.isPending());
    }

    @Test
    public void unmarshalCategorizationTest() throws IOException {
        String response = "{\"categorization\": \"Unknown\"}";

        ObjectMapper mapper = new ObjectMapper();
        Transaction model = mapper.readValue(response, Transaction.class);

        assertThat(model.getCategorization(), is("Unknown"));
    }

    @Test
    public void unmarshalTransactionIdTest() throws IOException {
        String response = "{\"transaction-id\": \"1412985120000\"}";

        ObjectMapper mapper = new ObjectMapper();
        Transaction model = mapper.readValue(response, Transaction.class);

        assertThat(model.getTransactionId(),is("1412985120000"));
    }

    @Test
    public void unmarshalClearDateTest() throws IOException {
        String response = "{\"clear-date\": 1412790480000}";

        ObjectMapper mapper = new ObjectMapper();
        Transaction model = mapper.readValue(response, Transaction.class);

        assertThat(model.getClearDate(), is(1412790480000l));
    }

    @Test
    public void unmarshalRawMerchantAndMerchantTest() throws IOException {
        String response = "{\"raw-merchant\": \"7-ELEVEN 23853\",\"merchant\": \"7-Eleven 23853\"}";

        ObjectMapper mapper = new ObjectMapper();
        Transaction model = mapper.readValue(response, Transaction.class);

        assertThat(model.getRawMerchant(),is("7-ELEVEN 23853"));
        assertThat(model.getMerchant(),is("7-Eleven 23853"));
    }

    @Test
    public void unmarshalTransactionTimeTest() throws IOException {
        String response = "{\"transaction-time\": \"2014-10-07T12:59:00.000Z\"}";

        ObjectMapper mapper = new ObjectMapper();
        Transaction model = mapper.readValue(response, Transaction.class);

        assertThat(model.getTransactionTime(), is("2014-10-07T12:59:00.000Z"));
        assertNotNull(model.getTransactionTimeAsDateTime());
        assertThat(model.getTransactionTimeAsDateTime().getYear(), is(2014));
        assertThat(model.getTransactionTimeAsDateTime().getMonthValue(), is(10));
        assertThat(model.getYearAndMonth(), is(new YearAndMonth("2014-10-07T12:59:00.000Z")));
        assertThat(model.getYearAndMonth().getYear(), is(2014));
        assertThat(model.getYearAndMonth().getMonth(), is(10));
    }

    @Test
    public void unmarshalPreviousTransactionIdTest() throws IOException {
        String response = "{\"previous-transaction-id\": \"1412985120000\"}";

        ObjectMapper mapper = new ObjectMapper();
        Transaction model = mapper.readValue(response, Transaction.class);

        assertThat(model.getPreviousTransactionId(),is("1412985120000"));
    }
}
