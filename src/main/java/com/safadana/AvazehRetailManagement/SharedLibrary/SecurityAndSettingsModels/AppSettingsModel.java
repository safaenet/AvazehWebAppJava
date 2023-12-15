package com.safadana.AvazehRetailManagement.SharedLibrary.SecurityAndSettingsModels;

import lombok.Data;

@Data
public class AppSettingsModel {
    private GeneralSettingsModel generalSettings;
    private PrintSettingsModel printSettings;
}
