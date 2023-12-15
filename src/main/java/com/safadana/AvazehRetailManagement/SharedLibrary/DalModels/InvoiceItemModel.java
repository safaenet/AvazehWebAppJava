package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import com.safadana.AvazehRetailManagement.SharedLibrary.Helpers.Helpers;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "invoiceitems")
@Data
public class InvoiceItemModel {
    @Id
    private int id;

    private int invoiceId;
    @ManyToOne(cascade = CascadeType.ALL)
    private ProductModel product;
    private long buyPrice;
    private long sellPrice;
    private String barCode;
    private String countString = "1";
    @ManyToOne(cascade = CascadeType.ALL)
    private ProductUnitModel unit;
    private String dateCreated;
    private String timeCreated;
    private String dateUpdated;
    private String timeUpdated;
    private boolean delivered;
    private String descriptions;

    public String getDateTimeCreated() {
        return timeCreated + " " + dateCreated;
    }

    public String getDateTimeUpdated() {
        return timeUpdated + " " + dateUpdated;
    }

    public double getCountValue() {
        return Helpers.evaluateExpression(countString);
    }

    public boolean getIsCountStringValid() {
        try {
            Helpers.evaluateExpression(countString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getCountPlusUnit() {
        String result = String.valueOf(getCountValue());
        if (unit != null && unit.getId() != 0) {
            result += " " + unit.getUnitName();
        } else {
            result += " واحد";
        }
        return result;
    }

    public double getTotalBuyValue() {
        return getCountValue() * buyPrice;
    }

    public double getTotalSellValue() {
        return getCountValue() * sellPrice;
    }

    public double getNetProfit() {
        return getTotalSellValue() - getTotalBuyValue();
    }
}