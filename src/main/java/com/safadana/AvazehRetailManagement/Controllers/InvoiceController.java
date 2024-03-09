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

import com.safadana.AvazehRetailManagement.Models.InvoiceListModel;
import com.safadana.AvazehRetailManagement.Models.InvoiceModel;
import com.safadana.AvazehRetailManagement.Models.InvoiceModel_DTO;
import com.safadana.AvazehRetailManagement.Models.InvoiceSpecsUpdate_DTO;
import com.safadana.AvazehRetailManagement.Services.InvoiceService;

@CrossOrigin
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
    public CompletableFuture<Page<InvoiceListModel>> getWithPagination(
            @RequestParam("searchText") Optional<String> searchText,
            @RequestParam("invoiceStatus") Optional<String> invoiceStatus,
            @RequestParam("invoiceDate") Optional<String> invoiceDate,
            @RequestParam("customerId") Optional<Long> customerId,
            @RequestParam int offset,
            @RequestParam int pageSize,
            @RequestParam String sortColumn, @RequestParam String sortOrder) {
        return service.getWithPagination(searchText, invoiceStatus, invoiceDate, customerId, offset, pageSize,
                sortColumn, sortOrder);
    }

    @GetMapping("/Id/{id}")
    public CompletableFuture<InvoiceModel_DTO> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/CreateUpdate")
    public CompletableFuture<Integer> createUpdate(@RequestBody InvoiceSpecsUpdate_DTO item) {
        return service.createUpdate(item);
    }

    @DeleteMapping("/Delete/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

    @GetMapping("/InvoiceAbouts")
    public CompletableFuture<List<String>> getInvoiceAbouts() {
        return service.getInvoiceAbouts();
    }

    @GetMapping("/GetPrevInvoices/{invoiceId}/{customerId}")
    public CompletableFuture<List<InvoiceListModel>> getPrevInvoices(@PathVariable Long invoiceId,
            @PathVariable Long customerId) {
        return service.getPrevInvoices(invoiceId, customerId);
    }
}