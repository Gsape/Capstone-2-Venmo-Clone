package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.techelevator.tenmo.model.Transfer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import org.springframework.stereotype.Component;

import javax.security.auth.login.AccountNotFoundException;

@Component
public class JdbcTransferDao implements TransferDao{

    private JdbcTemplate jdbcTemplate;
    private JdbcAccountDao jdbcAccountDao;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate, JdbcAccountDao jdbcAccountDao){
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcAccountDao = jdbcAccountDao;
    }

    @Override
    public int send(BigDecimal amount, Long accountFrom, Long accountTo) {
        // deduct from originator
        BigDecimal newFromBalance = deduct(accountFrom, amount);
        // deposit in acceptee
        BigDecimal newToBalance = deposit(accountTo, amount);
        // add to transfers table in db
        String sql = "INSERT INTO transfers VALUES (DEFAULT, 2, 2, ?, ?, ?) RETURNING transfer_id";
        int transferId = 0;
        try {
            transferId = jdbcTemplate.queryForObject(sql, int.class, accountFrom, accountTo, amount);
        } catch (DataAccessException e){
            return transferId;
        }
        // return the id of the new transfer created
        return transferId;
    }

    @Override
    public String approve(Long transferId, int code) {
        // change transfer status in transfer table of db to approved
        Transfer transfer = new Transfer();
        String sql = "UPDATE transfers SET transfer_status_id = ? WHERE transfer_id = ?";
        int success = 0;
        try {
            success = jdbcTemplate.update(sql, code, transferId);
        } catch (DataAccessException e){
            return "Error occured at request";
        }
        if (success==0){
            return "Transfer Approved!";
        } else {
            return "This transfer was rejected.";
        }
    }

    @Override
    public BigDecimal deposit(Long accountId, BigDecimal amount) {
        // data validation
        if (amount == null || amount.equals(0.00) || amount.compareTo(BigDecimal.ZERO)<0){
            return null; // should throw an exception/error instead?
        }
        // set up account info
        Account account = new Account();
        account = jdbcAccountDao.getAccountInfo(accountId);
        // calculate new balance
        BigDecimal newBalance = account.getBalance().add(amount);
        // update database
        String sql = "UPDATE accounts SET balance = ? WHERE account_id = ? RETURNING balance";
        BigDecimal returnBalance = jdbcTemplate.queryForObject(sql, BigDecimal.class, newBalance, accountId);
        // confirm that db balance is equal to new balance
        if (returnBalance.compareTo(newBalance)==0){
            return newBalance;
        }
        BigDecimal error = BigDecimal.valueOf(0);
        return error;
    }

    @Override
    public BigDecimal deduct(Long accountId, BigDecimal amount) {
        // set up account info
        Account account = new Account();
        account = jdbcAccountDao.getAccountInfo(accountId);
        // data validation
        if ((amount.compareTo(account.getBalance())>0) || amount == null || amount.equals(0.00) ||
                amount.compareTo(BigDecimal.ZERO)<0){
            return null; // actually I want this to throw an exception or something, essentially prevent it from happening
        }
        // calculate new balance
        BigDecimal newBalance = account.getBalance();
        newBalance= newBalance.subtract(amount);
        // update database
        String sql = "UPDATE accounts SET balance = ? WHERE account_id = ? RETURNING balance";
        BigDecimal returnBalance = jdbcTemplate.queryForObject(sql, BigDecimal.class, newBalance, accountId);
        // confirm that db balance is equal to new balance
        if (returnBalance.compareTo(newBalance)==0){
            return newBalance;
        }
        BigDecimal error = BigDecimal.valueOf(0);
        return error;
    }

    @Override
    public List<Transfer> getTransferHistory(Long accountId) {
        List<Transfer> transferHistory = new ArrayList<>();
        String sql = "SELECT * FROM transfers WHERE account_from = ? OR account_to = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId, accountId);
        while (results.next()){
            Transfer transfer = mapRowToTransfer(results);
            transferHistory.add(transfer);
        }
        return transferHistory;
    }

    @Override
    public Transfer getSingleTransferDetails(Long accountId, Long transferId){
        Transfer singleTransfer = new Transfer();
        String sql = "SELECT * FROM transfers JOIN accounts ON account_id = account_from OR account_id = account_to " +
                "WHERE account_id = ? AND transfer_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountId, transferId);
        if (result.next()){
            singleTransfer = mapRowToTransfer(result);
        }
        return singleTransfer;
    }

    private Transfer mapRowToTransfer(SqlRowSet rowset){
        Transfer transfer = new Transfer();
        transfer.setTransferID(rowset.getLong("transfer_id"));
        transfer.setTransferTypeID(rowset.getInt("transfer_type_id"));
        transfer.setTransferStatusID(rowset.getInt("transfer_status_id"));
        transfer.setAccountFrom(rowset.getLong("account_from"));
        transfer.setAccountTo(rowset.getLong("account_to"));
        transfer.setAmount(rowset.getBigDecimal("amount"));
        return transfer;
    }

}
