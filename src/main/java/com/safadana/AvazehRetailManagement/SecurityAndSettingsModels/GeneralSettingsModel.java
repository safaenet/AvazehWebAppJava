package com.safadana.AvazehRetailManagement.SecurityAndSettingsModels;

import lombok.Data;

import java.util.List;

import com.safadana.AvazehRetailManagement.Models.ProductUnitModel;

@Data
public class GeneralSettingsModel {
    private String companyName = "آوازه";
    private int barcodeAddItemCount = 1;
    private boolean canHaveDuplicateItemsInInvoice = true;
    private List<ProductUnitModel> productUnits;
}
