package com.safadana.AvazehRetailManagement.DataLibraryCore.DAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.ProductModel;

@Repository
public interface ProductDAO extends JpaRepository<ProductModel, Integer> {
    ProductModel findByBarcode(String barcode);

    @Query("FROM ProductModel WHERE UPPER(productName) LIKE CONCAT('%', UPPER(?1), '%') " +
            "OR UPPER(descriptions) LIKE CONCAT('%', UPPER(?1), '%') " +
            "OR UPPER(barcode) LIKE CONCAT('%', UPPER(?1), '%') " +
            "OR dateCreated LIKE CONCAT('%', ?1, '%') " +
            "OR dateUpdated LIKE CONCAT('%', ?1, '%')")
    Page<ProductModel> findByMany(String productName, Pageable pageable);
}
