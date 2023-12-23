package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import lombok.Data;

import java.util.List;

import com.safadana.AvazehRetailManagement.SharedLibrary.Enums.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

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

    @OneToOne
    @JoinColumn(name = "prevInvoiceId")
    private InvoiceModel prevInvoice;

    public double getTotalItemsBuySum() {
        return items == null || items.isEmpty() ? 0
                : items.stream().mapToDouble(InvoiceItemModel::getTotalBuyValue).sum();
    }

    public double getTotalItemsSellSum() {
        return items == null || items.isEmpty() ? 0
                : items.stream().mapToDouble(InvoiceItemModel::getTotalSellValue).sum();
    }

    public double getTotalDiscountAmount() {
        return discountType == DiscountTypes.PERCENT ? (getTotalItemsSellSum() * discountValue / 100) : discountValue;
    }

    public double getTotalInvoiceSum() {
        return getTotalItemsSellSum() - getTotalDiscountAmount();
    }

    public double getTotalPayments() {
        return payments == null || payments.isEmpty() ? 0
                : payments.stream().mapToDouble(InvoicePaymentModel::getPayAmount).sum();
    }

    public double getTotalInvoiceBalance() {
        return getTotalInvoiceSum() - getTotalPayments();
    }

    public double getTotalBalance() {
        if(prevInvoice == null) return getTotalInvoiceBalance();
        else return getTotalInvoiceBalance() + prevInvoice.getTotalInvoiceBalance();
    }

    public double getNetProfit() {
        return getTotalInvoiceSum() - getTotalItemsBuySum();
    }

    public double getCurrentProfit() {
        return getNetProfit() - getTotalInvoiceBalance();
    }

    public InvoiceFinancialStatus getInvoiceFinancialStatus() {
        return getTotalBalance() == 0 ? InvoiceFinancialStatus.BALANCED
                : getTotalBalance() > 0 ? InvoiceFinancialStatus.DEPTOR : InvoiceFinancialStatus.CREDITOR;
    }
}