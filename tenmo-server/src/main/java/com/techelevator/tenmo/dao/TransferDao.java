package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    int send(BigDecimal amount, Long accountFrom, Long accountTo);
    boolean request();
    String approve(Long transferId, int code);
    BigDecimal deposit(Long accountId, BigDecimal amount);
    BigDecimal deduct(Long accountId, BigDecimal amount);
    List<Transfer> getTransferHistory(Long accountId);
    Transfer getSingleTransferDetails(Long accountId, Long transferId);

}
