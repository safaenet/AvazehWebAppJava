package com.safadana.AvazehRetailManagement.DAO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.safadana.AvazehRetailManagement.Models.ItemsForComboBox;
import com.safadana.AvazehRetailManagement.Models.ProductModel;

@Repository
public interface ProductDAO extends JpaRepository<ProductModel, Long> {
    ProductModel findByBarcode(String barcode);

    @Async
    @Query("FROM ProductModel WHERE UPPER(productName) LIKE ?1 " +
            "OR CAST(id AS text) LIKE ?1 " +
            "OR UPPER(descriptions) LIKE ?1 " +
            "OR UPPER(barcode) LIKE ?1 " +
            "OR dateCreated LIKE ?1 " +
            "OR dateUpdated LIKE ?1")
    CompletableFuture<Page<ProductModel>> findByMany(String searchText, Pageable pageable);

    @Async
    @Query("SELECT NEW com.safadana.AvazehRetailManagement.Models.ItemsForComboBox(p.id AS id, p.productName AS itemName) FROM ProductModel p WHERE p.isActive = true")
    CompletableFuture<List<ItemsForComboBox>> getProductItems();
}
