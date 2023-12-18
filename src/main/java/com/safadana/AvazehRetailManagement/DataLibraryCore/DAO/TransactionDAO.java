package com.safadana.AvazehRetailManagement.DataLibraryCore.DAO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.TransactionModel;
import com.safadana.AvazehRetailManagement.SharedLibrary.DtoModels.ItemsForComboBox;

@Repository
public interface TransactionDAO extends JpaRepository<TransactionModel, Integer> {

    @Async
    @Query("SELECT c FROM ChequeModel c LEFT JOIN FETCH c.events e WHERE " +
            "(c.id = chequeId) AND " +
            "e.eventDate LIKE CONCAT('%', ?1, '%') OR " +
            "CAST(e.eventType as text) LIKE CONCAT('%', ?1, '%') OR " +
            "UPPER(e.eventText) LIKE CONCAT('%', UPPER(?1), '%') OR " +
            "UPPER(drawer) LIKE CONCAT('%', UPPER(?1), '%') OR " +
            "UPPER(orderer) LIKE CONCAT('%', UPPER(?1), '%') OR " +
            "CAST(payAmount as text) LIKE CONCAT('%', UPPER(?1), '%') OR " +
            "UPPER(about) LIKE CONCAT('%', UPPER(?1), '%') OR " +
            "issueDate LIKE CONCAT('%', ?1, '%') OR " +
            "dueDate LIKE CONCAT('%', ?1, '%') OR " +
            "UPPER(bankName) LIKE CONCAT('%', UPPER(?1), '%') OR " +
            "serialNumber LIKE CONCAT('%', ?1, '%') OR " +
            "identifier LIKE CONCAT('%', ?1, '%') OR " +
            "UPPER(descriptions) LIKE CONCAT('%', UPPER(?1), '%')")
    CompletableFuture<Page<TransactionModel>> findByMany(String searchText, Pageable pageable);

    @Query("SELECT NEW com.safadana.AvazehRetailManagement.SharedLibrary.DtoModels.ItemsForComboBox(p.productName AS itemName) FROM ProductModel p "
            + "UNION " +
            "SELECT NEW com.safadana.AvazehRetailManagement.SharedLibrary.DtoModels.ItemsForComboBox(ti.title AS itemName) FROM TransactionItems ti")
    CompletableFuture<List<ItemsForComboBox>> getProductItems();

    @Async
    @Query("SELECT NEW com.safadana.AvazehRetailManagement.SharedLibrary.DtoModels.ItemsForComboBox(t.id AS id, t.fileName AS itemName) "
            + "FROM TransactionModel t WHERE t.id <> COALESCE(:transactionId, 0)")
    CompletableFuture<List<ItemsForComboBox>> getTransactionNames(@Param("transactionId") int id);
}
