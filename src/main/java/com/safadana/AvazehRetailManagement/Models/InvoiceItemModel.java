package com.safadana.AvazehRetailManagement.Models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "invoiceitems")
@Data
public class InvoiceItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence-generator")
    @GenericGenerator(name = "sequence-generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
            @Parameter(name = "sequence_name", value = "user_sequence_invoiceitems"),
            @Parameter(name = "initial_value", value = "1"),
            @Parameter(name = "increment_size", value = "1")
    })
    private Long id;

    private Long invoiceId;

    @ManyToOne(optional = false)
    private ProductModel product;

    @Column(nullable = false)
    private long buyPrice;

    @Column(nullable = false)
    private long sellPrice;

    @Column(length = 50, nullable = false)
    private String countString = "1";
    
    @Column(nullable = false)
    private double countValue = 1;

    @ManyToOne
    private ProductUnitModel unit;

    @Column(length = 20, nullable = false)
    private String dateCreated;

    @Column(length = 20)
    private String dateUpdated;

    @Column(nullable = false)
    private boolean delivered;

    @Column(length = 50)
    private String descriptions;
}