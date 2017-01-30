package com.edy.interview.model;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class TransactionsResponseModel {

    private String error;
    private List<Transaction> transactions;

    /**
     * Class to represent response from server
     * @param error, error message
     * @param transactions, list of transactions
     */
    @JsonCreator
    public TransactionsResponseModel(@JsonProperty("error") String error, @JsonProperty("transactions")List<Transaction> transactions){
        this.error = error;
        this.transactions = transactions;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
