package com.safadana.AvazehRetailManagement.Services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.safadana.AvazehRetailManagement.DAO.TransactionItemDAO;
import com.safadana.AvazehRetailManagement.Helpers.PersianCalendarHelper;
import com.safadana.AvazehRetailManagement.Models.TransactionItemModel;

@Service
public class TransactionItemService {
    @Autowired
    TransactionItemDAO DAO;

    public CompletableFuture<List<TransactionItemModel>> getAll(long transactionId) {
        return DAO.findByTransactionId(transactionId);
    }

    public CompletableFuture<Page<TransactionItemModel>> getWithPagination(Long transactionId, String searchText,
            int offset, int pageSize,
            String sortColumn,
            String sortOrder) {
            Sort.Direction sortDir = sortOrder.toUpperCase().equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
            if(searchText == null || searchText == "") searchText = "%"; else searchText = "%" + searchText.toUpperCase() + "%";
            if(sortColumn == null || sortColumn == "") sortColumn = "id";
            if(pageSize == 0) pageSize = 50;   
        return DAO.findByMany(transactionId, searchText,
                PageRequest.of(offset, pageSize).withSort(Sort.by(sortDir, sortColumn)));
    }

    public CompletableFuture<TransactionItemModel> getById(long id) {
        if(id == 0) return null;
        return CompletableFuture.completedFuture(DAO.findById(id).get());
    }

    public CompletableFuture<TransactionItemModel> createUpdate(TransactionItemModel item) {
        if (item.getDateCreated() == null || item.getDateCreated() == "") {
            item.setDateCreated(PersianCalendarHelper.getPersianDateTime());
        }
        item.setDateUpdated(PersianCalendarHelper.getPersianDateTime());
        return CompletableFuture.completedFuture(DAO.save(item));
    }

    public void deleteById(long id) {
        DAO.deleteById(id);
    }
}
