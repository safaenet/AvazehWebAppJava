package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import lombok.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.safadana.AvazehRetailManagement.SharedLibrary.Enums.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "invoices")
@Data
public class InvoiceModel {
    @Id
    private int id;

    @ManyToOne(optional = false)
    private CustomerModel customer;

    @Column(length = 50)
    private String about;

    @Column(length = 20, nullable = false)
    private String dateCreated;

    @Column(length = 20)
    private String dateUpdated;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "invoiceId")
    private List<InvoiceItemModel> items;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "invoiceId")
    private List<InvoicePaymentModel> payments;

    @Column(nullable = false)
    private DiscountTypes discountType = DiscountTypes.AMOUNT;

    @Column(nullable = false)
    private double discountValue = 0;

    @Column(columnDefinition="TEXT")
    private String descriptions;

    @Column(nullable = false)
    private boolean isActive = true;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prevInvoiceId")
    private InvoiceModel prevInvoice;

    @JsonIgnore
    public double getTotalItemsBuySum() {
        return items == null || items.isEmpty() ? 0
                : items.stream().mapToDouble(InvoiceItemModel::getTotalBuyValue).sum();
    }

    @JsonIgnore
    public double getTotalItemsSellSum() {
        return items == null || items.isEmpty() ? 0
                : items.stream().mapToDouble(InvoiceItemModel::getTotalSellValue).sum();
    }

    @JsonIgnore
    public double getTotalDiscountAmount() {
        return discountType == DiscountTypes.PERCENT ? (getTotalItemsSellSum() * discountValue / 100) : discountValue;
    }

    @JsonIgnore
    public double getTotalInvoiceSum() {
        return getTotalItemsSellSum() - getTotalDiscountAmount();
    }

    @JsonIgnore
    public double getTotalPayments() {
        return payments == null || payments.isEmpty() ? 0
                : payments.stream().mapToDouble(InvoicePaymentModel::getPayAmount).sum();
    }

    @JsonIgnore
    public double getTotalInvoiceBalance() {
        return getTotalInvoiceSum() - getTotalPayments();
    }

    @JsonIgnore
    public double getTotalBalance() {
        if(prevInvoice == null) return getTotalInvoiceBalance();
        else return getTotalInvoiceBalance() + prevInvoice.getTotalInvoiceBalance();
    }

    @JsonIgnore
    public double getNetProfit() {
        return getTotalInvoiceSum() - getTotalItemsBuySum();
    }

    @JsonIgnore
    public double getCurrentProfit() {
        return getNetProfit() - getTotalInvoiceBalance();
    }

    @JsonIgnore
    public InvoiceFinancialStatus getInvoiceFinancialStatus() {
        return getTotalBalance() == 0 ? InvoiceFinancialStatus.BALANCED
                : getTotalBalance() > 0 ? InvoiceFinancialStatus.DEPTOR : InvoiceFinancialStatus.CREDITOR;
    }
}