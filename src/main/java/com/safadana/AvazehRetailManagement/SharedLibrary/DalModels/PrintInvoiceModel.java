package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import java.util.List;

import com.safadana.AvazehRetailManagement.SharedLibrary.SecurityAndSettingsModels.PrintSettingsModel;

import lombok.Data;

@Data
public class PrintInvoiceModel {
    private int invoiceId;
    private List<InvoiceItemForPrintModel> products;
    private int customerId;
    private String customerFullName;
    private String customerPhoneNumber;
    private String invoiceDateCreated;
    private String customerPostAddress;
    private String customerDescription = "";
    private String invoiceDescription = "";
    private String invoiceFinStatus;
    private double totalBalance;
    private double totalDiscountAmount;
    private double totalItemsSellSum;
    private double totalInvoiceSum;
    private double totalPayments;

    private double customerPreviousBalance;
    private int invoiceType;
    private boolean printInvoiceDescription;
    private boolean printCustomerDescription;
    private boolean printUserDescription;
    private boolean printCustomerPostAddress;
    private boolean printInvoiceId = true;
    private boolean printDate = true;
    private boolean printCustomerPhoneNumber = true;
    private PrintSettingsModel printSettings = new PrintSettingsModel();
}