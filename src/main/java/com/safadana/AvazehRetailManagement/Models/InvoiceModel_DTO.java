package com.safadana.AvazehRetailManagement.Models;

import com.safadana.AvazehRetailManagement.Enums.DiscountTypes;
import lombok.Data;

@Data
public class InvoiceModel_DTO {
    private Long id;
    private CustomerModel customer;
    private String about;
    private String dateCreated;
    private String dateUpdated;
    // private List<InvoiceItemModel> items;
    // private List<InvoicePaymentModel> payments;
    private DiscountTypes discountType = DiscountTypes.AMOUNT;
    private double discountValue = 0;
    private String descriptions;
    private Long prevInvoiceId;
    private double prevInvoiceBalance;
    private Long fwdInvoiceId;
}