package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.safadana.AvazehRetailManagement.SharedLibrary.Enums.TransactionFinancialStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionListModel {
    private int id;
    private String fileName;
    private String dateCreated;
    private String dateUpdated;
    private String descriptions;
    private double totalPositiveItemsSum;
    private double totalNegativeItemsSum;

    @JsonIgnore
    public double getTotalBalance() {
        return totalPositiveItemsSum + totalNegativeItemsSum;
    }

    @JsonIgnore
    public TransactionFinancialStatus getTransactionFinancialStatus() {
        if (getTotalBalance() == 0) {
            return TransactionFinancialStatus.BALANCED;
        } else if (getTotalBalance() > 0) {
            return TransactionFinancialStatus.POSITIVE;
        } else {
            return TransactionFinancialStatus.NEGATIVE;
        }
    }
}
