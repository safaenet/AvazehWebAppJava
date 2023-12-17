package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class ProductModel {

    @Id
    private int id;

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