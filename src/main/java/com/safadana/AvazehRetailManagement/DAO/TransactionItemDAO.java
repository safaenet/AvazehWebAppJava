package com.safadana.AvazehRetailManagement.DAO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.safadana.AvazehRetailManagement.Models.TransactionItemModel;

@Repository
public interface TransactionItemDAO extends JpaRepository<TransactionItemModel, Long> {

        @Async
        CompletableFuture<List<TransactionItemModel>> findByTransactionId(Long transactionId);

        @Async
        @Query("SELECT ti FROM TransactionItemModel ti WHERE (ti.transactionId = :TransactionId) AND " +
                        "(UPPER(ti.title) LIKE :SearchText OR " +
                        "CAST(ti.amount as text) LIKE :SearchText OR " +
                        "ti.countString LIKE :SearchText OR " +
                        "ti.dateCreated LIKE :SearchText OR " +
                        "ti.dateUpdated LIKE :SearchText OR " +
                        "UPPER(ti.descriptions) LIKE :SearchText) AND (ti.dateCreated LIKE :TransactionDate OR ti.dateUpdated LIKE :TransactionDate) AND (" +
                        "(:TransactionStatus = 'BALANCED' AND CAST(ti.amount AS LONG) * CAST(ti.countValue AS LONG) = 0) OR " +
                        "(:TransactionStatus = 'POSITIVE' AND CAST(ti.amount AS LONG) * CAST(ti.countValue AS LONG) > 0) OR " +
                        "(:TransactionStatus = 'NEGATIVE' AND CAST(ti.amount AS LONG) * CAST(ti.countValue AS LONG) < 0) OR " +
                        "(:TransactionStatus = 'ALL'))")
        CompletableFuture<Page<TransactionItemModel>> findByMany(@Param("TransactionId") Long TransactionId,
                        @Param("SearchText") String SearchText, @Param("TransactionStatus") String TransactionStatus,
                        @Param("TransactionDate") String TransactionDate,
                        Pageable pageable);
}
