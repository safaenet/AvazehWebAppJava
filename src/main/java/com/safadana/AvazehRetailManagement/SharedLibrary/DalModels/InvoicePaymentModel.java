package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import jakarta.persistence.Column;
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

    @Column(length = 20, nullable = false)
    private String dateCreated;

    @Column(length = 20)
    private String dateUpdated;

    @Column(nullable = false)
    private double payAmount;

    @Column(columnDefinition="TEXT")
    private String descriptions;
}