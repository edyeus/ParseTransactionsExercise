package com.edy.interview.parser;

import com.edy.interview.model.DefaultUser;
import com.edy.interview.model.User;
import org.apache.commons.cli.ParseException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class OneReportCommandParserTest {

    @Test
    public void createWithoutDefaultUserTest() throws ParseException {
        OneReportCommandParser oneReportCommandParser = new OneReportCommandParser("DEFAULT", null);
        User user = oneReportCommandParser.getUser();

        assertThat(user.getUid(),is(DefaultUser.getDefaultUid()));
        assertThat(user.getApiToken(),is(DefaultUser.getDefaultApiToken()));
        assertThat(user.getToken(),is(DefaultUser.getDefaultToken()));
    }

    @Test
    public void createWithExternalUserTest() throws ParseException {
        OneReportCommandParser oneReportCommandParser = new OneReportCommandParser("-u=123 -t=token -a=api -b -c", null);
        User user = oneReportCommandParser.getUser();

        assertThat(user.getUid(),is(123l));
        assertThat(user.getApiToken(),is("api"));
        assertThat(user.getToken(),is("token"));
    }

}
