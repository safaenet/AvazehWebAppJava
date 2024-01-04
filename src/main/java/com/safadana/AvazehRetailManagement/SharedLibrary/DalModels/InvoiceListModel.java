package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceListModel {
    private int id;
    private int customerId;
    private String customerFullName;
    private String about;
    private String dateCreated;
    private String dateUpdated;
    private boolean isActive;
    private String descriptions;
    private double totalInvoiceSum;
    private double totalInvoicePayments;
    private Integer prevInvoiceId;
    private double prevInvoiceBalance;
    private Integer fwdInvoiceId;
}