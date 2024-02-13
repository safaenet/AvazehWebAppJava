package com.safadana.AvazehRetailManagement.Services;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.safadana.AvazehRetailManagement.DAO.TransactionDAO;
import com.safadana.AvazehRetailManagement.Helpers.PersianCalendarHelper;
import com.safadana.AvazehRetailManagement.Models.ItemsForComboBox;
import com.safadana.AvazehRetailManagement.Models.TransactionListModel;
import com.safadana.AvazehRetailManagement.Models.TransactionModel;

@Service
public class TransactionService {
    @Autowired
    TransactionDAO DAO;

    public CompletableFuture<List<TransactionModel>> getAll() {
        return CompletableFuture.completedFuture(DAO.findAll());
    }

    public CompletableFuture<Page<TransactionListModel>> getWithPagination(Optional<String> searchText,
            Optional<String> transactionStatus, Optional<String> transactionDate, int offset,
            int pageSize,
            String sortColumn,
            String sortOrder) {
        Sort.Direction sortDir = sortOrder.toUpperCase().equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        String SearchText = "%";
        if (searchText != null && searchText.isPresent())
            SearchText = "%" + searchText.get().toUpperCase() + "%";
            
        if (sortColumn == null || sortColumn == "")
            sortColumn = "id";

        String TransactionStatus = "ALL";
        if (transactionStatus != null && transactionStatus.isPresent())
            TransactionStatus = transactionStatus.get();

        String TransactionDate = "%";
        if (transactionDate != null && transactionDate.isPresent()){
            TransactionDate = "%" + transactionDate.get() + "%";
            System.out.println(TransactionDate);
        }
            
        if (pageSize == 0)
            pageSize = 50;

        return DAO.findByMany(SearchText, TransactionStatus, TransactionDate,
                PageRequest.of(offset, pageSize).withSort(Sort.by(sortDir, sortColumn)));
    }

    public CompletableFuture<TransactionModel> getById(long id) {
        if (id == 0)
            return null;
        return CompletableFuture.completedFuture(DAO.findById(id).get());
    }

    public CompletableFuture<TransactionModel> createUpdate(TransactionModel item) {
        if (item.getDateCreated() == null || item.getDateCreated() == "") {
            item.setDateCreated(PersianCalendarHelper.getPersianDateTime());
        }
        item.setDateUpdated(PersianCalendarHelper.getPersianDateTime());
        return CompletableFuture.completedFuture(DAO.save(item));
    }

    public void deleteById(long id) {
        DAO.deleteById(id);
    }

    public CompletableFuture<List<ItemsForComboBox>> getProductItems() {
        return DAO.getProductItems();
    }

    public CompletableFuture<List<ItemsForComboBox>> getTransactionNames(long id) {
        return DAO.getTransactionNames(id);
    }
}
