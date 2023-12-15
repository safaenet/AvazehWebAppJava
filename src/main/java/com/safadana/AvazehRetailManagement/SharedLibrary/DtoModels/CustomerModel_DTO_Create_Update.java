package com.safadana.AvazehRetailManagement.SharedLibrary.DtoModels;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CustomerModel_DTO_Create_Update {

    @NotBlank(message = "Field is required")
    @Size(max = 50, message = "Maximum length is 50")
    private String firstName;

    @Size(max = 50, message = "Maximum length is 50")
    private String lastName;

    @Size(max = 50, message = "Maximum length is 50")
    private String companyName;

    @Size(max = 100, message = "Maximum length is 100")
    @Email(message = "Email is not valid")
    private String emailAddress;

    private String postAddress;

    private String dateJoined;

    private String descriptions;

}
