package com.safadana.AvazehRetailManagement.SecurityAndSettingsModels;

import lombok.Data;

@Data
public class AppSettingsModel {
    private GeneralSettingsModel generalSettings;
    private PrintSettingsModel printSettings;
}
