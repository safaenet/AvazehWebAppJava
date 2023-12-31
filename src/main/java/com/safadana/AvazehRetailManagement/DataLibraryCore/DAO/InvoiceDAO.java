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

        // @Query("")
        // Page<InvoiceListModel> loadInvoiceListOldStyle2(
        // @Param("lifeStatus") int lifeStatus,
        // @Param("invoiceId") int invoiceId,
        // @Param("customerId") int customerId,
        // @Param("date") String date,
        // @Param("searchValue") String searchValue,
        // @Param("finStatus") int finStatus,
        // Pageable pageable);

        @Async
        CompletableFuture<Page<InvoiceListModel>> findByMany(@Param("lifeStatus") String lifeStatus,
                        @Param("invoiceId") int invoiceId, @Param("customerId") int customerId,
                        @Param("date") String date, @Param("finStatus") String finStatus,
                        @Param("searchText") String searchText,
                        Pageable pageable);

        // @Query(name = "findByMany")
        // Page<InvoiceListModel> findByMany();

        @Async
        @Query("SELECT NEW com.safadana.AvazehRetailManagement.SharedLibrary.DtoModels.ItemsForComboBox(p.id AS id, p.productName AS itemName) FROM ProductModel p WHERE p.isActive = true")
        CompletableFuture<List<ItemsForComboBox>> getProductItems();
}
