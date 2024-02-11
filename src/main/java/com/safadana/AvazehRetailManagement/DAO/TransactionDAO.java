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

import com.safadana.AvazehRetailManagement.Models.ItemsForComboBox;
import com.safadana.AvazehRetailManagement.Models.TransactionListModel;
import com.safadana.AvazehRetailManagement.Models.TransactionModel;

@Repository
public interface TransactionDAO extends JpaRepository<TransactionModel, Long> {

        @Async
        @Query("SELECT NEW com.safadana.AvazehRetailManagement.Models.TransactionListModel" +
                "(t.id, t.fileName, t.dateCreated, t.dateUpdated, t.descriptions, " +
                "COALESCE(SUM(CASE WHEN CAST(i.amount AS LONG) * CAST(i.countValue AS LONG) > 0 THEN CAST(i.amount AS LONG) * CAST(i.countValue AS LONG) ELSE 0 END), 0) AS totalPositiveItemsSum, " +
                "COALESCE(SUM(CASE WHEN CAST(i.amount AS LONG) * CAST(i.countValue AS LONG) < 0 THEN CAST(i.amount AS LONG) * CAST(i.countValue AS LONG) ELSE 0 END), 0) AS totalNegativeItemsSum) " +
                "FROM TransactionModel t LEFT JOIN t.items i WHERE " +
                "(UPPER(t.fileName) LIKE :searchText OR " +
                "t.dateCreated LIKE :searchText OR " +
                "t.dateUpdated LIKE :searchText OR " +
                "UPPER(t.descriptions) LIKE :searchText OR " +

                "UPPER(i.title) LIKE :searchText OR " +
                "CAST(i.amount as text) LIKE :searchText OR " +
                "i.dateCreated LIKE :searchText OR " +
                "i.dateUpdated LIKE :searchText OR " +
                "UPPER(i.descriptions) LIKE :searchText) AND (" +
                "(:TransactionStatus = 'BALANCED' AND totalPositiveItemsSum + totalNegativeItemsSum = 0) OR " +
                "(:TransactionStatus = 'POSITIVE' AND totalPositiveItemsSum + totalNegativeItemsSum > 0) OR " +
                "(:TransactionStatus = 'NEGATIVE' AND totalPositiveItemsSum + totalNegativeItemsSum < 0) OR " +
                "(:TransactionStatus = 'ALL') " +
                ") GROUP BY t.id")
        CompletableFuture<Page<TransactionListModel>> findByMany(@Param("searchText") String searchText, @Param("TransactionStatus") String TransactionStatus, Pageable pageable);

        @Async
        @Query("SELECT NEW com.safadana.AvazehRetailManagement.Models.ItemsForComboBox(0 AS id, p.productName AS itemName) FROM ProductModel p WHERE p.isActive = true "
                        + "UNION " +
                        "SELECT NEW com.safadana.AvazehRetailManagement.Models.ItemsForComboBox(1 AS id, ti.title AS itemName) FROM TransactionItemModel ti")
        CompletableFuture<List<ItemsForComboBox>> getProductItems();

        @Async
        @Query("SELECT NEW com.safadana.AvazehRetailManagement.Models.ItemsForComboBox(t.id AS id, t.fileName AS itemName) "
                        + "FROM TransactionModel t WHERE t.id <> COALESCE(:transactionId, 0)")
        CompletableFuture<List<ItemsForComboBox>> getTransactionNames(@Param("transactionId") Long id);
}
