package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "productunits")
@Data
public class ProductUnitModel {
    @Id
    private int id;

    @Column(length = 10, nullable = false)
    private String UnitName;
}
