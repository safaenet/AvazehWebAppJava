package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import lombok.Data;

import java.util.List;

import com.safadana.AvazehRetailManagement.SharedLibrary.Enums.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "invoices")
@Data
public class InvoiceModel {
    private int id;
    private CustomerModel customer;
    private String about;
    private String dateCreated;
    private String timeCreated;
    private String dateUpdated;
    private String timeUpdated;
    private List<InvoiceItemModel> items;
    private List<InvoicePaymentModel> payments;
    private DiscountTypes discountType = DiscountTypes.Amount;
    private double discountValue;
    private String descriptions;
    private InvoiceLifeStatus lifeStatus;
    private int prevInvoiceId;
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