package com.safadana.AvazehRetailManagement.SharedLibrary.SecurityAndSettingsModels;

import lombok.Data;

@Data
public class UserInfoBaseModel {
    private int Id;
    private String username;
    private String firstName;
    private String lastName;
    private String dateCreated;
    private String lastLoginDate;
    private String lastLoginTime;
    private boolean isActive = true;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
