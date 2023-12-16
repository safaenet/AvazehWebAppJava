package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.safadana.AvazehRetailManagement.SharedLibrary.Enums.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @CreatedDate
    private LocalDateTime dateCreated;

    @LastModifiedDate
    private LocalDateTime dateUpdated;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "invoiceId")
    private List<InvoiceItemModel> items;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "invoiceId")
    private List<InvoicePaymentModel> payments;

    private DiscountTypes discountType = DiscountTypes.Amount;

    private double discountValue;

    @Column(columnDefinition="TEXT")
    private String descriptions;

    @Column(nullable = false)
    private InvoiceLifeStatus lifeStatus;

    @Column(nullable = true)
    private int prevInvoiceId;

    @Column(nullable = true)
    private double prevInvoiceBalance;

    public double getTotalItemsBuySum() {
        return items == null || items.isEmpty() ? 0
                : items.stream().mapToDouble(InvoiceItemModel::getTotalBuyValue).sum();
    }

    public double getTotalItemsSellSum() {
        return items == null || items.isEmpty() ? 0
                : items.stream().mapToDouble(InvoiceItemModel::getTotalSellValue).sum();
    }

    public double getTotalDiscountAmount() {
        return discountType == DiscountTypes.Percent ? (getTotalItemsSellSum() * discountValue / 100) : discountValue;
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
        return getTotalInvoiceBalance() + prevInvoiceBalance;
    }

    public double getNetProfit() {
        return getTotalInvoiceSum() - getTotalItemsBuySum();
    }

    public double getCurrentProfit() {
        return getNetProfit() - getTotalInvoiceBalance();
    }

    public InvoiceFinancialStatus getInvoiceFinancialStatus() {
        return getTotalBalance() == 0 ? InvoiceFinancialStatus.Balanced
                : getTotalBalance() > 0 ? InvoiceFinancialStatus.Deptor : InvoiceFinancialStatus.Creditor;
    }
}