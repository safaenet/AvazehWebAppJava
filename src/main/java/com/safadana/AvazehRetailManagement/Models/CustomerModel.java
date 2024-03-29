package com.safadana.AvazehRetailManagement.Models;

import javax.validation.constraints.Email;

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
@Table(name = "customers")
@Data
public class CustomerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence-generator-customers")
    @GenericGenerator(name = "sequence-generator-customers", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
            @Parameter(name = "sequence_name", value = "user_sequence_customers"),
            @Parameter(name = "initial_value", value = "1"),
            @Parameter(name = "increment_size", value = "1")
    })
    private Long id;

    @Column(length = 50, nullable = false)
    private String fullName;

    @Column(length = 50)
    private String companyName;
    
    @Column(length = 50)
    private String phoneNumber;

    @Column(length = 50)
    @Email
    private String emailAddress;

    @Column(columnDefinition = "TEXT")
    private String postAddress;

    @Column(length = 20, nullable = false)
    private String dateJoined;

    @Column(columnDefinition = "TEXT")
    private String descriptions;
}
