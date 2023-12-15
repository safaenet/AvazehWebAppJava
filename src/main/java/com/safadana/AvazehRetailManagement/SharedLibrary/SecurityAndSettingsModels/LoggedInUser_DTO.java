package com.safadana.AvazehRetailManagement.SharedLibrary.SecurityAndSettingsModels;

import lombok.Data;

@Data
public class LoggedInUser_DTO {
    private int id;
    private String token;
    private String dateCreated;
    private String lastLoginDate;
    private UserSettingsModel userSettings;
    private PrintSettingsModel printSettings;
    private GeneralSettingsModel generalSettings;
}
