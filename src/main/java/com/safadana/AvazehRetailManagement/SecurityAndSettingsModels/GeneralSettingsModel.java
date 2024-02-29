package com.safadana.AvazehRetailManagement.SecurityAndSettingsModels;

import lombok.Data;

@Data
public class GeneralSettingsModel {
    private String companyName = "آوازه";
    private int barcodeAddItemCount = 1;
    private boolean canHaveDuplicateItemsInInvoice = true;
}
