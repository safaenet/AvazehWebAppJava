package com.safadana.AvazehRetailManagement.Services;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.safadana.AvazehRetailManagement.DAO.ProductDAO;
import com.safadana.AvazehRetailManagement.Helpers.PersianCalendarHelper;
import com.safadana.AvazehRetailManagement.Models.ItemsForComboBox;
import com.safadana.AvazehRetailManagement.Models.ProductModel;
import com.safadana.AvazehRetailManagement.Models.ProductUnitModel;

@Service
public class ProductService {
    @Autowired
    ProductDAO DAO;

    public CompletableFuture<List<ProductModel>> getAll() {
        return CompletableFuture.completedFuture(DAO.findAll(Sort.by(Sort.Direction.ASC, "productName")));
    }

    public CompletableFuture<Page<ProductModel>> getWithPagination(String searchText, int offset, int pageSize,
            String sortColumn,
            String sortOrder) {
        Sort.Direction sortDir = sortOrder.toUpperCase().equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        if (searchText == null || searchText == "")
            searchText = "%";
        else
            searchText = "%" + searchText.toUpperCase() + "%";
        if (sortColumn == null || sortColumn == "")
            sortColumn = "id";
        if (pageSize == 0)
            pageSize = 50;
        return DAO.findByMany(searchText,
                PageRequest.of(offset, pageSize).withSort(Sort.by(sortDir, sortColumn)));
    }

    public CompletableFuture<ProductModel> getById(long id) {
        if (id == 0)
            return null;
        return CompletableFuture.completedFuture(DAO.findById(id).get());
    }

    public CompletableFuture<ProductModel> getByBarcode(String barcode) {
        if (barcode == null || barcode == "")
            return null;
        return CompletableFuture.completedFuture(DAO.findByBarcode(barcode));
    }

    public CompletableFuture<ProductModel> createUpdate(ProductModel item) {
        if (item.getDateCreated() == null || item.getDateCreated() == "") {
            item.setDateCreated(PersianCalendarHelper.getPersianDateTime());
            item.setDateUpdated(PersianCalendarHelper.getPersianDateTime());
        }
        if (item.getId() > 0) {
            item.setDateUpdated(PersianCalendarHelper.getPersianDateTime());
        }
        return CompletableFuture.completedFuture(DAO.save(item));
    }

    public void deleteById(long id) {
        DAO.deleteById(id);
    }

    public CompletableFuture<List<ItemsForComboBox>> getProductItems() {
        return DAO.getProductItems();
    }

    public CompletableFuture<List<ProductUnitModel>> getProductUnits() {
        return DAO.getProductUnits();
    }
}
