package com.safadana.AvazehRetailManagement.SharedLibrary.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceListModel {
    private Long id;
    private Long customerId;
    private String customerFullName;
    private String about;
    private String dateCreated;
    private String dateUpdated;
    private boolean isActive;
    private String descriptions;
    private double totalInvoiceSum;
    private double totalInvoicePayments;
    private Long prevInvoiceId;
    private double prevInvoiceBalance;
    private Long fwdInvoiceId;
}