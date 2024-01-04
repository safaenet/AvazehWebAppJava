package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import lombok.Data;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "transactions")
@Data
public class TransactionModel {
    @Id
    private int id;

    @Column(length = 100, nullable = false)
    private String fileName;

    @Column(length = 20, nullable = false)
    private String dateCreated;
    
    @Column(length = 20)
    private String dateUpdated;
    
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "transactionId")
    private List<TransactionItemModel> items; //List of Items for one Page.

    @Column(columnDefinition="TEXT")
    private String descriptions;

    @Transient
    private double totalPositiveItemsSum;

    @Transient
    private double totalNegativeItemsSum;
}
