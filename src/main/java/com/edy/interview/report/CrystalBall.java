package com.edy.interview.report;

import com.edy.interview.*;
import com.edy.interview.model.*;
import org.apache.commons.cli.*;
import org.apache.http.entity.StringEntity;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class CrystalBall implements ReportOption{
    private static Logger logger = LoggerFactory.getLogger(CrystalBall.class);
    public static final String GET_PROJECTED_TRANSACTIONS="https://2016.api.levelmoney.com/api/v2/core/projected-transactions-for-month";

    private static Option crystalBallO = new Option("c","crystalBall",false, "Crystal Ball");
    private static Options options;
    private static CommandLineParser parser;

    static {
        options = new Options();
        options.addOption(crystalBallO);
        parser = new DefaultParser();
    }

    /**
     * @param transactions, pre-processed transactions
     * @param args, argument to indicate whether run this filter or not
     * @param user, user for report
     * @return list of transactions after adding in predictions from prediction end point, also create file ccs containing credit card transactions
     */
    public List<Transaction> filter(List<Transaction> transactions, String[] args, User user) {
        try {
            CommandLine cmd = parser.parse(options, Util.ignoreUnknownOptions(options, args));
            if (cmd.hasOption("c")){
                return applyFilter(transactions, user);
            }
            else return transactions;

        } catch (ParseException e) {
            logger.error("Unable to parse parameters and failed to apply filter IgnoreCCPayment",e);
            return transactions;
        }
    }

    List<Transaction> applyFilter(List<Transaction> transactions, User user){
        List<Transaction> projections = retrieveProjectedTransactions(user);
        return addToNewList(transactions, projections);
    }

    List<Transaction> retrieveProjectedTransactions(User user){
        ObjectMapper mapper = new ObjectMapper();
        LocalDateTime localDateTime = LocalDateTime.now();

        try {
            String jsonInString = mapper.writeValueAsString(new ArgParamForCrystalBall(user, localDateTime.getYear(), localDateTime.getMonthValue()));
            logger.info("Send post with json: "+jsonInString);

            StringEntity input = new StringEntity(jsonInString);
            input.setContentType("application/json");
            String response = SendPost.sendPost(GET_PROJECTED_TRANSACTIONS, input);
            logger.debug("Response from post: "+response);

            TransactionsResponseModel model = mapper.readValue(response, TransactionsResponseModel.class);
            if (!model.getError().toLowerCase().contains(ReportBuilder.NO_ERROR) && model.getTransactions().isEmpty()) {
                logger.error("Cannot generate report: ", model.getError());
            }
            else return model.getTransactions();
        } catch (IOException e) {
            logger.error("Cannot Retrieve transactions for user: "+user.getUid(),e);
        }
        return Collections.emptyList();
    }

    List<Transaction> addToNewList(List<Transaction> transactions, List<Transaction> projections){
        Map<String, Transaction> collisionMap = new HashMap<>();
        for (Transaction transaction: projections) collisionMap.put(transaction.getTransactionId(), transaction);

        List<Transaction> newList = new ArrayList<Transaction>();

        for (Transaction transaction: transactions){
            if (collisionMap.containsKey(transaction.getTransactionId())){
                collisionMap.remove(transaction.getTransactionId());
            }
            newList.add(transaction);
        }

        for (Map.Entry<String, Transaction> entry: collisionMap.entrySet()) newList.add(entry.getValue());

        return newList;
    }
}
