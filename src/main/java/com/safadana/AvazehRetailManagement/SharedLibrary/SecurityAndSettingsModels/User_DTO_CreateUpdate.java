package com.safadana.AvazehRetailManagement.SharedLibrary.SecurityAndSettingsModels;

import lombok.Data;

@Data
public class User_DTO_CreateUpdate {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private UserPermissionsModel permissions;
    private UserSettingsModel settings;
}
