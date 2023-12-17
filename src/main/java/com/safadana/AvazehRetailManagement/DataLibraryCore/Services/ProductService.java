package com.safadana.AvazehRetailManagement.DataLibraryCore.Services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.safadana.AvazehRetailManagement.DataLibraryCore.DAO.ProductDAO;
import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.ProductModel;

@Service
public class ProductService {
    @Autowired
    ProductDAO DAO;

    public CompletableFuture<List<ProductModel>> getAll() {
        return CompletableFuture.completedFuture(DAO.findAll());
    }

    public CompletableFuture<Page<ProductModel>> getWithPagination(String searchText, int offset, int pageSize,
            String sortColumn,
            String sortOrder) {
        Sort.Direction sortDir = sortColumn.toUpperCase().equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return DAO.findByMany(searchText,
                PageRequest.of(offset, pageSize).withSort(Sort.by(sortDir, sortColumn)));
    }

    public CompletableFuture<ProductModel> getById(int id) {
        return CompletableFuture.completedFuture(DAO.findById(id).get());
    }

    public CompletableFuture<ProductModel> getByBarcode(String barcode) {
        return CompletableFuture.completedFuture(DAO.findByBarcode(barcode));
    }

    public CompletableFuture<ProductModel> createUpdateProduct(ProductModel product) {
        return CompletableFuture.completedFuture(DAO.save(product));
    }

    public void deleteById(int id) {
        DAO.deleteById(id);
    }
}
