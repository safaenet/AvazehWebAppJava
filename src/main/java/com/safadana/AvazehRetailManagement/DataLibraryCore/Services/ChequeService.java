package com.safadana.AvazehRetailManagement.DataLibraryCore.Services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.safadana.AvazehRetailManagement.DataLibraryCore.DAO.ChequeDAO;
import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.ChequeModel;
import com.safadana.AvazehRetailManagement.SharedLibrary.Helpers.PersianCalendarHelper;

@Service
public class ChequeService {
    @Autowired
    ChequeDAO DAO;

    public CompletableFuture<List<ChequeModel>> getAll() {
        return CompletableFuture.completedFuture(DAO.findAll());
    }

    public CompletableFuture<Page<ChequeModel>> getWithPagination(String searchText, int offset, int pageSize,
            String sortColumn,
            String sortOrder) {
        Sort.Direction sortDir = sortColumn.toUpperCase().equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return DAO.findByMany(searchText,
                PageRequest.of(offset, pageSize).withSort(Sort.by(sortDir, sortColumn)));
    }

    public CompletableFuture<ChequeModel> getById(int id) {
        return CompletableFuture.completedFuture(DAO.findById(id).get());
    }

    public CompletableFuture<ChequeModel> createUpdateProduct(ChequeModel item) {
        return CompletableFuture.completedFuture(DAO.save(item));
    }

    public void deleteById(int id) {
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
