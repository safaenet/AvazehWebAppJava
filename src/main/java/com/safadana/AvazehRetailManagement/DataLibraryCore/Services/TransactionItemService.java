package com.safadana.AvazehRetailManagement.DataLibraryCore.Services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.safadana.AvazehRetailManagement.DataLibraryCore.DAO.TransactionItemDAO;
import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.TransactionItemModel;
import com.safadana.AvazehRetailManagement.SharedLibrary.Helpers.PersianCalendarHelper;

@Service
public class TransactionItemService {
    @Autowired
    TransactionItemDAO DAO;

    public CompletableFuture<List<TransactionItemModel>> getAll(int transactionId) {
        return DAO.findByTransactionId(transactionId);
    }

    public CompletableFuture<Page<TransactionItemModel>> getWithPagination(int transactionId, String searchText,
            int offset, int pageSize,
            String sortColumn,
            String sortOrder) {
        Sort.Direction sortDir = sortColumn.toUpperCase().equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return DAO.findByMany(transactionId, searchText,
                PageRequest.of(offset, pageSize).withSort(Sort.by(sortDir, sortColumn)));
    }

    public CompletableFuture<TransactionItemModel> getById(int id) {
        return CompletableFuture.completedFuture(DAO.findById(id).get());
    }

    public CompletableFuture<TransactionItemModel> createUpdateProduct(TransactionItemModel item) {
        if (item.getDateCreated() == null || item.getDateCreated() == "") {
            item.setDateCreated(PersianCalendarHelper.getPersianDateTime());
        }
        item.setDateUpdated(PersianCalendarHelper.getPersianDateTime());
        return CompletableFuture.completedFuture(DAO.save(item));
    }

    public void deleteById(int id) {
        DAO.deleteById(id);
    }
}
