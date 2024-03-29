package com.safadana.AvazehRetailManagement.Models;

import lombok.Data;

import java.util.List;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "invoices")
@Data
public class InvoiceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence-generator-invoices")
    @GenericGenerator(name = "sequence-generator-invoices", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
            @Parameter(name = "sequence_name", value = "user_sequence_invoices"),
            @Parameter(name = "initial_value", value = "1000"),
            @Parameter(name = "increment_size", value = "1")
    })
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private CustomerModel customer;

    @Column(length = 50)
    private String about;

    @Column(length = 20, nullable = false)
    private String dateCreated;

    @Column(length = 20)
    private String dateUpdated;

    @OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true)
    @JoinColumn(name = "invoiceId")
    private List<InvoiceItemModel> items;

    @OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true)
    @JoinColumn(name = "invoiceId")
    private List<InvoicePaymentModel> payments;

    @Column(nullable = false)
    private int discountType = 1; // 0: PERCENT, 1:AMOUNT

    @Column(nullable = false)
    private double discountValue = 0;

    @Column(columnDefinition = "TEXT")
    private String descriptions;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prevInvoiceId")
    private InvoiceModel prevInvoice;
}