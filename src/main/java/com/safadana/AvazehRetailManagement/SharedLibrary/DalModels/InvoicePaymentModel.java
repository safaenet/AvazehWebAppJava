package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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

    @CreatedDate
    private LocalDateTime dateCreated;

    @LastModifiedDate
    private LocalDateTime dateUpdated;

    @Column(nullable = false)
    private double payAmount;

    @Column(columnDefinition="TEXT")
    private String descriptions;
}