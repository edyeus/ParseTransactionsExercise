package com.edy.interview.parser;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReportCommandLineParser {
    private static Logger logger = LoggerFactory.getLogger(ReportCommandLineParser.class);
    private static final String RESULT_FILE = "result";

    private List<OneReportCommandParser> reports;
    private Option script = new Option("s","script",true, "Using script to run");
    private ApplicationContext context;

    private boolean usingScript;

    /**
     * @param args, command line args
     * @param context, spring context for setting up optional filters, such as IgnoreDonut
     */
    public ReportCommandLineParser(String[] args, ApplicationContext context){
        this.context = context;

        Options options = new Options();
        options.addOption(script);

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            reports = new ArrayList<OneReportCommandParser>();

            if (cmd.hasOption("s")){
                parseScript(cmd.getOptionValue("s"));
                usingScript = true;
            }
            else {
                OneReportCommandParser defaultUserAndReport = new OneReportCommandParser(OneReportCommandParser.DEFAULT, context);
                reports.add(defaultUserAndReport);
                usingScript = false;
            }

        }catch (ParseException e){
            logger.error("Cannot parse parameters: "+e.getMessage());
        }
    }

    /**
     * method to kick off reporting process
     */
    public void run(){
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(RESULT_FILE);
            bw = new BufferedWriter(fw);

            if (reports.isEmpty()) bw.write("No Result, possibly error occurred.");

            for (OneReportCommandParser oneReportCommandParser : reports){
                String r = oneReportCommandParser.runOneReport();

                if (r!=null && !r.isEmpty()) {
                    System.out.println(r);
                    bw.write(r);
                    bw.write("\n");
                }
            }

        } catch (IOException e) {
            logger.error("Cannot write result to file: "+RESULT_FILE);
        }
        finally {
            try {
                if (bw!=null) bw.close();
                if (fw!=null) fw.close();
            } catch (IOException e) {
                logger.error("Unable to close file", e);
            }
        }
    }

    private void parseScript(String filePath){
        BufferedReader br = null;
        String sCurrentLine;

        try {
            br = new BufferedReader(new FileReader(filePath));
            while ((sCurrentLine = br.readLine()) != null) {
                OneReportCommandParser oneReportCommandParser = new OneReportCommandParser(sCurrentLine, context);
                reports.add(oneReportCommandParser);
            }
        } catch (FileNotFoundException e) {
            logger.error("Cannot access file with path: "+filePath, e);
        } catch (IOException e) {
            logger.error("Cannot parse file", e);
        } catch (Exception e) {
            logger.error("Cannot generate report with given parameters", e);
        }
    }

    boolean isUsingScript() {
        return usingScript;
    }
}
