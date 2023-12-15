package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "phonenumbers")
@Data
public class PhoneNumberModel {
    @Id
    private int id;

    private int customerId;
    private String phoneNumber;
}
