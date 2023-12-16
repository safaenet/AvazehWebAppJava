package com.safadana.AvazehRetailManagement.DataLibraryCore.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.ProductModel;

@Repository
public interface ProductDAO extends JpaRepository<ProductModel, Integer> {
    ProductModel findByBarcode(String barcode);
    List<ProductModel> findByProductNameContains(String text);
}