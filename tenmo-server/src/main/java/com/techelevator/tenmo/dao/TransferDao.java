package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface TransferDao {

    boolean send();
    boolean request();
    boolean approve();
    BigDecimal deposit();
    BigDecimal deduct();
    String addToTransferHistory();

}
