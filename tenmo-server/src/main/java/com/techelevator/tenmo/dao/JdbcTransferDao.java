package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public class JdbcTransferDao implements TransferDao{
    @Override
    public boolean send(BigDecimal amount, Long ) {
        // deduct from originator
        String sql = "UPDATE accounts SET balance = ? WHERE user_id";

        // deposit in acceptee
        String sql2 = "UPDATE accounts SET balance = ? WHERE user_id";
        return false;
    }

    @Override
    public boolean request() {
        // if approved
        // deduct from other account
        //deposit into requestor's account
        String sql = "";

        return false;
    }

    @Override
    public boolean approve() {
        // change transfer status in transfer table of db to approved
        String sql = "";
        return false;
    }

    @Override
    public BigDecimal deposit() {
        String sql = "";
        return null;
    }

    @Override
    public BigDecimal deduct() {
        // called during
        String sql = "";
        return null;
    }

    @Override
    public String addToTransferHistory() {
        // need to create an object that has a list of transactions
        // add each transaction to the list
        String sql = "";
        return null;
    }
}
