package com.techelevator.tenmo.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class TransferHistory {
    private Map<LocalDate, Transfer> transferHistory;

    public TransferHistory() {
    }

    public Map<LocalDate, Transfer> getTransferHistory() {
        return transferHistory;
    }

}
