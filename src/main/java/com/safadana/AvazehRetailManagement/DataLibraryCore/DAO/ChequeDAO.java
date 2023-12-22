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
        @Query("SELECT c FROM ChequeModel c LEFT JOIN FETCH c.events e WHERE (" +
                "(:chequeStatus = 'NOTCASHED' AND (SELECT ce.eventType FROM ChequeEventModel ce WHERE ce.chequeId = c.id AND ce.id = (SELECT MAX(ce2.id) FROM ChequeEventModel ce2)) NOT IN (ChequeEventTypes.ENDORSED, ChequeEventTypes.CASHED)) OR " +
                "(:chequeStatus = 'CASHED' AND (SELECT ce.eventType FROM ChequeEventModel ce WHERE ce.chequeId = c.id AND ce.id = (SELECT MAX(ce2.id) FROM ChequeEventModel ce2)) = ChequeEventTypes.CASHED) OR " +
                "(:chequeStatus = 'ENDORSED' AND (SELECT ce.eventType FROM ChequeEventModel ce WHERE ce.chequeId = c.id AND ce.id = (SELECT MAX(ce2.id) FROM ChequeEventModel ce2)) = ChequeEventTypes.ENDORSED) OR " +
                "(:chequeStatus = 'RETURNED' AND (SELECT ce.eventType FROM ChequeEventModel ce WHERE ce.chequeId = c.id AND ce.id = (SELECT MAX(ce2.id) FROM ChequeEventModel ce2)) = ChequeEventTypes.RETURNED) OR " +
                "(:chequeStatus = 'FROMNOWON' AND (((SELECT ce.eventType FROM ChequeEventModel ce WHERE ce.chequeId = c.id AND ce.id = (SELECT MAX(ce2.id) FROM ChequeEventModel ce2)) NOT IN (ChequeEventTypes.ENDORSED, ChequeEventTypes.CASHED)) OR ((SELECT ce.eventType FROM ChequeEventModel ce WHERE ce.chequeId = c.id AND ce.id = (SELECT MAX(ce2.id) FROM ChequeEventModel ce2)) = ChequeEventTypes.ENDORSED) AND c.dueDate >= :persianDate)) OR " +
                "(:chequeStatus = 'ALL')) AND " +
                "(e.eventDate LIKE :searchText OR " +
                "CAST(e.eventType as text) LIKE :searchText OR " +
                "UPPER(e.eventText) LIKE :searchText OR " +
                "UPPER(c.drawer) LIKE :searchText OR " +
                "UPPER(c.orderer) LIKE :searchText OR " +
                "CAST(c.payAmount as text) LIKE :searchText OR " +
                "UPPER(c.about) LIKE :searchText OR " +
                "c.issueDate LIKE :searchText OR " +
                "c.dueDate LIKE :searchText OR " +
                "UPPER(c.bankName) LIKE :searchText OR " +
                "c.serialNumber LIKE :searchText OR " +
                "c.identifier LIKE :searchText OR " +
                "UPPER(c.descriptions) LIKE :searchText)")
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
