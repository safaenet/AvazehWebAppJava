package com.safadana.AvazehRetailManagement.DataLibraryCore.DAO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.TransactionItemModel;

@Repository
public interface TransactionItemDAO extends JpaRepository<TransactionItemModel, Integer> {

        @Async
        CompletableFuture<List<TransactionItemModel>> findByTransactionId(int transactionId);

        @Async
        @Query("SELECT ti FROM TransactionItemModel ti WHERE " +
                        "UPPER(ti.title) LIKE CONCAT('%', UPPER(?1), '%') OR " +
                        "CAST(ti.amount as text) LIKE CONCAT('%', ?1, '%') OR " +
                        "ti.countString LIKE CONCAT('%', ?1, '%') OR " +
                        "ti.dateCreated LIKE CONCAT('%', ?1, '%') OR " +
                        "ti.dateUpdated LIKE CONCAT('%', ?1, '%') OR " +
                        "UPPER(ti.descriptions) LIKE CONCAT('%', UPPER(?1), '%')")
        CompletableFuture<Page<TransactionItemModel>> findByMany(int transactionId, String searchText,
                        Pageable pageable);
}
