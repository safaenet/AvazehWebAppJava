package com.safadana.AvazehRetailManagement.DataLibraryCore.Services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.safadana.AvazehRetailManagement.DataLibraryCore.DAO.CustomerDAO;
import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.CustomerModel;
import com.safadana.AvazehRetailManagement.SharedLibrary.DtoModels.ItemsForComboBox;
import com.safadana.AvazehRetailManagement.SharedLibrary.Helpers.PersianCalendarHelper;

@Service
public class CustomerService {
    @Autowired
    CustomerDAO DAO;

    public CompletableFuture<List<CustomerModel>> getAll() {
        return CompletableFuture.completedFuture(DAO.findAll());
    }

    public CompletableFuture<Page<CustomerModel>> getWithPagination(String searchText, int offset, int pageSize,
            String sortColumn,
            String sortOrder) {
        Sort.Direction sortDir = sortOrder.toUpperCase().equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        if(searchText == null || searchText == "") searchText = "%"; else searchText = "%" + searchText.toUpperCase() + "%";
        if(sortColumn == null || sortColumn == "") sortColumn = "id";
        if(pageSize == 0) pageSize = 50;   
        return DAO.findByMany(searchText,
                PageRequest.of(offset, pageSize).withSort(Sort.by(sortDir, sortColumn)));
    }

    public CompletableFuture<CustomerModel> getById(int id) {
        if(id == 0) return null;
        return CompletableFuture.completedFuture(DAO.findById(id).get());
    }

    public CompletableFuture<CustomerModel> createUpdate(CustomerModel item) {
        if (item.getDateJoined() == null || item.getDateJoined() == "") {
            item.setDateJoined(PersianCalendarHelper.getPersianDateTime());
        }
        return CompletableFuture.completedFuture(DAO.save(item));
    }

    public void deleteById(int id) {
        DAO.deleteById(id);
    }

    public CompletableFuture<List<ItemsForComboBox>> getCustomerNames() {
        return DAO.getCustomerNames();
    }

    public CompletableFuture<Double> getCustomerBalance(int customerId) {
        return DAO.getCustomerBalance(customerId);
    }
}
