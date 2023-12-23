package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import com.safadana.AvazehRetailManagement.SharedLibrary.Helpers.Helpers;

import jakarta.persistence.Column;
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

    @ManyToOne(optional = false)
    private ProductModel product;

    @Column(nullable = false)
    private long buyPrice;

    @Column(nullable = false)
    private long sellPrice;

    @Column(length = 50, nullable = false)
    private String countString = "1";
    
    @Column(nullable = false)
    private double countValue = 1;

    @ManyToOne
    private ProductUnitModel unit;

    @Column(length = 20, nullable = false)
    private String dateCreated;

    @Column(length = 20)
    private String dateUpdated;

    @Column(nullable = false)
    private boolean delivered;

    @Column(length = 50)
    private String descriptions;

    // public double getCountValue() {
    //     return Helpers.evaluateExpression(countString);
    // }

    public boolean isCountStringValid() {
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