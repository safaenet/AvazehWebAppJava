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

import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.CustomerModel;
import com.safadana.AvazehRetailManagement.SharedLibrary.DtoModels.ItemsForComboBox;

@Repository
public interface CustomerDAO extends JpaRepository<CustomerModel, Integer> {

    @Async
    @Query("SELECT c FROM CustomerModel c LEFT JOIN FETCH c.phoneNumbers pn WHERE " +
            "pn.phoneNumber LIKE ?1 " +
            "OR UPPER(c.fullName) LIKE ?1 " +
            "OR UPPER(c.companyName) LIKE ?1 " +
            "OR UPPER(c.emailAddress) LIKE ?1 " +
            "OR UPPER(c.postAddress) LIKE ?1 " +
            "OR c.dateJoined LIKE ?1 " +
            "OR UPPER(c.descriptions) LIKE ?1")
    CompletableFuture<Page<CustomerModel>> findByMany(String searchText, Pageable pageable);

    @Async
    @Query("SELECT NEW com.safadana.AvazehRetailManagement.SharedLibrary.DtoModels.ItemsForComboBox(c.id, c.fullName AS itemName) FROM CustomerModel c ORDER BY c.fullName")
    CompletableFuture<List<ItemsForComboBox>> getCustomerNames();

    @Async
    @Query(name = "customerBalance")
    CompletableFuture<Double> getCustomerBalance(@Param("cId") int customerId);
}
