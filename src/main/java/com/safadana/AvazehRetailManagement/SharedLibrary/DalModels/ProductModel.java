package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class ProductModel {

    @Id
    private int id;

    private String productName;
    private long buyPrice = 0;
    private long sellPrice = 0;
    private String barcode;
    private String countString = "0";
    private String dateCreated;
    private String timeCreated;
    private String dateUpdated;
    private String timeUpdated;
    private String descriptions;
    private boolean isActive = true;

    public String getDateTimeCreated() {
        return timeCreated + " " + dateCreated;
    }

    public String getDateTimeUpdated() {
        return timeUpdated + " " + dateUpdated;
    }
}