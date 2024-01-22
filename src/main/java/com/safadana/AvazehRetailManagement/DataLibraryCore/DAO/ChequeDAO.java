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
public interface ChequeDAO extends JpaRepository<ChequeModel, Long> {

        @Async
        @Query("SELECT c FROM ChequeModel c WHERE (" +
                "(:chequeStatus = 'NOTCASHED' AND c.statusType NOT IN (ChequeStatus.ENDORSED, ChequeStatus.CASHED)) OR " +
                "(:chequeStatus = 'CASHED' AND c.statusType = ChequeStatus.CASHED) OR " +
                "(:chequeStatus = 'ENDORSED' AND c.statusType = ChequeStatus.ENDORSED) OR " +
                "(:chequeStatus = 'RETURNED' AND c.statusType = ChequeStatus.RETURNED) OR " +
                "(:chequeStatus = 'FROMNOWON' AND ((c.statusType NOT IN (ChequeStatus.ENDORSED, ChequeStatus.CASHED)) OR (c.statusType = ChequeStatus.ENDORSED) AND c.dueDate >= :persianDate)) OR " +
                "(:chequeStatus = 'ALL')) AND (" +
                "UPPER(c.statusDate) LIKE :searchText OR " +
                "UPPER(c.statusText) LIKE :searchText OR " +
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
        @Query("SELECT c FROM ChequeModel c WHERE " +
                        "CAST(REPLACE(c.dueDate, '/','') AS INTEGER) >= CAST(:today AS INTEGER) AND " +
                        "CAST(REPLACE(c.dueDate, '/','') AS INTEGER) <= CAST(:until AS INTEGER)")
        CompletableFuture<List<ChequeModel>> findCloseCheques(@Param("today") String today,
                        @Param("until") String until);
}
