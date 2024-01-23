package com.safadana.AvazehRetailManagement.SharedLibrary.Models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerModelDto {
    private Long id;
    private String fullName;
    private String companyName;
    private String emailAddress;
    private String postAddress;
    private String dateJoined;
    private String descriptions;
    private List<PhoneNumberModelDto> phoneNumbers;
}