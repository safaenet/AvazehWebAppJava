package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import lombok.Data;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.safadana.AvazehRetailManagement.SharedLibrary.Helpers.Helpers;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transactionitems")
@Data
public class TransactionItemModel {
    @Id
    private int id;

    private int transactionId;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(nullable = false)
    private long amount;

    @Column(length = 50, nullable = false)
    private String countString = "1";

    @CreatedDate
    private LocalDateTime dateCreated;

    @LastModifiedDate
    private LocalDateTime dateUpdated;

    @Column(length = 50)
    private String descriptions;

    // public String getDateTimeCreated() {
    //     return getTimeCreated() + " " + getDateCreated();
    // }

    // public String getDateTimeUpdated() {
    //     return getTimeUpdated() + " " + getDateUpdated();
    // }

    public double getCountValue() {
        return Helpers.evaluateExpression(countString);
    }

    public boolean isCountStringValid() {
        try {
            Helpers.evaluateExpression(countString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public double getTotalValue() {
        return getAmount() * getCountValue();
    }
}