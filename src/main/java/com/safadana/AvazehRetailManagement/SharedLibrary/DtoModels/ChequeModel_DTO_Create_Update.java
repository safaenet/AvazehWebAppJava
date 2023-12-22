package com.safadana.AvazehRetailManagement.SharedLibrary.DtoModels;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.safadana.AvazehRetailManagement.SharedLibrary.Enums.ChequeStatus;

@Data
public class ChequeModel_DTO_Create_Update {

    @NotBlank(message = "Field is required")
    @Size(max = 50, message = "Maximum length is 50")
    private String drawer;

    @NotBlank(message = "Field is required")
    @Size(max = 50, message = "Maximum length is 50")
    private String orderer;

    @NotBlank(message = "Field is required")
    private long payAmount;

    @Size(max = 100, message = "Maximum length is 100")
    private String about;

    @NotBlank(message = "Field is required")
    private String issueDate;

    @NotBlank(message = "Field is required")
    private String dueDate;

    @NotBlank(message = "Field is required")
    @Size(max = 50, message = "Maximum length is 50")
    private String bankName;

    @Size(max = 25, message = "Maximum length is 25")
    private String serial;

    @Size(max = 20, message = "Maximum length is 20")
    private String identifier; //Sayyaad Code

    private String descriptions;
    private ChequeStatus statusType;

    @Size(max = 20, message = "Maximum length is 20")
    private ChequeStatus statusDate;

    @Size(max = 50, message = "Maximum length is 50")
    private ChequeStatus statusText;
}