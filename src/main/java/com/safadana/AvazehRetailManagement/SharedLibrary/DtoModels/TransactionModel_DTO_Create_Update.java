package com.safadana.AvazehRetailManagement.SharedLibrary.DtoModels;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class TransactionModel_DTO_Create_Update {
    @NotEmpty(message = "Field is required")
    @Size(max = 100, message = "Maximum length is 100")
    private String fileName;

    private String dateCreated;
    private String timeCreated;

    @Size(max = 50, message = "Maximum length is 50")
    private String descriptions;
}
