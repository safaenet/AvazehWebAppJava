package com.safadana.AvazehRetailManagement.Controllers;

import java.util.List;
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

import com.safadana.AvazehRetailManagement.Models.TransactionItemModel;
import com.safadana.AvazehRetailManagement.Services.TransactionItemService;

@CrossOrigin
@RestController
@RequestMapping("/TransactionItem")
public class TransactionItemController {
    @Autowired
    TransactionItemService service;

    @GetMapping("/GetAll/{transactionId}")
    public CompletableFuture<List<TransactionItemModel>> getAll(@PathVariable Long transactionId) {
        return service.getAll(transactionId);
    }

    @GetMapping("/GetWithPagination/{transactionId}")
    public CompletableFuture<Page<TransactionItemModel>> getWithPagination(@PathVariable Long transactionId,
            @RequestParam String searchText,
            @RequestParam int offset,
            @RequestParam int pageSize,
            @RequestParam String sortColumn, @RequestParam String sortOrder) {
        return service.getWithPagination(transactionId, searchText, offset, pageSize, sortColumn, sortOrder);
    }

    @GetMapping("/Id/{id}")
    public CompletableFuture<TransactionItemModel> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/CreateUpdate")
    public CompletableFuture<TransactionItemModel> createUpdate(@RequestBody TransactionItemModel item) {
        return service.createUpdate(item);
    }

    @DeleteMapping("/Delete/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}