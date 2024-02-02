package com.safadana.AvazehRetailManagement.Models;

import java.util.List;

import com.safadana.AvazehRetailManagement.SecurityAndSettingsModels.PrintSettingsModel;

import lombok.Data;

@Data
public class PrintInvoiceModel {
    private Long invoiceId;
    private List<InvoiceItemForPrintModel> products;
    private Long customerId;
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