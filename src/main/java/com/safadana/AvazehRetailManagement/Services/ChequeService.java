package com.safadana.AvazehRetailManagement.Services;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.safadana.AvazehRetailManagement.DAO.ChequeDAO;
import com.safadana.AvazehRetailManagement.Enums.ChequeQueryStatus;
import com.safadana.AvazehRetailManagement.Helpers.PersianCalendarHelper;
import com.safadana.AvazehRetailManagement.Models.ChequeModel;

@Service
public class ChequeService {
    @Autowired
    ChequeDAO DAO;

    public CompletableFuture<List<ChequeModel>> getAll() {
        return CompletableFuture.completedFuture(DAO.findAll());
    }

    public CompletableFuture<Page<ChequeModel>> getWithPagination(Optional<String> searchText,
            Optional<String> chequeStatus, int offset, int pageSize,
            String sortColumn, String sortOrder) {
        Sort.Direction sortDir = sortOrder.toUpperCase().equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        String SearchText = "";
        if (searchText == null || !searchText.isPresent())
            SearchText = "%";
        else
            SearchText = "%" + searchText.get().toUpperCase() + "%";
        if (sortColumn == null || sortColumn == "")
            sortColumn = "id";
        String ChequeStatus = "";
        if (chequeStatus == null || !chequeStatus.isPresent())
            ChequeStatus = "ALL";
        else
            ChequeStatus = chequeStatus.get();
        if (pageSize == 0)
            pageSize = 50;
        String persianDate = PersianCalendarHelper.getPersianDate();
        return DAO.findByMany(SearchText, persianDate, ChequeStatus,
                PageRequest.of(offset, pageSize).withSort(Sort.by(sortDir, sortColumn)));
    }

    public CompletableFuture<ChequeModel> getById(long id) {
        if (id == 0)
            return null;
        return CompletableFuture.completedFuture(DAO.findById(id).get());
    }

    public CompletableFuture<ChequeModel> createUpdate(ChequeModel item) {
        return CompletableFuture.completedFuture(DAO.save(item));
    }

    public void deleteById(long id) {
        DAO.deleteById(id);
    }

    public CompletableFuture<List<String>> getBankNames() {
        return DAO.findBankNames();
    }

    public CompletableFuture<List<ChequeModel>> getCloseCheques() {
        int addDays = 3; // This should be read from settings.
        String today = PersianCalendarHelper.getRawPersianDate();
        String until = PersianCalendarHelper.getRawPersianDate(addDays);
        return DAO.findCloseCheques(today, until);
    }
}
