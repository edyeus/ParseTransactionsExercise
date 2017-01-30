package com.edy.interview.report;

import com.edy.interview.model.Transaction;
import com.edy.interview.model.User;

import java.util.List;

public interface ReportOption {

    /**
     * Take a list of transactions, filter out transactions based on a certain criteria
     * @return a new list of transactions, the input list shall not be modified
     */
    List<Transaction> filter(List<Transaction> transactions, String[] args, User user);

}
