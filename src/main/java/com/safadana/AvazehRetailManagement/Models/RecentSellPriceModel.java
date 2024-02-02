package com.safadana.AvazehRetailManagement.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecentSellPriceModel {
    private long sellPrice;
    private String dateSold;
}
