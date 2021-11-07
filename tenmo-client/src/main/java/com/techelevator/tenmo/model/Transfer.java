package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private Long transferID;
    private int transferTypeID;
    private int transferStatusID;
    private Long accountFrom;
    private Long accountTo;
    private BigDecimal amount;
    private String fromUsername;
    private String toUsername;

    public Transfer(Long transferID, int transferTypeID, int transferStatusID, Long accountFrom, Long accountTo, BigDecimal amount) {
        this.transferID = transferID;
        this.transferTypeID = transferTypeID;
        this.transferStatusID = transferStatusID;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    public Transfer(){
    }

    public String getTypeWord(int type){
        if (type == 2){
            return "sent";
        } else {
            return "requested";
        }
    }

    public String getStatusWord(int status){
        if (status == 2){
            return "Approved";
        } else if (status == 1){
            return "Pending";
        } else {
            return "Rejected";
        }
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
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

    public String toString(){
        return "Transfer id: " + transferID + " (" + getStatusWord(transferStatusID) + ") Account number " + accountFrom +
                " " + getTypeWord(transferTypeID) + " $" + amount + " to account number " + accountTo;
    }
}
