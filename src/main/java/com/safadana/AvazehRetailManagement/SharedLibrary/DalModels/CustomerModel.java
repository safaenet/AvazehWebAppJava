package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "customers")
@Data
public class CustomerModel {
    @Id
    private int id;

    private String firstName;
    private String lastName;
    private String companyName;
    private String emailAddress;
    private String postAddress;
    private String dateJoined;
    private String descriptions;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "customerId")
    private List<PhoneNumberModel> phoneNumbers;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
