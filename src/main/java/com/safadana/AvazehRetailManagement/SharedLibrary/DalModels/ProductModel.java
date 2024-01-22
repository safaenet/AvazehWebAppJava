package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence-generator")
    @GenericGenerator(name = "sequence-generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
            @Parameter(name = "sequence_name", value = "user_sequence"),
            @Parameter(name = "initial_value", value = "1"),
            @Parameter(name = "increment_size", value = "1")
    })
    private Long id;

    @Column(length = 100)
    private String productName;

    @Column(nullable = false)
    private long buyPrice = 0;

    @Column(nullable = false)
    private long sellPrice = 0;

    @Column(length = 15)
    private String barcode;

    @Column(length = 50)
    private String countString = "0";

    @Column(length = 20, nullable = false)
    private String dateCreated;

    @Column(length = 20)
    private String dateUpdated;

    @Column(columnDefinition="TEXT")
    private String descriptions;

    @Column(nullable = false)
    private boolean isActive = true;
}