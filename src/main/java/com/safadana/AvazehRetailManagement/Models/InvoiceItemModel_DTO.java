package com.safadana.AvazehRetailManagement.Models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvoiceItemModel_DTO {
    private Long id;
    private Long invoiceId;
    private Long productId;
    private String productName;
    private long buyPrice;
    private long sellPrice;
    private String countString = "1";
    private double countValue = 1;
    private String dateCreated;
    private String dateUpdated;
    private boolean delivered;
    private String descriptions;
}
