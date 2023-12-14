package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;
import com.safadana.AvazehRetailManagement.SharedLibrary.Enums.ChequeEventTypes;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class ChequeEventModel {
    @Id
    private int id;

    private int ChequeId;
    private String EventDate;
    private ChequeEventTypes EventType;
    private String EventText;
}
