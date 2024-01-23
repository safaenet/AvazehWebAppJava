package com.safadana.AvazehRetailManagement.DataLibraryCore.DAO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.safadana.AvazehRetailManagement.SharedLibrary.Models.InvoiceModel;

@Repository
public interface InvoiceDAO extends JpaRepository<InvoiceModel, Long> {

        @Async
        @Query("SELECT DISTINCT i.about AS itemName FROM InvoiceModel i WHERE i.about IS NOT NULL ORDER BY i.about")
        CompletableFuture<List<String>> getInvoiceAbouts();
}
