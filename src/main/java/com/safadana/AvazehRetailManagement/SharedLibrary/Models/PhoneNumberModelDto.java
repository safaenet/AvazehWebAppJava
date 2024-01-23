package com.safadana.AvazehRetailManagement.SharedLibrary.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumberModelDto {
    private Long id;
    private String phoneNumber;
}