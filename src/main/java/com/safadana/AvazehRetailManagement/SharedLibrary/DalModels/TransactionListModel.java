package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import com.safadana.AvazehRetailManagement.SharedLibrary.Enums.TransactionFinancialStatus;

import lombok.Data;

@Data
public class TransactionListModel {
    private int id;

    private String fileName;
    private String dateCreated;
    private String timeCreated;
    private String dateUpdated;
    private String timeUpdated;
    private String descriptions;
    private double totalPositiveItemsSum;
    private double totalNegativeItemsSum;

    public String getDateTimeCreated() {
        return timeCreated + " " + dateCreated;
    }

    public String getDateTimeUpdated() {
        return timeUpdated + " " + dateUpdated;
    }

    public double getTotalBalance() {
        return totalPositiveItemsSum + totalNegativeItemsSum;
    }

    public TransactionFinancialStatus getTransactionFinancialStatus() {
        if (getTotalBalance() == 0) {
            return TransactionFinancialStatus.Balanced;
        } else if (getTotalBalance() > 0) {
            return TransactionFinancialStatus.Positive;
        } else {
            return TransactionFinancialStatus.Negative;
        }
    }
}
