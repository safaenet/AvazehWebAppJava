package com.safadana.AvazehRetailManagement.Models;

import lombok.Data;

@Data
public class InvoiceSpecsUpdate_DTO {
    private long id;
    private long customerId;
    private String about;
    private String dateCreated;
    private String dateUpdated;
    private int discountType; // 0: PERCENT, 1:AMOUNT
    private double discountValue;
    private String descriptions;
    private Long prevInvoiceId;
}