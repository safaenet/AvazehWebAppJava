package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "userdescriptions")
public class UserDescriptionModel {
    @Id
    private int id;
    
    @Column(length = 30)
    private String descriptionTitle;

    @Column(columnDefinition="TEXT", nullable = false)
    private String descriptionText;
}