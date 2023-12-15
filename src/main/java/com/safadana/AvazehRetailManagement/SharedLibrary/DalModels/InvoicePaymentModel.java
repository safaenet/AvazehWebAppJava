package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class InvoicePaymentModel {
    @Id
    private int id;
    
    private int invoiceId;
    private String dateCreated;
    private String timeCreated;
    private String dateUpdated;
    private String timeUpdated;
    private double payAmount;
    private String descriptions;
}