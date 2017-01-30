package com.edy.interview.parser;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReportCommandLineParserTest {

    @Test
    public void createWithoutScriptTest(){
        ReportCommandLineParser reportCommandLineParser = new ReportCommandLineParser(new String[]{}, null);
        assertThat(reportCommandLineParser.isUsingScript(), is(false));
    }

    @Test
    public void createWithScriptTest(){
        ReportCommandLineParser reportCommandLineParser = new ReportCommandLineParser(new String[]{"-s=file"}, null);
        assertThat(reportCommandLineParser.isUsingScript(), is(true));
    }

}
