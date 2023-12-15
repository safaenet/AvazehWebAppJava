package com.safadana.AvazehRetailManagement.SharedLibrary.DtoModels;

import lombok.Data;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.safadana.AvazehRetailManagement.SharedLibrary.Enums.DiscountTypes;
import com.safadana.AvazehRetailManagement.SharedLibrary.Enums.InvoiceLifeStatus;

@Data
public class InvoiceModel_DTO_Create_Update {

    @NotNull(message = "Customer ID is required")
    private int customerId;

    @Size(max = 255, message = "Maximum length is 255")
    private String about;

    private String dateCreated;

    private String timeCreated;

    private DiscountTypes discountType;

    @PositiveOrZero(message = "Discount value must be greater than or equal to Zero")
    private BigDecimal discountValue;

    @Size(max = 255, message = "Maximum length is 255")
    private String descriptions;

    private InvoiceLifeStatus lifeStatus;
}
