package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "userdescription")
public class UserDescriptionModel {
    @Id
    private int id;
    
    private String descriptionTitle;
    private String descriptionText;
}