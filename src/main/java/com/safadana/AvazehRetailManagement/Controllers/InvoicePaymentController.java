package com.safadana.AvazehRetailManagement.Controllers;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safadana.AvazehRetailManagement.DataLibraryCore.Services.InvoicePaymentService;
import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.InvoicePaymentModel;

@CrossOrigin
@RestController
@RequestMapping("/InvoicePayment")
public class InvoicePaymentController {
    @Autowired
    InvoicePaymentService service;

    @GetMapping("/Id/{id}")
    public CompletableFuture<InvoicePaymentModel> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/CreateUpdate")
    public CompletableFuture<InvoicePaymentModel> createUpdate(@RequestBody InvoicePaymentModel item) {
        return service.createUpdate(item);
    }

    @DeleteMapping("/Delete/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}