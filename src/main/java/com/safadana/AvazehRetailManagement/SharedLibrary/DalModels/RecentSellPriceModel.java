package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import lombok.Data;

@Data
public class RecentSellPriceModel {
    private long sellPrice;
    private String dateSold;

    public String getRecordName() {
        return sellPrice + " : " + dateSold;
    }
}
