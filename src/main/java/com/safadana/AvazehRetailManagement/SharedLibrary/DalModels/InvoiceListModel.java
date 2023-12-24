package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.safadana.AvazehRetailManagement.SharedLibrary.Enums.*;

import lombok.Data;

@Data
public class InvoiceListModel {
    private int id;
    private int customerid;
    private String customerfullname;
    private String about;
    private String datecreated;
    private String dateupdated;
    private boolean isactive;
    private String descriptions;
    private double totalinvoicesum;
    private double totalinvoicepayments;
    private int previnvoiceid;
    private double previnvoicebalance;
    private int fwdinvoiceid;

    @JsonIgnore
    public double getTotalInvoiceBalance() {
        return totalinvoicesum - totalinvoicepayments;
    }

    @JsonIgnore
    public double getTotalBalance() {
        return getTotalInvoiceBalance() + previnvoicebalance;
    }

    @JsonIgnore
    public String getInvoiceTitle() {
        return (about == null || about.isEmpty()) ? customerfullname : customerfullname + " - " + about;
    }

    @JsonIgnore
    public InvoiceFinancialStatus getInvoiceFinancialStatus() {
        return getTotalBalance() == 0 ? InvoiceFinancialStatus.BALANCED
                : getTotalBalance() > 0 ? InvoiceFinancialStatus.DEPTOR : InvoiceFinancialStatus.CREDITOR;
    }
}