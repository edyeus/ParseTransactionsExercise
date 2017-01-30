package com.edy.interview.report;

import com.edy.interview.SendPost;
import com.edy.interview.model.*;
import org.apache.http.entity.StringEntity;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ReportBuilder {
    private static Logger logger = LoggerFactory.getLogger(ReportBuilder.class);
    public static final String GET_ALL_TRANSACTIONS="https://2016.api.levelmoney.com/api/v2/core/get-all-transactions";
    public static final String NO_ERROR="no-error";
    public static final String BEAN_NAME="reportOption";

    private List<ReportOption> reportOptionList;

    /**
     * Build report from given options
     * @param reportOptionList, injected from context
     */
    public ReportBuilder(List<ReportOption> reportOptionList){
        this.reportOptionList = reportOptionList;
    }

    /**
     * @param user, user for report
     * @param args, arguments from script
     * @return, string in json format
     */
    public String getJsonFormatResultForUser(User user, String[] args){
        List<Transaction> transactions = retrieveAllTransactions(user);

        for (ReportOption reportOption : reportOptionList){
            transactions = reportOption.filter(transactions, args, user);
        }

        if (transactions.isEmpty()) return "";

        return generateResult(transactions);
    }

    List<Transaction> retrieveAllTransactions(User user){
        ObjectMapper mapper = new ObjectMapper();

        try {
            String jsonInString = mapper.writeValueAsString(new ArgParam(user));
            logger.info("Send post with json: "+jsonInString);

            StringEntity input = new StringEntity(jsonInString);
            input.setContentType("application/json");
            String response = SendPost.sendPost(GET_ALL_TRANSACTIONS, input);
            logger.debug("Response from post: "+response);

            TransactionsResponseModel model = mapper.readValue(response, TransactionsResponseModel.class);
            if (!model.getError().toLowerCase().contains(NO_ERROR) && model.getTransactions().isEmpty()) {
                logger.error("Cannot generate report: ", model.getError());
            }
            else return model.getTransactions();
        } catch (IOException e) {
            logger.error("Cannot Retrieve transactions for user: "+user.getUid(),e);
        }
        return Collections.emptyList();
    }

    String generateResult(List<Transaction> transactions){
        Map<YearAndMonth, SpendAndIncome> map = buildMap(transactions);
        StringBuilder builder = new StringBuilder();
        builder.append("{");

        long avgIncome = 0;
        long avgSpend = 0;

        for (Map.Entry<YearAndMonth, SpendAndIncome> entry: map.entrySet()){

            builder.append(convertSpendAndIncomeToJson(entry.getKey().toString(), entry.getValue(), true));

            avgSpend += entry.getValue().retrieveSpendInLong();
            avgIncome += entry.getValue().retrieveIncomeInLong();
        }

        SpendAndIncome avg = new SpendAndIncome();
        avg.addToSpend(avgSpend/map.size());
        avg.addToIncome(avgIncome/map.size());

        builder.append(convertSpendAndIncomeToJson("average", avg, false));
        builder.append("}");
        return builder.toString();
    }

    private String convertSpendAndIncomeToJson(String title, SpendAndIncome spendAndIncome, boolean needComma){
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder builder = new StringBuilder();
        String jsonInString;

        try {
            jsonInString = mapper.writeValueAsString(spendAndIncome);

            builder.append("\"").append(title).append("\"");
            builder.append(":");
            builder.append(jsonInString);
            if (needComma) builder.append(",");

        } catch (IOException e) {
            logger.error("Cannot convert spend and income to string.",e);
        }
        return builder.toString();
    }

    Map<YearAndMonth, SpendAndIncome> buildMap(List<Transaction> transactions){
        Map<YearAndMonth, SpendAndIncome> map = new TreeMap<YearAndMonth, SpendAndIncome>();

        for (Transaction transaction: transactions){
            //do not report pending transaction until cleared
            if (!transaction.isPending()){
                recordClearedTransaction(map, transaction);
            }
        }

        return map;
    }

    private void recordClearedTransaction(Map<YearAndMonth, SpendAndIncome> map, Transaction transaction){
        YearAndMonth yearAndMonth = transaction.getYearAndMonth();
        long amount = transaction.getAmount();
        if (map.containsKey(yearAndMonth)){
            if (amount<0) map.get(yearAndMonth).addToSpend(-amount);
            else map.get(yearAndMonth).addToIncome(amount);
        }
        else {
            SpendAndIncome spendAndIncome = new SpendAndIncome();
            if (amount<0) spendAndIncome.addToSpend(-amount);
            else spendAndIncome.addToIncome(amount);
            map.put(yearAndMonth, spendAndIncome);
        }
    }

}
