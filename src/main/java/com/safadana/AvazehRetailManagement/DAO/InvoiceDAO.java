package com.safadana.AvazehRetailManagement.DAO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.safadana.AvazehRetailManagement.Models.InvoiceModel;

import jakarta.transaction.Transactional;

@Repository
public interface InvoiceDAO extends JpaRepository<InvoiceModel, Long> {

        @Async
        @Query("SELECT DISTINCT i.about AS itemName FROM InvoiceModel i WHERE i.about IS NOT NULL ORDER BY i.about")
        CompletableFuture<List<String>> getInvoiceAbouts();

        @Modifying
        @Transactional
        @Query(value = "UPDATE invoices SET customer_id = :customerId, about = :about, datecreated = :dateCreated, " +
        "dateupdated = :dateUpdated, discounttype = :discountType, discountvalue = :discountValue, " +
        "descriptions = :descriptions, previnvoiceid = :prevInvoiceId WHERE id = :id", nativeQuery = true)
        int updateInvoiceSpecs(Long id, Long customerId, String about, String dateCreated, String dateUpdated,
        int discountType, double discountValue, String descriptions, Long prevInvoiceId);
}
