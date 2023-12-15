package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

import com.safadana.AvazehRetailManagement.SharedLibrary.Enums.TransactionFinancialStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transactions")
@Data
public class TransactionModel {
    @Id
    private int id;

    private String fileName;
    private String dateCreated;
    private String timeCreated;
    private String dateUpdated;
    private String timeUpdated;
    private List<TransactionItemModel> items;
    private String descriptions;
    private BigDecimal totalPositiveItemsSum;
    private BigDecimal totalNegativeItemsSum;

    public BigDecimal getPositiveItemsSum() {
        if (items == null) {
            return BigDecimal.ZERO;
        }
        return this.items.stream().filter(i -> i.getTotalValue().compareTo(BigDecimal.ZERO) > 0)
                .map(TransactionItemModel::getTotalValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getNegativeItemsSum() {
        if (this.items == null) {
            return BigDecimal.ZERO;
        }
        return this.items.stream().filter(i -> i.getTotalValue().compareTo(BigDecimal.ZERO) < 0)
                .map(TransactionItemModel::getTotalValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getBalance() {
        return getPositiveItemsSum().add(getNegativeItemsSum());
    }

    public BigDecimal getTotalBalance() {
        return this.totalPositiveItemsSum.add(this.totalNegativeItemsSum);
    }

    public TransactionFinancialStatus getTransactionFinancialStatus() {
        if (getBalance().compareTo(BigDecimal.ZERO) == 0) {
            return TransactionFinancialStatus.Balanced;
        } else if (getBalance().compareTo(BigDecimal.ZERO) > 0) {
            return TransactionFinancialStatus.Positive;
        } else {
            return TransactionFinancialStatus.Negative;
        }
    }
}
