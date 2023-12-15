package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

import com.safadana.AvazehRetailManagement.SharedLibrary.Enums.TransactionFinancialStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "transactionId")
    private List<TransactionItemModel> items;
    private String descriptions;
    private double totalPositiveItemsSum;
    private double totalNegativeItemsSum;

    public double getPositiveItemsSum() {
        if (items == null) {
            return 0;
        }
        return this.items.stream().filter(i -> i.getTotalValue() > 0)
                .mapToDouble(TransactionItemModel::getTotalValue).sum();
    }

    public double getNegativeItemsSum() {
        if (this.items == null) {
            return 0;
        }
        return this.items.stream().filter(i -> i.getTotalValue() < 0)
                .mapToDouble(TransactionItemModel::getTotalValue).sum();
    }

    public double getBalance() {
        return getPositiveItemsSum() + getNegativeItemsSum();
    }

    public double getTotalBalance() {
        return this.totalPositiveItemsSum + this.totalNegativeItemsSum;
    }

    public TransactionFinancialStatus getTransactionFinancialStatus() {
        if (getBalance() == 0) {
            return TransactionFinancialStatus.Balanced;
        } else if (getBalance() > 0) {
            return TransactionFinancialStatus.Positive;
        } else {
            return TransactionFinancialStatus.Negative;
        }
    }
}
