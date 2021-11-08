package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {

    List<Account> findAll();

    Account getAccountInfo(long accountId) ;

    Long getAccountIdFromUserId(long userId);

    BigDecimal getBalance(long accountId) throws AccountNotFoundException;


}
