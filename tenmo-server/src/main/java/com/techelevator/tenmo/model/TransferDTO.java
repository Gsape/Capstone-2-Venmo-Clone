package com.techelevator.tenmo.model;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

public class TransferDTO {
    private BigDecimal amount;
    private String fromUser;
    private String toUser;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    @Override
    public String toString() {
        return "TransferDTO{" +
                "amount=$" + amount +
                ", accountFrom='" + fromUser + '\'' +
                ", accountTo='" + toUser + '\'' +
                '}';
    }
}
