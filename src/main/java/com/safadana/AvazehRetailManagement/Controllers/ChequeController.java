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

import com.safadana.AvazehRetailManagement.DataLibraryCore.Services.ChequeService;
import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.ChequeModel;

@CrossOrigin
@RestController
@RequestMapping("/Cheque")
public class ChequeController {
    @Autowired
    ChequeService service;

    @GetMapping("/GetAll")
    public CompletableFuture<List<ChequeModel>> getAll() {
        return service.getAll();
    }

    @GetMapping("/GetWithPagination")
    public CompletableFuture<Page<ChequeModel>> getWithPagination(@RequestParam String searchText,
            @RequestParam String chequeStatus,
            @RequestParam int offset,
            @RequestParam int pageSize,
            @RequestParam String sortColumn, @RequestParam String sortOrder) {
        return service.getWithPagination(searchText, chequeStatus, offset, pageSize, sortColumn, sortOrder);
    }

    @GetMapping("/Id/{id}")
    public CompletableFuture<ChequeModel> getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping("/CreateUpdate")
    public CompletableFuture<ChequeModel> createUpdate(@RequestBody ChequeModel product) {
        return service.createUpdate(product);
    }

    @DeleteMapping("/Delete/{id}")
    public void delete(@PathVariable int id) {
        service.deleteById(id);
    }

    @GetMapping("/GetBankNames")
    public CompletableFuture<List<String>> getBankNames() {
        return service.getBankNames();
    }

    @GetMapping("/GetCloseCheques")
    public CompletableFuture<List<ChequeModel>> getCloseCheques() {
        return service.getCloseCheques();
    }
}