package com.safadana.AvazehRetailManagement.DAO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.safadana.AvazehRetailManagement.Models.InvoiceItemModel;
import com.safadana.AvazehRetailManagement.Models.ItemsForComboBox;

@Repository
public interface InvoiceItemDAO extends JpaRepository<InvoiceItemModel, Long> {

    void deleteByInvoiceId(Long invoiceId);

    List<InvoiceItemModel> findByInvoiceId(Long invoiceId);

    @Async
    @Query("SELECT NEW com.safadana.AvazehRetailManagement.Models.ItemsForComboBox(p.id AS id, p.productName AS itemName) FROM ProductModel p WHERE p.isActive = true ")
    CompletableFuture<List<ItemsForComboBox>> getProductItems();
}
