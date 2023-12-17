package com.safadana.AvazehRetailManagement.DataLibraryCore.Services;

import java.util.List;

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
    ProductDAO productDAO;

    public List<ProductModel> getAll() {
        return productDAO.findAll();
    }

    public Page<ProductModel> getWithPagination(String searchText, int offset, int pageSize, String sortColumn,
            String sortOrder) {
        Sort.Direction sortDir = sortColumn.toUpperCase().equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return productDAO.findByMany(searchText, PageRequest.of(offset, pageSize).withSort(Sort.by(sortDir, sortColumn)));
    }

    public ProductModel getById(int id) {
        return productDAO.findById(id).get();
    }

    public ProductModel getByBarcode(String barcode) {
        return productDAO.findByBarcode(barcode);
    }
}
