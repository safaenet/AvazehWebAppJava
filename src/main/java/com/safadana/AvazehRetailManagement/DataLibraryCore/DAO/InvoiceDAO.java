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

import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.InvoiceListModel;
import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.InvoiceModel;
import com.safadana.AvazehRetailManagement.SharedLibrary.DtoModels.ItemsForComboBox;

@Repository
public interface InvoiceDAO extends JpaRepository<InvoiceModel, Integer> {

        @Query("")
        Page<InvoiceListModel> loadInvoiceListOldStyle2(
                @Param("lifeStatus") int lifeStatus,
                @Param("invoiceId") int invoiceId,
                @Param("customerId") int customerId,
                @Param("date") String date,
                @Param("searchValue") String searchValue,
                @Param("finStatus") int finStatus,
                Pageable pageable);

        // @Async
        // @Query("SELECT NEW com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.TransactionListModel" +
        //                 "(t.id, t.fileName, t.dateCreated, t.dateUpdated, t.descriptions, " +
        //                 "COALESCE(SUM(CASE WHEN CAST(i.amount AS LONG) * CAST(i.countValue AS LONG) > 0 THEN CAST(i.amount AS LONG) * CAST(i.countValue AS LONG) ELSE 0 END), 0) AS totalPositiveItemsSum, "
        //                 +
        //                 "COALESCE(SUM(CASE WHEN CAST(i.amount AS LONG) * CAST(i.countValue AS LONG) < 0 THEN CAST(i.amount AS LONG) * CAST(i.countValue AS LONG) ELSE 0 END), 0) AS totalNegativeItemsSum) "
        //                 +
        //                 "FROM TransactionModel t LEFT JOIN t.items i WHERE " +
        //                 "UPPER(t.fileName) LIKE CONCAT('%', UPPER(?1), '%') OR " +
        //                 "t.dateCreated LIKE CONCAT('%', ?1, '%') OR " +
        //                 "t.dateUpdated LIKE CONCAT('%', ?1, '%') OR " +
        //                 "UPPER(t.descriptions) LIKE CONCAT('%', UPPER(?1), '%') OR " +

        //                 "UPPER(i.title) LIKE CONCAT('%', UPPER(?1), '%') OR " +
        //                 "CAST(i.amount as text) LIKE CONCAT('%', ?1, '%') OR " +
        //                 "i.dateCreated LIKE CONCAT('%', ?1, '%') OR " +
        //                 "i.dateUpdated LIKE CONCAT('%', ?1, '%') OR " +
        //                 "UPPER(i.descriptions) LIKE CONCAT('%', UPPER(?1), '%') GROUP BY t.id")
        // CompletableFuture<Page<InvoiceListModel>> findByMany(String searchText, Pageable pageable);

        @Async
        @Query("SELECT NEW com.safadana.AvazehRetailManagement.SharedLibrary.DtoModels.ItemsForComboBox(p.id AS id, p.productName AS itemName) FROM ProductModel p WHERE p.isActive = true")
        CompletableFuture<List<ItemsForComboBox>> getProductItems();
}
