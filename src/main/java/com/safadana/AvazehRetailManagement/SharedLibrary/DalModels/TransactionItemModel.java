package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transactionitems")
@Data
public class TransactionItemModel {
    @Id
    private int id;

    private int transactionId;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(nullable = false)
    private long amount;

    @Column(length = 50, nullable = false)
    private String countString = "1";

    @Column(nullable = false)
    private double countValue = 1;

    @Column(length = 20, nullable = false)
    private String dateCreated;

    @Column(length = 20)
    private String dateUpdated;

    @Column(length = 50)
    private String descriptions;
}