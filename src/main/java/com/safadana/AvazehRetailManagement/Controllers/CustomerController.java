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

import com.safadana.AvazehRetailManagement.Models.CustomerModel;
import com.safadana.AvazehRetailManagement.Models.ItemsForComboBox;
import com.safadana.AvazehRetailManagement.Models.PhoneNumberModel;
import com.safadana.AvazehRetailManagement.Services.CustomerService;

@CrossOrigin
@RestController
@RequestMapping("/Customer")
public class CustomerController {
    @Autowired
    CustomerService service;

    @GetMapping("/GetAll")
    public CompletableFuture<List<CustomerModel>> getAll() {
        return service.getAll();
    }

    @GetMapping("/GetWithPagination")
    public CompletableFuture<Page<CustomerModel>> getWithPagination(@RequestParam("searchText") Optional<String> searchText,
            @RequestParam int offset,
            @RequestParam int pageSize,
            @RequestParam String sortColumn, @RequestParam String sortOrder) {
        return service.getWithPagination(searchText, offset, pageSize, sortColumn, sortOrder);
    }

    @GetMapping("/Id/{id}")
    public CompletableFuture<CustomerModel> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/CreateUpdate")
    public CompletableFuture<CustomerModel> createUpdate(@RequestBody CustomerModel customer) {
        return service.createUpdate(customer);
    }

    @DeleteMapping("/Delete/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

    @GetMapping("/CustomerNames")
    public CompletableFuture<List<ItemsForComboBox>> getCustomerNames() {
        return service.getCustomerNames();
    }

    @GetMapping("/CustomerBalance/{customerId}")
    public CompletableFuture<Double> getCustomerBalance(@PathVariable Long customerId) {
        return service.getCustomerBalance(customerId);
    }

    @GetMapping("/Phones/{customerId}")
    public CompletableFuture<List<PhoneNumberModel>> getPhones(@PathVariable Long customerId) {
        return service.getPhoneNumbers(customerId);
    }
}