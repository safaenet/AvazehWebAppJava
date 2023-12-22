package com.safadana.AvazehRetailManagement.DataLibraryCore.Services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.safadana.AvazehRetailManagement.DataLibraryCore.DAO.InvoiceDAO;
import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.InvoiceListModel;
import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.InvoiceModel;
import com.safadana.AvazehRetailManagement.SharedLibrary.DtoModels.ItemsForComboBox;
import com.safadana.AvazehRetailManagement.SharedLibrary.Helpers.PersianCalendarHelper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class InvoiceService {
    @Autowired
    InvoiceDAO DAO;

    public CompletableFuture<List<InvoiceModel>> getAll() {
        return CompletableFuture.completedFuture(DAO.findAll());
    }

    // public CompletableFuture<Page<InvoiceListModel>> getWithPagination(String searchText, int offset, int pageSize,
    //     String sortColumn,
    //     String sortOrder) {
    //     Sort.Direction sortDir = sortOrder.toUpperCase().equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
    //     if(searchText == null || searchText == "") searchText = "%";
    //     if(sortColumn == null || sortColumn == "") sortColumn = "id"; else searchText = "%" + searchText.toUpperCase() + "%";
    //     if(pageSize == 0) pageSize = 50;  
    //     return DAO.findByMany(searchText,
    //     PageRequest.of(offset, pageSize).withSort(Sort.by(sortDir, sortColumn)));
    // }

    public CompletableFuture<InvoiceModel> getById(int id) {
        return CompletableFuture.completedFuture(DAO.findById(id).get());
    }

    public CompletableFuture<InvoiceModel> createUpdateProduct(InvoiceModel item) {
        if (PersianCalendarHelper.isValidPersianDateTime(item.getDateCreated()) == false)
            item.setDateCreated(PersianCalendarHelper.getPersianDateTime());
        item.setDateUpdated(PersianCalendarHelper.getPersianDateTime());
        return CompletableFuture.completedFuture(DAO.save(item));
    }

    public void deleteById(int id) {
        DAO.deleteById(id);
    }

    public CompletableFuture<List<ItemsForComboBox>> getProductItems() {
        return DAO.getProductItems();
    }

    @PersistenceContext
    private EntityManager entityManager;
}
