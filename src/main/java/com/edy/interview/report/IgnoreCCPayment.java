package com.edy.interview.report;

import com.edy.interview.*;
import com.edy.interview.model.Transaction;
import com.edy.interview.model.User;
import org.apache.commons.cli.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

public class IgnoreCCPayment implements ReportOption{
    private static Logger logger = LoggerFactory.getLogger(IgnoreCCPayment.class);
    private static final int DAY_IN_SECOND = 24*3600;
    private static final String RESULT_FILE = "ccs";

    private static Option ignoreCCO = new Option("cc","ccPayment", false, "Ignore CC Payments");
    private static Options options;
    private static CommandLineParser parser;

    static {
        options = new Options();
        options.addOption(ignoreCCO);
        parser = new DefaultParser();
    }

    /**
     * @param transactions, pre-processed transactions
     * @param args, argument to indicate whether run this filter or not
     * @param user, user for report
     * @return list of transactions after filtering out credit card related ones
     */
    public List<Transaction> filter(List<Transaction> transactions, String[] args, User user) {

        try {
            CommandLine cmd = parser.parse(options, Util.ignoreUnknownOptions(options, args));
            if (cmd.hasOption("cc")){
                return applyFilter(transactions);
            }
            else return transactions;

        } catch (ParseException e) {
            logger.error("Unable to parse parameters and failed to apply filter IgnoreCCPayment",e);
            return transactions;
        }
    }

    List<Transaction> applyFilter(List<Transaction> transactions){
        if (transactions.size()<2) return transactions;

        List<Transaction> newList = sortList(transactions);
        List<Transaction> ccs = new ArrayList<>();

        findCCPayments(newList, ccs);
        removeCCPayments(newList, ccs);
        reportCCPayments(ccs);

        return newList;
    }

    List<Transaction> sortList(List<Transaction> transactions){
        List<Transaction> newList = new ArrayList<Transaction>(transactions);

        Collections.sort(newList, new Comparator<Transaction>() {
            public int compare(Transaction o1, Transaction o2) {
                if (o1.getTransactionTimeAsDateTime().isBefore(o2.getTransactionTimeAsDateTime())) return -1;
                else if (o1.getTransactionTimeAsDateTime().isAfter(o2.getTransactionTimeAsDateTime())) return 1;
                else return 0;
            }
        });

        return newList;
    }

    void findCCPayments(List<Transaction> newList, List<Transaction> ccs){
        Map<Long, Transaction> amountToTransactionMap = new HashMap<>();
        amountToTransactionMap.put(newList.get(0).getAmount(), newList.get(0));
        int frontPointer = 0, backPointer = 1;

        while (backPointer<newList.size()) {
            Duration duration = Duration.between(newList.get(frontPointer).getTransactionTimeAsDateTime(), newList.get(backPointer).getTransactionTimeAsDateTime());
            while (backPointer>frontPointer && duration.getSeconds() >= DAY_IN_SECOND) {
                amountToTransactionMap.remove(newList.get(frontPointer).getAmount());
                frontPointer++;
                duration = Duration.between(newList.get(frontPointer).getTransactionTimeAsDateTime(), newList.get(backPointer).getTransactionTimeAsDateTime());
            }

            if (backPointer!=frontPointer){
                long invertAmount =  -newList.get(backPointer).getAmount();
                if (amountToTransactionMap.containsKey(invertAmount)) {
                    ccs.add(amountToTransactionMap.get(invertAmount));
                    ccs.add(newList.get(backPointer));
                    amountToTransactionMap.remove(invertAmount);
                }
                else amountToTransactionMap.put(newList.get(backPointer).getAmount(), newList.get(backPointer));
            }
            backPointer++;
        }
    }

    void removeCCPayments(List<Transaction> newList, List<Transaction> ccs){
        for (Transaction transaction:ccs) newList.remove(transaction);
    }

    void reportCCPayments(List<Transaction> ccs){
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(RESULT_FILE);
            bw = new BufferedWriter(fw);
            StringBuilder builder = new StringBuilder();
            builder.append("[");

            ObjectMapper mapper = new ObjectMapper();

            boolean first = true;
            for (Transaction transaction : ccs){
                if (first) first = false;
                else builder.append(",");

                builder.append(mapper.writeValueAsString(transaction));
            }
            builder.append("]");

            System.out.println(builder.toString());
            bw.write(builder.toString());
            bw.write("\n");

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

}
