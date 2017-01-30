package com.edy.interview.live;

import com.edy.interview.SendPost;
import com.edy.interview.model.DefaultUser;
import com.edy.interview.model.ArgParam;
import com.edy.interview.model.TransactionsResponseModel;
import com.edy.interview.report.ReportBuilder;
import org.apache.http.entity.StringEntity;
import org.junit.Ignore;
import org.junit.Test;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class SendPostTest {

    //ignored when no connection to internet, or default user is expired
    @Ignore
    @Test
    public void sendPostToGetAllTrasnactionsUsingDefaultUser() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String jsonInString = mapper.writeValueAsString(new ArgParam(DefaultUser.getDefaultUser()));
        StringEntity input = new StringEntity(jsonInString);
        input.setContentType("application/json");
        String response = SendPost.sendPost(ReportBuilder.GET_ALL_TRANSACTIONS, input);

        TransactionsResponseModel model = mapper.readValue(response, TransactionsResponseModel.class);
        assertThat(model.getError(), is("no-error"));
        assertFalse(model.getTransactions().isEmpty());
    }

}
