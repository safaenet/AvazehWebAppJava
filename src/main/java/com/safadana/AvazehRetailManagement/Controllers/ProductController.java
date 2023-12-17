package com.safadana.AvazehRetailManagement.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safadana.AvazehRetailManagement.DataLibraryCore.Services.ProductService;
import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.ProductModel;

@RestController
@RequestMapping("/Product")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/AllProducts")
    public List<ProductModel> getAllProducts() {
        return productService.getAll();
    }

    @GetMapping("/ProductsWithPagination/{searchText}/{offset}/{pageSize}/{sortColumn}/{sortOrder}")
    public List<ProductModel> getProductsWithPagination(@PathVariable String searchText, @PathVariable int offset, @PathVariable int pageSize,
            @PathVariable String sortColumn, @PathVariable String sortOrder) {
        return productService.getWithPagination(searchText, offset, pageSize, sortColumn, sortOrder);
    }

    @GetMapping("/Id/{id}")
    public ProductModel getProductById(@PathVariable int id) {
        return productService.getById(id);
    }

    @GetMapping("/Barcode/{barcode}")
    public ProductModel getProductById(@PathVariable String barcode) {
        return productService.getByBarcode(barcode);
    }
}