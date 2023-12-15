package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import com.safadana.AvazehRetailManagement.SharedLibrary.Enums.*;

import lombok.Data;

@Data
public class InvoiceListModel {
    private int id;
    private int customerId;
    private String customerFullName;
    private String about;
    private String dateCreated;
    private String timeCreated;
    private String dateUpdated;
    private String timeUpdated;
    private double totalInvoiceSum;
    private double totalPayments;
    private InvoiceLifeStatus lifeStatus;
    private int prevInvoiceId;
    private double prevInvoiceBalance;
    private int fwdInvoiceId;

    public double getTotalInvoiceBalance() {
        return totalInvoiceSum - totalPayments;
    }

    public double getTotalBalance() {
        return getTotalInvoiceBalance() + prevInvoiceBalance;
    }

    public String getInvoiceTitle() {
        return (about == null || about.isEmpty()) ? customerFullName : customerFullName + " - " + about;
    }

    public String getDateTimeCreated() {
        return timeCreated + " " + dateCreated;
    }

    public String getDateTimeUpdated() {
        return timeUpdated + " " + dateUpdated;
    }

    public InvoiceFinancialStatus getInvoiceFinancialStatus() {
        return getTotalBalance() == 0 ? InvoiceFinancialStatus.Balanced
                : getTotalBalance() > 0 ? InvoiceFinancialStatus.Deptor : InvoiceFinancialStatus.Creditor;
    }
}