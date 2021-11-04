package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {

    private Long accountId;
    private Long userId;
    private BigDecimal balance;

    //need explain
    public Account() { }

    public Account(Long accountId, Long userId, BigDecimal balance){
        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccount_id(Long accountId) {
        this.accountId = accountId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", userId='" + userId + '\'' +
                ", balance" + balance +
                '}';
    }
}
