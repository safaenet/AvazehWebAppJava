package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import lombok.Data;

@Data
public class InvoiceItemForPrintModel {
    private int id;
    private String productName;
    private long sellPrice;
    private String countString;
    private double totalPrice;
    private String dateCreated;
    private String timeCreated;
    private String dateUpdated;
    private String timeUpdated;
    private boolean delivered;
    private String descriptions;
    private String productUnitName;
}