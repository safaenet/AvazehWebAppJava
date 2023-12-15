package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class UserDescriptionModel {
    @Id
    private int id;
    
    private String descriptionTitle;
    private String descriptionText;
}