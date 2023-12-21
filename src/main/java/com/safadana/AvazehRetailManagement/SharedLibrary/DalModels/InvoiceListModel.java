package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.safadana.AvazehRetailManagement.SharedLibrary.Enums.*;

import lombok.Data;

@Data
public class InvoiceListModel {
    private int id;
    private int customerId;
    private String customerFullName;
    private String about;
    private String dateCreated;
    private String dateUpdated;
    private double totalInvoiceSum;
    private double totalInvoicePayments;
    private InvoiceLifeStatus lifeStatus;
    private int prevInvoiceId;
    private double prevInvoiceBalance;
    private int fwdInvoiceId;

    @JsonIgnore
    public double getTotalInvoiceBalance() {
        return totalInvoiceSum - totalInvoicePayments;
    }

    @JsonIgnore
    public double getTotalBalance() {
        return getTotalInvoiceBalance() + prevInvoiceBalance;
    }

    @JsonIgnore
    public String getInvoiceTitle() {
        return (about == null || about.isEmpty()) ? customerFullName : customerFullName + " - " + about;
    }

    @JsonIgnore
    public InvoiceFinancialStatus getInvoiceFinancialStatus() {
        return getTotalBalance() == 0 ? InvoiceFinancialStatus.BALANCED
                : getTotalBalance() > 0 ? InvoiceFinancialStatus.DEPTOR : InvoiceFinancialStatus.CREDITOR;
    }
}