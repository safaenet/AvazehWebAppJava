package com.safadana.AvazehRetailManagement.DataLibraryCore.DAO;

import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.CustomerModel;

@Repository
public interface CustomerDAO extends JpaRepository<CustomerModel, Integer> {

    @Async
    @Query("SELECT c FROM CustomerModel c LEFT JOIN FETCH c.phoneNumbers pn WHERE " +
            "(c.id = customerId) AND " +
            "pn.phoneNumber LIKE CONCAT('%', UPPER(?1), '%') " +
            "OR UPPER(firstName) LIKE CONCAT('%', UPPER(?1), '%') " +
            "OR UPPER(lastName) LIKE CONCAT('%', UPPER(?1), '%') " +
            "OR UPPER(CONCAT(firstName, ' ', lastName)) LIKE CONCAT('%', UPPER(?1), '%') " +
            "OR UPPER(companyName) LIKE CONCAT('%', UPPER(?1), '%') " +
            "OR UPPER(emailAddress) LIKE CONCAT('%', UPPER(?1), '%') " +
            "OR UPPER(postAddress) LIKE CONCAT('%', UPPER(?1), '%') " +
            "OR dateJoined LIKE CONCAT('%', ?1, '%') " +
            "OR UPPER(descriptions) LIKE CONCAT('%', UPPER(?1), '%')")
    CompletableFuture<Page<CustomerModel>> findByMany(String searchText, Pageable pageable);
}
