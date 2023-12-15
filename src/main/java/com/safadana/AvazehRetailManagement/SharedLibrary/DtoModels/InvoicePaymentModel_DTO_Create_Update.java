package com.safadana.AvazehRetailManagement.SharedLibrary.DtoModels;

import lombok.Data;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class InvoicePaymentModel_DTO_Create_Update {

    @NotNull(message = "Invoice ID is required")
    private int invoiceId;

    @Positive(message = "Payment amount must be greater than Zero")
    private BigDecimal payAmount;

    @NotBlank(message = "Description is required")
    private String descriptions;
}
