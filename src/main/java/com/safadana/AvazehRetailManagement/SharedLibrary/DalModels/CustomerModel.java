package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.Email;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Column(length = 50)
    private String companyName;

    @Column(length = 50)
    @Email
    private String emailAddress;

    @Column(columnDefinition="TEXT")
    private String postAddress;

    @CreatedDate
    private LocalDateTime dateJoined;

    @Column(columnDefinition="TEXT")
    private String descriptions;
    
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "customerId")
    private List<PhoneNumberModel> phoneNumbers;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
