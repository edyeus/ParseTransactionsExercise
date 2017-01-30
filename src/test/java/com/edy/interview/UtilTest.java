package com.edy.interview;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UtilTest {

    @Test
    public void ignoreOptionTest(){
        String[] args = new String[]{"-a","-s=abc","-d"};
        Options options = new Options();
        options.addOption(new Option("s","script",true,"Script"));
        options.addOption(new Option("a","aa",false,"None"));

        String[] newArgs = Util.ignoreUnknownOptions(options, args);
        assertThat(newArgs.length,is(2));
        assertThat(args[0],is("-a"));
        assertThat(args[1],is("-s=abc"));
    }
}
