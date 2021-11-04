package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {

    List<Account> findAll();

    Account getAccountInfo(long accountId); // maybe an int?

    Long getAccountFromUserId(long userId);

    BigDecimal getBalance(BigDecimal balance);

    void updateBalance(long accountId, Account account); // maybe make a boolean to see if it was successful

}
