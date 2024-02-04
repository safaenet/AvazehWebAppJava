package com.safadana.AvazehRetailManagement.Models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.safadana.AvazehRetailManagement.Enums.ChequeStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "cheques")
@Data
public class ChequeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence-generator-cheques")
    @GenericGenerator(name = "sequence-generator-cheques", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
            @Parameter(name = "sequence_name", value = "user_sequence_cheques"),
            @Parameter(name = "initial_value", value = "1"),
            @Parameter(name = "increment_size", value = "1")
    })
    private Long id;

    @Column(length = 50, nullable = false)
    private String drawer;

    @Column(length = 50, nullable = false)
    private String orderer;

    @Column(nullable = false)
    private long payAmount;

    @Column(length = 100)
    private String about;

    @Column(nullable = false, length = 20)
    private String issueDate;

    @Column(nullable = false, length = 10)
    private String dueDate;

    @Column(nullable = false, length = 20)
    private String dateCreated; //Date saved in DB

    @Column(length = 50, nullable = false)
    private String bankName;

    @Column(length = 25)
    private String serialNumber;

    @Column(length = 20)
    private String identifier; // Sayyaad Code

    @Column(columnDefinition="TEXT")
    private String descriptions;

    private ChequeStatus statusType;

    @Column(length = 20)
    private String statusDate;

    @Column(length = 50)
    private String statusText;
}
