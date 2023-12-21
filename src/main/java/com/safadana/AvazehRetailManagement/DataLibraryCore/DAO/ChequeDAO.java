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
                "(:chequeStatus = NOTCASHED AND (SELECT ce.eventType FROM ChequeEventModel ce WHERE ce.chequeId = c.id ORDER BY ce.id DESC FETCH FIRST 1 ROW ONLY) = :eventType) AND " +
                "(e.eventDate LIKE CONCAT('%', :searchText, '%') OR " +
                "CAST(e.eventType as text) LIKE CONCAT('%', :searchText, '%') OR " +
                "UPPER(e.eventText) LIKE CONCAT('%', UPPER(:searchText), '%') OR " +
                "UPPER(c.drawer) LIKE CONCAT('%', UPPER(:searchText), '%') OR " +
                "UPPER(c.orderer) LIKE CONCAT('%', UPPER(:searchText), '%') OR " +
                "CAST(c.payAmount as text) LIKE CONCAT('%', :searchText, '%') OR " +
                "UPPER(c.about) LIKE CONCAT('%', UPPER(:searchText), '%') OR " +
                "c.issueDate LIKE CONCAT('%', :searchText, '%') OR " +
                "c.dueDate LIKE CONCAT('%', :searchText, '%') OR " +
                "UPPER(c.bankName) LIKE CONCAT('%', UPPER(:searchText), '%') OR " +
                "c.serialNumber LIKE CONCAT('%', :searchText, '%') OR " +
                "c.identifier LIKE CONCAT('%', :searchText, '%') OR " +
                "UPPER(c.descriptions) LIKE CONCAT('%', UPPER(:searchText), '%'))")
        CompletableFuture<Page<ChequeModel>> findByMany(@Param("searchText") String searchText, @Param("persianDate") String persianDate, @Param("chequeStatus") String chequeStatus, Pageable pageable);

        @Async
        @Query("SELECT DISTINCT c.bankName FROM ChequeModel c")
        CompletableFuture<List<String>> findBankNames();

        @Async
        @Query("SELECT c FROM ChequeModel c LEFT JOIN FETCH c.events WHERE " +
                        "CAST(REPLACE(c.dueDate, '/','') AS INTEGER) >= CAST(:today AS INTEGER) AND " +
                        "CAST(REPLACE(c.dueDate, '/','') AS INTEGER) <= CAST(:until AS INTEGER)")
        CompletableFuture<List<ChequeModel>> findCloseCheques(@Param("today") String today,
                        @Param("until") String until);
}
