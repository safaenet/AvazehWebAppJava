package com.safadana.AvazehRetailManagement.SharedLibrary.DtoModels;

import lombok.Data;

@Data
public class DtoModel<T> {
    private T value;
}
