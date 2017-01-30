package com.edy.interview.parser;

import com.edy.interview.*;
import com.edy.interview.model.DefaultUser;
import com.edy.interview.model.User;
import com.edy.interview.report.ReportBuilder;
import org.apache.commons.cli.*;
import org.springframework.context.ApplicationContext;

import java.util.regex.Pattern;

public class OneReportCommandParser {
    private static final String REGEX = "(.*)((\\s)(.*))*";
    private static Pattern pattern;
    public static final String DEFAULT="DEFAULT";
    private ApplicationContext context;
    private String[] args;

    private Option uidO = new Option("u","uid", true, "User ID");
    private Option tokenO = new Option("t","token", true, "Token");
    private Option apiTokenO = new Option("a","apiToken", true, "Api Token");

    private User user;

    static {
        pattern = Pattern.compile(REGEX);
    }

    /**
     * @param argStr, one line from script to represent commandline args
     * @param context, spring context for setting up optional filters, such as IgnoreDonut
     * @throws ParseException, throw exception when args cannot be parsed correctly
     */
    public OneReportCommandParser(String argStr, ApplicationContext context) throws ParseException{
        this.context = context;
        if (argStr.equals(DEFAULT)) {
            user = DefaultUser.getDefaultUser();
            this.args = new String[]{};
        }
        else if (pattern.matcher(argStr).matches()){
            this.args = argStr.split("\\s");
            parseArgs();
        }
        else {
            throw new ParseException("Cannot parse command: "+argStr);
        }
    }

    /**
     * method to run report for one line in script
     * @return report string in json format
     */
    public String runOneReport(){
        ReportBuilder reportBuilder = (ReportBuilder) context.getBean(ReportBuilder.BEAN_NAME);
        return reportBuilder.getJsonFormatResultForUser(user, args);
    }

    private void parseArgs() throws ParseException {
        Options options = new Options();
        options.addOption(uidO);
        options.addOption(tokenO);
        options.addOption(apiTokenO);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, Util.ignoreUnknownOptions(options, args));

        try {
            if (cmd.hasOption("u") && cmd.hasOption("t") && cmd.hasOption("a")) {
                user = new User(Long.parseLong(cmd.getOptionValue("u")), cmd.getOptionValue("t"), cmd.getOptionValue("a"), false, false);
            }
            else {
                user = DefaultUser.getDefaultUser();
            }
        }
        catch (Exception e){
            user = DefaultUser.getDefaultUser();
        }
    }

    User getUser() {
        return user;
    }
}
