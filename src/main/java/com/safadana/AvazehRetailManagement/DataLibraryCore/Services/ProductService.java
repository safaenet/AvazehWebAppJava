package com.safadana.AvazehRetailManagement.DataLibraryCore.Services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.safadana.AvazehRetailManagement.DataLibraryCore.DAO.ProductDAO;
import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.ProductModel;

@Service
public class ProductService {
    @Autowired
    ProductDAO productDAO;

    public CompletableFuture<List<ProductModel>> getAll() {
        return CompletableFuture.completedFuture(productDAO.findAll());
    }

    public CompletableFuture<Page<ProductModel>> getWithPagination(String searchText, int offset, int pageSize,
            String sortColumn,
            String sortOrder) {
        Sort.Direction sortDir = sortColumn.toUpperCase().equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return productDAO.findByMany(searchText,
                PageRequest.of(offset, pageSize).withSort(Sort.by(sortDir, sortColumn)));
    }

    public CompletableFuture<ProductModel> getById(int id) {
        return productDAO.findById(id);
    }

    public CompletableFuture<ProductModel> getByBarcode(String barcode) {
        return CompletableFuture.completedFuture(productDAO.findByBarcode(barcode));
    }

    public CompletableFuture<ProductModel> createUpdateProduct(ProductModel product) {
        return CompletableFuture.completedFuture(productDAO.save(product));
    }

    public void deleteProduct(int id) {
        productDAO.deleteById(id);
    }
}
