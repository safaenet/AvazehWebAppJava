package com.safadana.AvazehRetailManagement.DataLibraryCore.Services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.safadana.AvazehRetailManagement.DataLibraryCore.DAO.TransactionDAO;
import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.TransactionModel;
import com.safadana.AvazehRetailManagement.SharedLibrary.DtoModels.ItemsForComboBox;

@Service
public class TransactionService {
    @Autowired
    TransactionDAO DAO;

    public CompletableFuture<List<TransactionModel>> getAll() {
        return CompletableFuture.completedFuture(DAO.findAll());
    }

    public CompletableFuture<Page<TransactionModel>> getWithPagination(String searchText, int offset, int pageSize,
            String sortColumn,
            String sortOrder) {
        Sort.Direction sortDir = sortColumn.toUpperCase().equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return DAO.findByMany(searchText,
                PageRequest.of(offset, pageSize).withSort(Sort.by(sortDir, sortColumn)));
    }

    public CompletableFuture<TransactionModel> getById(int id) {
        return CompletableFuture.completedFuture(DAO.findById(id).get());
    }

    public CompletableFuture<TransactionModel> createUpdateProduct(TransactionModel product) {
        return CompletableFuture.completedFuture(DAO.save(product));
    }

    public void deleteById(int id) {
        DAO.deleteById(id);
    }

    public CompletableFuture<List<ItemsForComboBox>> getProductItems() {
        return DAO.getProductItems();
    }

    public CompletableFuture<List<ItemsForComboBox>> getTransactionNames(int id) {
        return DAO.getTransactionNames(id);
    }
}
