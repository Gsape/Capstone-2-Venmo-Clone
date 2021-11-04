package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
        return null; // I want to change this
        //throw new AccountNotFoundException("Account " + accountId + "not found");
    }

    @Override
    public Long getAccountFromUserId(long userId) {
       String sql = "SELECT account_id FROM accounts WHERE user_id = ?;"; // I don't remember if I'm doing this correct
       Long id = jdbcTemplate.queryForObject(sql, long.class, userId);
       if( id != null) {
           return id;
       } else {
           return (long) -1; // unsure of this, maybe make int value instead
       }
    }

    @Override
    public BigDecimal getBalance(BigDecimal balance) {
        String sql = "SELECT balance FROM accounts WHERE account_id = ?;";
        BigDecimal amount = jdbcTemplate.queryForObject(sql, BigDecimal.class, balance);
        return amount; //I think we should check for null here
    }

    @Override
    public void updateBalance(long accountId, Account account) {
        String sql = "UPDATE accounts SET balance WHERE account_id = ?;";
        jdbcTemplate.update(sql, account.getBalance()); // I feel like this is wrong
    }

    private Account mapRowToAccount(SqlRowSet asr) {
        Account account = new Account();
        account.setAccount_id(asr.getLong("account_id"));
        account.setUserId(asr.getLong("user_id"));
        account.setBalance(asr.getBigDecimal("balance"));
        return account;
    }
}
