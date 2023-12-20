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

import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.TransactionItemModel;
import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.TransactionListModel;
import com.safadana.AvazehRetailManagement.SharedLibrary.DtoModels.ItemsForComboBox;

@Repository
public interface TransactionItemDAO extends JpaRepository<TransactionItemModel, Integer> {

        @Async
        @Query("SELECT NEW com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.TransactionListModel" +
                        "(t.id, t.fileName, t.dateCreated, t.dateUpdated, t.descriptions, " +
                        "COALESCE(SUM(CASE WHEN CAST(i.amount AS LONG) * CAST(i.countValue AS LONG) > 0 THEN CAST(i.amount AS LONG) * CAST(i.countValue AS LONG) ELSE 0 END), 0) AS totalPositiveItemsSum, " +
                        "COALESCE(SUM(CASE WHEN CAST(i.amount AS LONG) * CAST(i.countValue AS LONG) < 0 THEN CAST(i.amount AS LONG) * CAST(i.countValue AS LONG) ELSE 0 END), 0) AS totalNegativeItemsSum) " +
                        "FROM TransactionModel t LEFT JOIN t.items i WHERE " +
                        "UPPER(t.fileName) LIKE CONCAT('%', UPPER(?1), '%') OR " +
                        "t.dateCreated LIKE CONCAT('%', ?1, '%') OR " +
                        "t.dateUpdated LIKE CONCAT('%', ?1, '%') OR " +
                        "UPPER(t.descriptions) LIKE CONCAT('%', UPPER(?1), '%') OR " +

                        "UPPER(i.title) LIKE CONCAT('%', UPPER(?1), '%') OR " +
                        "CAST(i.amount as text) LIKE CONCAT('%', UPPER(?1), '%') OR " +
                        "i.dateCreated LIKE CONCAT('%', ?1, '%') OR " +
                        "i.dateUpdated LIKE CONCAT('%', ?1, '%') OR " +
                        "UPPER(i.descriptions) LIKE CONCAT('%', UPPER(?1), '%') GROUP BY t.id")
        CompletableFuture<Page<TransactionListModel>> findByMany(String searchText, Pageable pageable);

        @Async
        @Query("SELECT NEW com.safadana.AvazehRetailManagement.SharedLibrary.DtoModels.ItemsForComboBox(0 AS id, p.productName AS itemName) FROM ProductModel p "
                        + "UNION " +
                        "SELECT NEW com.safadana.AvazehRetailManagement.SharedLibrary.DtoModels.ItemsForComboBox(1 AS id, ti.title AS itemName) FROM TransactionItemModel ti")
        CompletableFuture<List<ItemsForComboBox>> getProductItems();

        @Async
        @Query("SELECT NEW com.safadana.AvazehRetailManagement.SharedLibrary.DtoModels.ItemsForComboBox(t.id AS id, t.fileName AS itemName) "
                        + "FROM TransactionModel t WHERE t.id <> COALESCE(:transactionId, 0)")
        CompletableFuture<List<ItemsForComboBox>> getTransactionNames(@Param("transactionId") int id);
}
