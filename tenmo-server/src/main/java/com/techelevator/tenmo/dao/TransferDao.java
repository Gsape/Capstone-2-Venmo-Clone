package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface TransferDao {

    String send(BigDecimal amount, Long accountFrom, Long accountTo);
    boolean request();
    String approve(Long transferId, int code);
    BigDecimal deposit(Long accountId, BigDecimal amount);
    BigDecimal deduct(Long accountId, BigDecimal amount);
    String addToTransferHistory();

}
