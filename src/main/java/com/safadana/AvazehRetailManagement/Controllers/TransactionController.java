package com.safadana.AvazehRetailManagement.Controllers;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safadana.AvazehRetailManagement.DataLibraryCore.Services.TransactionService;
import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.TransactionListModel;
import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.TransactionModel;
import com.safadana.AvazehRetailManagement.SharedLibrary.DtoModels.ItemsForComboBox;

@RestController
@RequestMapping("/Transaction")
public class TransactionController {
    @Autowired
    TransactionService service;

    @GetMapping("/GetAll")
    public CompletableFuture<List<TransactionModel>> getAll() {
        return service.getAll();
    }

    @GetMapping("/GetWithPagination")
    public CompletableFuture<Page<TransactionListModel>> getWithPagination(@RequestParam String searchText,
            @RequestParam int offset,
            @RequestParam int pageSize,
            @RequestParam String sortColumn, @RequestParam String sortOrder) {
        return service.getWithPagination(searchText, offset, pageSize, sortColumn, sortOrder);
    }

    @GetMapping("/Id/{id}")
    public CompletableFuture<TransactionModel> getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping("/CreateUpdate")
    public CompletableFuture<TransactionModel> createUpdate(@RequestBody TransactionModel product) {
        return service.createUpdateProduct(product);
    }

    @DeleteMapping("/Delete/{id}")
    public void delete(@PathVariable int id) {
        service.deleteById(id);
    }

    @GetMapping("/ProductItems")
    public CompletableFuture<List<ItemsForComboBox>> getProductItems() {
        return service.getProductItems();
    }

    @GetMapping("/TransactionNames")
    public CompletableFuture<List<ItemsForComboBox>> getTransactionNames(@RequestParam Optional<Integer> id) {
        return service.getTransactionNames(id.isEmpty() ? 0 : id.get());
    }
}