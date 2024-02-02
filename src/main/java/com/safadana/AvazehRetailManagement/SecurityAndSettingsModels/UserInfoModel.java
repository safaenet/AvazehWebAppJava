package com.safadana.AvazehRetailManagement.SecurityAndSettingsModels;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserInfoModel extends UserInfoBaseModel {
    private UserPermissionsModel permissions;
    private UserSettingsModel settings;
}
