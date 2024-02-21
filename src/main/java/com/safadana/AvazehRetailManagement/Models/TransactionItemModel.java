package com.safadana.AvazehRetailManagement.Models;

import lombok.Data;

import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transactionitems")
@Data
public class TransactionItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence-generator-transactionitems")
    @GenericGenerator(name = "sequence-generator-transactionitems", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
            @Parameter(name = "sequence_name", value = "user_sequence_transactionitems"),
            @Parameter(name = "initial_value", value = "1"),
            @Parameter(name = "increment_size", value = "1")
    })
    private Long id;

    private Long transactionId;

    @Column(length = 100, nullable = false)
    @Size(min = 2, message = "The string must be at least 2 characters long")
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