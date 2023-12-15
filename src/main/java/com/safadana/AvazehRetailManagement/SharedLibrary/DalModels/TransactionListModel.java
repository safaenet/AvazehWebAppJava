package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import java.math.BigDecimal;

import com.safadana.AvazehRetailManagement.SharedLibrary.Enums.TransactionFinancialStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class TransactionListModel {
    @Id
    private int id;
    
    private String fileName;
    private String dateCreated;
    private String timeCreated;
    private String dateUpdated;
    private String timeUpdated;
    private String descriptions;
    private BigDecimal totalPositiveItemsSum;
    private BigDecimal totalNegativeItemsSum;

    public String getDateTimeCreated() {
        return timeCreated + " " + dateCreated;
    }

    public String getDateTimeUpdated() {
        return timeUpdated + " " + dateUpdated;
    }

    public BigDecimal getTotalBalance() {
        return totalPositiveItemsSum.add(totalNegativeItemsSum);
    }

    public TransactionFinancialStatus getTransactionFinancialStatus() {
        if (getTotalBalance().compareTo(BigDecimal.ZERO) == 0) {
            return TransactionFinancialStatus.Balanced;
        } else if (getTotalBalance().compareTo(BigDecimal.ZERO) > 0) {
            return TransactionFinancialStatus.Positive;
        } else {
            return TransactionFinancialStatus.Negative;
        }
    }
}
