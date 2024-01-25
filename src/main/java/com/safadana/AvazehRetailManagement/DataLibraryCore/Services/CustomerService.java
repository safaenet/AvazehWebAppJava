package com.safadana.AvazehRetailManagement.DataLibraryCore.Services;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.safadana.AvazehRetailManagement.DataLibraryCore.DAO.CustomerDAO;
import com.safadana.AvazehRetailManagement.SharedLibrary.Helpers.PersianCalendarHelper;
import com.safadana.AvazehRetailManagement.SharedLibrary.Models.CustomerModel;
import com.safadana.AvazehRetailManagement.SharedLibrary.Models.ItemsForComboBox;
import com.safadana.AvazehRetailManagement.SharedLibrary.Models.PhoneNumberModel;

@Service
public class CustomerService {
    @Autowired
    CustomerDAO DAO;

    public CompletableFuture<List<CustomerModel>> getAll() {
        return CompletableFuture.completedFuture(DAO.findAll());
    }

    public CompletableFuture<Page<CustomerModel>> getWithPagination(Optional<String> searchText, int offset, int pageSize,
            String sortColumn,
            String sortOrder) {
        Sort.Direction sortDir = sortOrder.toUpperCase().equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        String SearchText="";
        if(searchText == null || !searchText.isPresent()) SearchText = "%"; else SearchText = "%" + searchText.get().toUpperCase() + "%";
        if(sortColumn == null || sortColumn == "") sortColumn = "id";
        if(pageSize == 0) pageSize = 50;
        var result = DAO.findByMany(SearchText, PageRequest.of(offset, pageSize).withSort(Sort.by(sortDir, sortColumn)));
        return result;
    }

    public CompletableFuture<CustomerModel> getById(Long id) {
        if (id == 0)
            return null;
        var customer = DAO.findById(id).get();
        return CompletableFuture.completedFuture(customer);
    }

    public CompletableFuture<CustomerModel> createUpdate(CustomerModel item) {
        if (item.getDateJoined() == null || item.getDateJoined() == "") {
            item.setDateJoined(PersianCalendarHelper.getPersianDateTime());
        }

        return CompletableFuture.completedFuture(DAO.save(item));
    }

    public void deleteById(Long id) {
        DAO.deleteById(id);
    }

    public CompletableFuture<List<ItemsForComboBox>> getCustomerNames() {
        return DAO.getCustomerNames();
    }

    public CompletableFuture<Double> getCustomerBalance(Long customerId) {
        return DAO.getCustomerBalance(customerId);
    }

    public CompletableFuture<List<PhoneNumberModel>> getPhoneNumbers(Long customerId) {
        return DAO.getPhoneNumbers(customerId);
    }
}
