package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.safadana.AvazehRetailManagement.SharedLibrary.Enums.TransactionFinancialStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "transactions")
@Data
public class TransactionModel {
    @Id
    private int id;

    @Column(length = 100, nullable = false)
    private String fileName;

    @CreatedDate
    private LocalDateTime dateCreated;
    
    @LastModifiedDate
    private LocalDateTime dateUpdated;
    
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "transactionId")
    private List<TransactionItemModel> items;

    @Column(columnDefinition="TEXT")
    private String descriptions;

    @Transient
    private double totalPositiveItemsSum;

    @Transient
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
