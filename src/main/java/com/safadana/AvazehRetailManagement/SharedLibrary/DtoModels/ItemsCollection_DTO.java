package com.safadana.AvazehRetailManagement.SharedLibrary.DtoModels;

import lombok.Data;

import java.util.List;

@Data
public class ItemsCollection_DTO<T> {
    private List<T> items;
    private int pagesCount;
    private int currentPage;
}
