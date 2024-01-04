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

import com.safadana.AvazehRetailManagement.DataLibraryCore.Services.ProductService;
import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.ItemsForComboBox;
import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.ProductModel;
import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.ProductUnitModel;

@RestController
@RequestMapping("/Product")
public class ProductController {
    @Autowired
    ProductService service;

    @GetMapping("/GetAll")
    public CompletableFuture<List<ProductModel>> getAll() {
        return service.getAll();
    }

    @GetMapping("/GetWithPagination")
    public CompletableFuture<Page<ProductModel>> getWithPagination(@RequestParam String searchText,
            @RequestParam int offset,
            @RequestParam int pageSize,
            @RequestParam String sortColumn, @RequestParam String sortOrder) {
        return service.getWithPagination(searchText, offset, pageSize, sortColumn, sortOrder);
    }

    @GetMapping("/Id/{id}")
    public CompletableFuture<ProductModel> getById(@PathVariable int id) {
        return service.getById(id);
    }

    @GetMapping("/Barcode/{barcode}")
    public CompletableFuture<ProductModel> getByBarcode(@PathVariable String barcode) {
        return service.getByBarcode(barcode);
    }

    @PostMapping("/CreateUpdate")
    public CompletableFuture<ProductModel> createUpdate(@RequestBody ProductModel product) {
        return service.createUpdate(product);
    }

    @DeleteMapping("/Delete/{id}")
    public void delete(@PathVariable int id) {
        service.deleteById(id);
    }

    @GetMapping("/ProductItems") //loads active products
    public CompletableFuture<List<ItemsForComboBox>> getProductItems() {
        return service.getProductItems();
    }

    @GetMapping("/ProductUnits")
    public CompletableFuture<List<ProductUnitModel>> getProductUnits() {
        return service.getProductUnits();
    }
}