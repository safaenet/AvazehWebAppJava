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
import jakarta.persistence.Query;

@Service
public class InvoiceService {
    @Autowired
    InvoiceDAO DAO;
    @PersistenceContext
    private EntityManager entityManager;

    public CompletableFuture<List<InvoiceModel>> getAll() {
        return CompletableFuture.completedFuture(DAO.findAll());
    }

    public List<InvoiceListModel> getWithPagination(String lifeStatus, int invoiceId, int customerId, String date, String finStatus, String searchText,
        int offset, int pageSize,
        String sortColumn,
        String sortOrder) {
        Sort.Direction sortDir = sortOrder.toUpperCase().equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        if(lifeStatus == null || lifeStatus == "") lifeStatus = "ALL";
        if(date == null || date == "") date = "%"; //else date = "%" + date + "%";
        if(finStatus == null || finStatus == "") finStatus = "ALL";
        if(searchText == null || searchText == "") searchText = "%"; else searchText = "%" + searchText.toUpperCase() + "%";
        if(sortColumn == null || sortColumn == "") sortColumn = "id";
        if(pageSize == 0) pageSize = 50;
        List<InvoiceListModel> resultList = entityManager.createNamedQuery("findByMany", InvoiceListModel.class).getResultList();
        return resultList;
        // return DAO.findByMany(
        // PageRequest.of(offset, pageSize).withSort(Sort.by(sortDir, sortColumn)));
    }

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
}
