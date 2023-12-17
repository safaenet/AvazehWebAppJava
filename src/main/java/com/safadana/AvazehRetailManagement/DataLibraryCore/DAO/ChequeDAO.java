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

import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.ChequeModel;

@Repository
public interface ChequeDAO extends JpaRepository<ChequeModel, Integer> {

    @Async
    @Query("SELECT c FROM ChequeModel c LEFT JOIN FETCH c.events e WHERE " +
            "(c.id = chequeId) AND " +
            "e.eventDate LIKE CONCAT('%', ?1, '%') " +
            "OR CAST(e.eventType as text) LIKE CONCAT('%', ?1, '%') " +
            "OR UPPER(e.eventText) LIKE CONCAT('%', UPPER(?1), '%') " +
            "OR UPPER(drawer) LIKE CONCAT('%', UPPER(?1), '%') " +
            "OR UPPER(orderer) LIKE CONCAT('%', UPPER(?1), '%') " +
            "OR CAST(payAmount as text) LIKE CONCAT('%', UPPER(?1), '%') " +
            "OR UPPER(about) LIKE CONCAT('%', UPPER(?1), '%') " +
            "OR issueDate LIKE CONCAT('%', ?1, '%') " +
            "OR dueDate LIKE CONCAT('%', ?1, '%') " +
            "OR UPPER(bankName) LIKE CONCAT('%', UPPER(?1), '%') " +
            "OR serialNumber LIKE CONCAT('%', ?1, '%') " +
            "OR identifier LIKE CONCAT('%', ?1, '%') " +
            "OR UPPER(descriptions) LIKE CONCAT('%', UPPER(?1), '%')")
    CompletableFuture<Page<ChequeModel>> findByMany(String searchText, Pageable pageable);

    @Async
    @Query("SELECT DISTINCT c.bankName FROM ChequeModel c")
    CompletableFuture<List<String>> findBankNames();

    @Async
    @Query("FROM ChequeModel c LEFT JOIN FETCH c.events e WHERE c.id = chequeId AND CAST(REPLACE(c.dueDate, '/','') AS INTEGER) >= CAST(:today AS INTEGER) AND CAST(REPLACE(c.dueDate, '/','') AS INTEGER) <= CAST(:until AS INTEGER)")
    CompletableFuture<List<ChequeModel>> findCloseCheques(@Param("today") String today, @Param("until") String until);
}
