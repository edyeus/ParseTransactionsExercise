package com.edy.interview.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    /**
     * Class representing one transaction
     */

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private long amount;
    private boolean isPending;
    private long aggregationTime;
    private String accountId;
    private long clearDate;
    private String transactionId;
    private String rawMerchant;
    private String categorization;
    private String merchant;
    private LocalDateTime transactionTime;
    private String previousTransactionId;

    private YearAndMonth yearAndMonth;

    private String payeeNameOnlyForTesting;
    private String memoOnlyForTesting;

    @JsonProperty("amount")
    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @JsonProperty("is-pending")
    public boolean isPending() {
        return isPending;
    }

    public void setPending(boolean pending) {
        isPending = pending;
    }

    @JsonProperty("aggregation-time")
    public long getAggregationTime() {
        return aggregationTime;
    }

    public void setAggregationTime(long aggregationTime) {
        this.aggregationTime = aggregationTime;
    }

    @JsonProperty("account-id")
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @JsonProperty("clear-date")
    public long getClearDate() {
        return clearDate;
    }

    public void setClearDate(long clearDate) {
        this.clearDate = clearDate;
    }

    @JsonProperty("transaction-id")
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @JsonProperty("raw-merchant")
    public String getRawMerchant() {
        return rawMerchant;
    }

    public void setRawMerchant(String rawMerchant) {
        this.rawMerchant = rawMerchant;
    }

    @JsonProperty("categorization")
    public String getCategorization() {
        return categorization;
    }

    public void setCategorization(String categorization) {
        this.categorization = categorization;
    }

    @JsonProperty("merchant")
    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    @JsonProperty("transaction-time")
    public String getTransactionTime() {
        if (transactionTime!=null) return formatter.format(transactionTime);
        else return null;
    }

    public LocalDateTime getTransactionTimeAsDateTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = LocalDateTime.parse(transactionTime, formatter);
        setYearAndMonth(transactionTime);
    }

    @JsonProperty("payee-name-only-for-testing")
    public String getPayeeNameOnlyForTesting() {
        return payeeNameOnlyForTesting;
    }

    public void setPayeeNameOnlyForTesting(String payeeNameOnlyForTesting) {
        this.payeeNameOnlyForTesting = payeeNameOnlyForTesting;
    }

    @JsonProperty("memo-only-for-testing")
    public String getMemoOnlyForTesting() {
        return memoOnlyForTesting;
    }

    public void setMemoOnlyForTesting(String memoOnlyForTesting) {
        this.memoOnlyForTesting = memoOnlyForTesting;
    }

    public YearAndMonth getYearAndMonth() {
        if (yearAndMonth.isInvalid()) return null;
        else return yearAndMonth;
    }

    public void setYearAndMonth(String transactionTime){
        this.yearAndMonth = new YearAndMonth(transactionTime);
    }

    @JsonProperty("previous-transaction-id")
    public String getPreviousTransactionId() {
        return previousTransactionId;
    }

    public void setPreviousTransactionId(String previousTransactionId) {
        this.previousTransactionId = previousTransactionId;
    }
}
