package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecentSellPriceModel {
    private long sellPrice;
    private String dateSold;

    @JsonIgnore
    public String getRecordName() {
        return sellPrice + " : " + dateSold;
    }
}
