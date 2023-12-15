package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import java.util.List;

import com.safadana.AvazehRetailManagement.SharedLibrary.SecurityAndSettingsModels.PrintSettingsModel;

import lombok.Data;

@Data
public class PrintTransactionModel {
    private int transactionId;
    private List<TransactionItemForPrintModel> items;
    private String fileName;
    private String transactionDateCreated;
    private String transactionDescription = "";
    private String transactionFinStatus;
    private double totalBalance;
    private double totalPositiveItemsSum;
    private double totalNegativeItemsSum;

    private int transactionType;
    private boolean printTransactionDescription;
    private boolean printUserDescription;
    private boolean printTransactionId = true;
    private boolean printDate = true;
    private PrintSettingsModel printSettings = new PrintSettingsModel();
}