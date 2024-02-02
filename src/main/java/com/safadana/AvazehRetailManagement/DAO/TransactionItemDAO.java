package com.safadana.AvazehRetailManagement.DAO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.safadana.AvazehRetailManagement.Models.TransactionItemModel;

@Repository
public interface TransactionItemDAO extends JpaRepository<TransactionItemModel, Long> {

        @Async
        CompletableFuture<List<TransactionItemModel>> findByTransactionId(Long transactionId);

        @Async
        @Query("SELECT ti FROM TransactionItemModel ti WHERE " +
                        "UPPER(ti.title) LIKE ?1 OR " +
                        "CAST(ti.amount as text) LIKE ?1 OR " +
                        "ti.countString LIKE ?1 OR " +
                        "ti.dateCreated LIKE ?1 OR " +
                        "ti.dateUpdated LIKE ?1 OR " +
                        "UPPER(ti.descriptions) LIKE ?1")
        CompletableFuture<Page<TransactionItemModel>> findByMany(Long transactionId, String searchText,
                        Pageable pageable);
}
