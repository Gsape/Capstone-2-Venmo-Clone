package com.techelevator.tenmo.model;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

public class Transfer {
    @NotEmpty
    private Long transferID;
    private int transferTypeID;
    private int transferStatusID;
    @NotEmpty
    private Long accountFrom;
    @NotEmpty
    private Long accountTo;
    @NotEmpty
    private BigDecimal amount;

    public Transfer(Long transferID, int transferTypeID, int transferStatusID, Long accountFrom, Long accountTo, BigDecimal amount) {
        this.transferID = transferID;
        this.transferTypeID = transferTypeID;
        this.transferStatusID = transferStatusID;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    public Transfer() {
    }

    @Override
    public String toString(){
        return "Transfer{" + "id=" + transferID +
                ", transferTypeID=" + transferTypeID +
                ", transferStatusID=" + transferStatusID +
                ", accountFrom=" + accountFrom +
                ", accountTo=" + accountTo +
                ", amount=$" + amount + "}";
    }

    public Long getTransferID() {
        return transferID;
    }

    public void setTransferID(Long transferID) {
        this.transferID = transferID;
    }

    public int getTransferTypeID() {
        return transferTypeID;
    }

    public void setTransferTypeID(int transferTypeID) {
        this.transferTypeID = transferTypeID;
    }

    public int getTransferStatusID() {
        return transferStatusID;
    }

    public void setTransferStatusID(int transferStatusID) {
        this.transferStatusID = transferStatusID;
    }

    public Long getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Long accountFrom) {
        this.accountFrom = accountFrom;
    }

    public Long getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Long accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
