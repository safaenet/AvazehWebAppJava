package com.safadana.AvazehRetailManagement.Controllers;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safadana.AvazehRetailManagement.Models.TransactionListModel;
import com.safadana.AvazehRetailManagement.Models.TransactionModel;
import com.safadana.AvazehRetailManagement.Services.TransactionService;

@CrossOrigin
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
    public CompletableFuture<Page<TransactionListModel>> getWithPagination(
            @RequestParam("searchText") Optional<String> searchText,
            @RequestParam("transactionStatus") Optional<String> transactionStatus,
            @RequestParam("transactionDate") Optional<String> transactionDate,
            @RequestParam int offset,
            @RequestParam int pageSize,
            @RequestParam String sortColumn, @RequestParam String sortOrder) {
        return service.getWithPagination(searchText, transactionStatus, transactionDate, offset, pageSize, sortColumn, sortOrder);
    }

    @GetMapping("/Id/{id}")
    public CompletableFuture<TransactionModel> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/CreateUpdate")
    public CompletableFuture<TransactionModel> createUpdate(@RequestBody TransactionModel item) {
        return service.createUpdate(item);
    }

    @DeleteMapping("/Delete/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}