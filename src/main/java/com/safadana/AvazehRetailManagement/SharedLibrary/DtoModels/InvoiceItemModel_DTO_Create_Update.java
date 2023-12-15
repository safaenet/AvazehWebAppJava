package com.safadana.AvazehRetailManagement.SharedLibrary.DtoModels;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.ProductUnitModel;

@Data
public class InvoiceItemModel_DTO_Create_Update {

    @NotNull(message = "Field is required")
    @PositiveOrZero(message = "Field must be greater than or equal to Zero")
    private int invoiceId;

    @NotNull(message = "Field is required")
    @PositiveOrZero(message = "Field must be greater than or equal to Zero")
    private int productId;

    @NotNull(message = "Field is required")
    @PositiveOrZero(message = "Field must be greater than or equal to Zero")
    private long buyPrice;

    @NotNull(message = "Field is required")
    @PositiveOrZero(message = "Field must be greater than or equal to Zero")
    private long sellPrice;

    @Size(max = 15, message = "Maximum length is 15")
    private String barCode;

    @NotNull(message = "Field is required")
    @Size(max = 50, message = "Maximum length is 50")
    //@CountStringIsValid(message = "Count string is not valid")
    private String countString;

    private ProductUnitModel unit;

    @NotNull(message = "Field is required")
    private boolean delivered;

    @Size(max = 50, message = "Maximum length is 50")
    private String descriptions;
}
