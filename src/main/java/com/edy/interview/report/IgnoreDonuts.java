package com.edy.interview.report;

import com.edy.interview.*;
import com.edy.interview.model.Transaction;
import com.edy.interview.model.User;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class IgnoreDonuts implements ReportOption {
    private static Logger logger = LoggerFactory.getLogger(IgnoreDonuts.class);

    private static Option ignoreDonutsO = new Option("d", "donut", false, "Ignore Donuts");
    private static Options options;
    private static CommandLineParser parser;

    static {
        options = new Options();
        options.addOption(ignoreDonutsO);
        parser = new DefaultParser();
    }

    /**
     * @param transactions, pre-processed transactions
     * @param args, argument to indicate whether run this filter or not
     * @param user, user for report
     * @return list of transactions after filtering out donut related ones
     */
    public List<Transaction> filter(List<Transaction> transactions, String[] args, User user) {

        try {
            CommandLine cmd = parser.parse(options, Util.ignoreUnknownOptions(options, args));
            if (cmd.hasOption("d")){
                return applyFilter(transactions);
            }
            else return transactions;

        } catch (ParseException e) {
            logger.error("Unable to parse parameters and failed to apply filter IgnoreDonuts",e);
            return transactions;
        }
    }

    List<Transaction> applyFilter(List<Transaction> transactions){
        List<Transaction> newList = new ArrayList<Transaction>();

        for (Transaction transaction: transactions){
            if (!isDonut(transaction)) newList.add(transaction);
        }

        return newList;
    }

    boolean isDonut(Transaction transaction){
        if (transaction.getMerchant().contains("Krispy Kreme Donuts") || transaction.getMerchant().contains("DUNKIN #336784")) return true;
        return false;
    }
}
