package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import lombok.Data;

import com.safadana.AvazehRetailManagement.SharedLibrary.Helpers.Helpers;

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
    private String title;
    private long amount;
    private String countString = "1";
    private String dateCreated;
    private String timeCreated;
    private String dateUpdated;
    private String timeUpdated;
    private String descriptions;

    public String getDateTimeCreated() {
        return getTimeCreated() + " " + getDateCreated();
    }

    public String getDateTimeUpdated() {
        return getTimeUpdated() + " " + getDateUpdated();
    }

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