package com.safadana.AvazehRetailManagement.Models;

import com.safadana.AvazehRetailManagement.Enums.TransactionFinancialStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionListModel {
    private Long id;
    private String fileName;
    private String dateCreated;
    private String dateUpdated;
    private String descriptions;
    private double totalPositiveItemsSum;
    private double totalNegativeItemsSum;
    private TransactionFinancialStatus finStatus;
}
