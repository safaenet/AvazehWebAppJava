package com.safadana.AvazehRetailManagement.SharedLibrary.DtoModels;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class TransactionItemModel_DTO_Create_Update {
    @NotNull(message = "Field is required")
    private int transactionId;

    @NotNull(message = "Field is required")
    private String title;

    @NotNull(message = "Field is required")
    private long amount;

    @Size(max = 50, message = "Maximum length is 50")
    // @CountStringIsValid
    private String countString = "0";

    @Size(max = 50, message = "Maximum length is 50")
    private String descriptions;
}
