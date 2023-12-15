package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "invoicepayments")
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