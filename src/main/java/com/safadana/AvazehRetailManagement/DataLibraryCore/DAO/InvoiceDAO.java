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

        @Query("SELECT new com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.InvoiceListModel(" +
                "i.id, i.customerId, CONCAT(cust.firstName, ' ', COALESCE(cust.lastName, '')) AS customerFullName, " +
                "i.about, i.dateCreated, i.dateUpdated, " +
                "ROUND(CASE WHEN COALESCE(i.discountType, 0) = 0 THEN SUM(ii.countValue * ii.sellPrice) - (COALESCE(i.discountValue, 0) / 100 * SUM(ii.countValue * ii.sellPrice)) "
                +
                "WHEN COALESCE(i.discountType, 0) = 1 THEN SUM(ii.countValue * ii.sellPrice) - COALESCE(i.discountValue, 0) END, 0) AS totalInvoiceSum, "
                +
                "COALESCE(SUM(ip.payAmount), 0) AS totalInvoicePayments, " +
                "i.lifeStatus, i.prevInvoiceId, " +
                "(SELECT SUM(COALESCE(isum.prevInvoiceSum, 0)) - SUM(COALESCE(ipay.prevInvoicePays, 0)) " +
                "FROM InvoiceListModel isum " +
                "WHERE isum.id = i.id) AS prevInvoiceBalance, " +
                "(SELECT baseI.id FROM Invoices baseI LEFT JOIN Invoices prevI ON baseI.id = prevI.prevInvoiceId WHERE baseI.id = i.id) AS fwdInvoiceId) "
                +
                "FROM Invoices i " +
                "INNER JOIN Customers cust ON i.customerId = cust.id " +
                "LEFT JOIN InvoiceItems ii ON i.prevInvoiceId = ii.invoiceId " +
                "LEFT JOIN InvoicePayments ip ON i.prevInvoiceId = ip.invoiceId " +
                "WHERE (:lifeStatus = -1 OR i.lifeStatus = :lifeStatus) " +
                "AND (:invoiceId = -1 OR i.id = :invoiceId OR i.prevInvoiceId = :invoiceId OR " +
                "(SELECT baseI.id FROM Invoices baseI LEFT JOIN Invoices prevI ON baseI.id = prevI.prevInvoiceId WHERE baseI.id = i.id) = :invoiceId) "
                +
                "AND (:customerId = -1 OR i.customerId = :customerId) " +
                "AND (:date = '%' OR i.dateCreated LIKE :date OR i.dateUpdated LIKE :date) " +
                "AND (:searchValue = '%' OR cust.firstName || ' ' || COALESCE(cust.lastName, '') LIKE :searchValue OR i.about LIKE :searchValue OR i.descriptions LIKE :searchValue) "
                +
                "AND (:finStatus = -1 OR (:finStatus = 0 AND ROUND(COALESCE(SUM(ii.countValue * ii.sellPrice), 0) - (COALESCE(i.discountValue, 0) / 100 * SUM(ii.countValue * ii.sellPrice)), 0) - COALESCE(SUM(ip.payAmount), 0) + "
                +
                "(SELECT SUM(COALESCE(isum.prevInvoiceSum, 0)) - SUM(COALESCE(ipay.prevInvoicePays, 0)) FROM InvoiceListModel isum WHERE isum.id = i.id) = 0) "
                +
                "OR (:finStatus = 1 AND ROUND(COALESCE(SUM(ii.countValue * ii.sellPrice), 0) - (COALESCE(i.discountValue, 0) / 100 * SUM(ii.countValue * ii.sellPrice)), 0) - COALESCE(SUM(ip.payAmount), 0) + "
                +
                "(SELECT SUM(COALESCE(isum.prevInvoiceSum, 0)) - SUM(COALESCE(ipay.prevInvoicePays, 0)) FROM InvoiceListModel isum WHERE isum.id = i.id) > 0) "
                +
                "OR (:finStatus = 2 AND ROUND(COALESCE(SUM(ii.countValue * ii.sellPrice), 0) - (COALESCE(i.discountValue, 0) / 100 * SUM(ii.countValue * ii.sellPrice)), 0) - COALESCE(SUM(ip.payAmount), 0) + "
                +
                "(SELECT SUM(COALESCE(isum.prevInvoiceSum, 0)) - SUM(COALESCE(ipay.prevInvoicePays, 0)) FROM InvoiceListModel isum WHERE isum.id = i.id) < 0) "
                +
                "OR (:finStatus = 3 AND " +
                "(SELECT baseI.id FROM Invoices baseI LEFT JOIN Invoices prevI ON baseI.id = prevI.prevInvoiceId WHERE baseI.id = i.id) IS NULL "
                +
                "AND ROUND(COALESCE(SUM(ii.countValue * ii.sellPrice), 0) - (COALESCE(i.discountValue, 0) / 100 * SUM(ii.countValue * ii.sellPrice)), 0) - COALESCE(SUM(ip.payAmount), 0) + "
                +
                "(SELECT SUM(COALESCE(isum.prevInvoiceSum, 0)) - SUM(COALESCE(ipay.prevInvoicePays, 0)) FROM InvoiceListModel isum WHERE isum.id = i.id) <> 0))) "
                +
                "GROUP BY i.id, cust.firstName, cust.lastName, i.about, i.dateCreated, i.dateUpdated, i.lifeStatus, i.prevInvoiceId")
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
