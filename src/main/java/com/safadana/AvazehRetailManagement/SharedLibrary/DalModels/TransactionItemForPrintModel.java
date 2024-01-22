package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import lombok.Data;

@Data
public class TransactionItemForPrintModel {
    private Long id;
    private String title;
    private long amount;
    private String countString;
    private double totalPrice;
    private String dateCreated;
    private String timeCreated;
    private String dateUpdated;
    private String timeUpdated;
    private String descriptions;
}