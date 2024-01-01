package com.safadana.AvazehRetailManagement.Controllers;

import java.util.List;
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

import com.safadana.AvazehRetailManagement.DataLibraryCore.Services.InvoiceService;
import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.InvoiceListModel;
import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.InvoiceModel;

@RestController
@RequestMapping("/Invoice")
public class InvoiceController {
    @Autowired
    InvoiceService service;

    @GetMapping("/GetAll")
    public CompletableFuture<List<InvoiceModel>> getAll() {
        return service.getAll();
    }

    @GetMapping("/GetWithPagination")
    public CompletableFuture<Page<InvoiceListModel>> getWithPagination(@RequestParam String lifeStatus,
            @RequestParam int invoiceId, @RequestParam int customerId, @RequestParam String date,
            @RequestParam String finStatus, @RequestParam String searchText,
            @RequestParam int offset,
            @RequestParam int pageSize,
            @RequestParam String sortColumn, @RequestParam String sortOrder) {
        return service.getWithPagination(lifeStatus, invoiceId, customerId, date, finStatus, searchText, offset,
                pageSize, sortColumn, sortOrder);
    }

    @GetMapping("/Id/{id}")
    public CompletableFuture<InvoiceModel> getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping("/CreateUpdate")
    public CompletableFuture<InvoiceModel> createUpdate(@RequestBody InvoiceModel item) {
        return service.createUpdateProduct(item);
    }

    @DeleteMapping("/Delete/{id}")
    public void delete(@PathVariable int id) {
        service.deleteById(id);
    }

    @GetMapping("/InvoiceAbouts")
    public CompletableFuture<List<String>> getInvoiceAbouts() {
        return service.getInvoiceAbouts();
    }
}