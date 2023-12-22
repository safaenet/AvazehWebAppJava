package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import com.safadana.AvazehRetailManagement.SharedLibrary.Enums.ChequeStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "cheques")
@Data
public class ChequeModel {
    @Id
    private int id;

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
