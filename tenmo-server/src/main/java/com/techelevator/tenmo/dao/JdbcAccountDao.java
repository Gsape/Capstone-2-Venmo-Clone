package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.TransferNotFoundException;
import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;


import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT account_id, user_id, balance FROM accounts;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            Account account = mapRowToAccount(results);
            accounts.add(account);
        }
        return accounts;
    }

    @Override
    public Account getAccountInfo(long accountId) {
        String sql = "SELECT account_id, user_id, balance FROM accounts WHERE account_id = ?;";
        SqlRowSet row = jdbcTemplate.queryForRowSet(sql,accountId);
        if (row.next()){
            return mapRowToAccount(row);
        }
         return null; //throw new AccountNotFoundException();
    }

    @Override
    public Long getAccountIdFromUserId(long userId) {
       String sql = "SELECT account_id FROM accounts WHERE user_id = ?;";
       Long id = jdbcTemplate.queryForObject(sql, long.class, userId);
       if( id != null) {
           return id;
       } else {
           return (long) -1;
       }
    }

    @Override
    public BigDecimal getBalance(long accountId) throws AccountNotFoundException {
        String sql = "SELECT balance FROM accounts WHERE account_id = ?;";
        BigDecimal amount = jdbcTemplate.queryForObject(sql, BigDecimal.class, accountId);
        if (amount == null || amount.compareTo(BigDecimal.ZERO)<0){
            throw new AccountNotFoundException();
        }
        return amount;
    }

    private Account mapRowToAccount(SqlRowSet asr) {
        Account account = new Account();
        account.setAccount_id(asr.getLong("account_id"));
        account.setUserId(asr.getLong("user_id"));
        account.setBalance(asr.getBigDecimal("balance"));
        return account;
    }
}
