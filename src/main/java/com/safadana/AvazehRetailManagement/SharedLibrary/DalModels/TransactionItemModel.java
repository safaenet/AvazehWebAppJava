package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import lombok.Data;

import java.math.BigDecimal;

import com.safadana.AvazehRetailManagement.SharedLibrary.Helpers.Helpers;

@Data
public class TransactionItemModel {
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

    public BigDecimal getCountValue() {
        return BigDecimal.valueOf(Helpers.evaluateExpression(countString));
    }

    public boolean isCountStringValid() {
        try {
            Helpers.evaluateExpression(countString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public BigDecimal getTotalValue() {
        return BigDecimal.valueOf(getAmount()).multiply(getCountValue());
    }
}