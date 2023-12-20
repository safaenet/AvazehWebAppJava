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
import com.safadana.AvazehRetailManagement.SharedLibrary.Helpers.PersianCalendarHelper;

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

    public CompletableFuture<ProductModel> createUpdateProduct(ProductModel item) {
        if (item.getDateCreated() == null || item.getDateCreated() == "") {
            item.setDateCreated(PersianCalendarHelper.getPersianDateTime());
        }
        item.setDateUpdated(PersianCalendarHelper.getPersianDateTime());
        return CompletableFuture.completedFuture(DAO.save(item));
    }

    public void deleteById(int id) {
        DAO.deleteById(id);
    }
}
