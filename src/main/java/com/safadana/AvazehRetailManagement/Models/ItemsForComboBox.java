package com.safadana.AvazehRetailManagement.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemsForComboBox {
    ItemsForComboBox(Long id, String itemName){
        this.id = id;
        this.itemName = itemName;
    }
    private Long id;
    private String itemName;
    private boolean isChecked;
}
