package com.edy.interview;

import com.edy.interview.parser.ReportCommandLineParser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    /**
     * Entry point for application
     * @param args, take -s for script file path (optional)
     */
    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

        ReportCommandLineParser reportCommandLineParser = new ReportCommandLineParser(args, context);
        reportCommandLineParser.run();
    }
}
