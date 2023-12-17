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
import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.ProductModel;

@RestController
@RequestMapping("/Product")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/GetAll")
    public CompletableFuture<List<ProductModel>> getAll() {
        return productService.getAll();
    }

    @GetMapping("/GetWithPagination")
    public CompletableFuture<Page<ProductModel>> getProductsWithPagination(@RequestParam String searchText,
            @RequestParam int offset,
            @RequestParam int pageSize,
            @RequestParam String sortColumn, @RequestParam String sortOrder) {
        return productService.getWithPagination(searchText, offset, pageSize, sortColumn, sortOrder);
    }

    @GetMapping("/Id/{id}")
    public CompletableFuture<ProductModel> getProductById(@PathVariable int id) {
        return productService.getById(id);
    }

    @GetMapping("/Barcode/{barcode}")
    public CompletableFuture<ProductModel> getProductById(@PathVariable String barcode) {
        return productService.getByBarcode(barcode);
    }

    @PostMapping("/CreateUpdate")
    public CompletableFuture<ProductModel> createUpdate(@RequestBody ProductModel product) {
        return productService.createUpdateProduct(product);
    }

    @DeleteMapping("/Delete/{id}")
    public void delete(@PathVariable int id) {
        productService.deleteProduct(id);
    }
}