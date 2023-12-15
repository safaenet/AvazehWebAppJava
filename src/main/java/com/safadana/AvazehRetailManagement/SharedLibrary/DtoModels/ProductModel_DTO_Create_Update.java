package com.safadana.AvazehRetailManagement.SharedLibrary.DtoModels;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ProductModel_DTO_Create_Update {
    @NotNull(message = "Field is required")
    @Size(max = 100, message = "Maximum length is 100")
    private String productName;

    @NotNull(message = "Field is required")
    private long buyPrice;

    @NotNull(message = "Field is required")
    private long sellPrice;

    @Size(max = 15, message = "Maximum length is 15")
    private String barcode;

    @Size(max = 50, message = "Maximum length is 50")
    private String countString = "0";

    private String descriptions;

    private boolean isActive = true;
}
