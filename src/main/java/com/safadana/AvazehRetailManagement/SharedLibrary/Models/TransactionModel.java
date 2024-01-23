package com.safadana.AvazehRetailManagement.SharedLibrary.Models;

import lombok.Data;

import java.util.List;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence-generator")
    @GenericGenerator(name = "sequence-generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
            @Parameter(name = "sequence_name", value = "user_sequence"),
            @Parameter(name = "initial_value", value = "1"),
            @Parameter(name = "increment_size", value = "1")
    })
    private long id;

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
