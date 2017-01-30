package com.edy.interview.live;

import com.edy.interview.Main;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class MainTest {

    private Pattern pattern;

    @Before
    public void setup(){
        String money = "\\$(\\d)+(\\.)(\\d)+";
        String spendAndIncome = "\\{\"spent\":\""+money+"\",\"income\":\""+money+"\"\\}";
        String yearAndMonth = "\"(\\d\\d\\d\\d-\\d\\d)\"";
        String regex = "\\{("+yearAndMonth+":"+spendAndIncome+",)*(\"average\":"+spendAndIncome+")\\}";

        pattern = Pattern.compile(regex);
    }

    //ignored when no connection to internet, or default user is expired
    @Ignore
    @Test
    public void liveTestWithDefaultUser() throws IOException {
        File file = new File("result");
        if (file.exists()) file.delete();

        Main.main(new String[]{});

        assertThat(file.exists(),is(true));

        BufferedReader br = new BufferedReader(new FileReader(file));
        String sCurrentLine = br.readLine();

        assertTrue(pattern.matcher(sCurrentLine).matches());
    }

    //ignored when no connection to internet, or default user is expired
    @Ignore
    @Test
    public void liveTestWithScript() throws IOException {
        File file = new File("result");
        if (file.exists()) file.delete();

        File ccs = new File("ccs");
        if (ccs.exists()) ccs.delete();

        Main.main(new String[]{"-s=script"});

        assertThat(file.exists(),is(true));

        BufferedReader br = new BufferedReader(new FileReader(file));
        String sCurrentLine;
        while ((sCurrentLine = br.readLine()) != null) {
            assertTrue(pattern.matcher(sCurrentLine).matches());
        }

        assertThat(ccs.exists(),is(true));
    }

    @After
    public void cleanUp(){
        File file = new File("result");
        if (file.exists()) file.delete();

        File ccs = new File("ccs");
        if (ccs.exists()) ccs.delete();
    }
}
