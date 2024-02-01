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

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class CustomerService {
    @Autowired
    CustomerDAO DAO;
    @PersistenceContext
    private EntityManager entityManager;

    public CompletableFuture<List<CustomerModel>> getAll() {
        return CompletableFuture.completedFuture(DAO.findAll());
    }

    public CompletableFuture<Page<CustomerModel>> getWithPagination(Optional<String> searchText, int offset,
            int pageSize,
            String sortColumn,
            String sortOrder) {
        Sort.Direction sortDir = sortOrder.toUpperCase().equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        String SearchText = "";
        if (searchText == null || !searchText.isPresent())
            SearchText = "%";
        else
            SearchText = "%" + searchText.get().toUpperCase() + "%";
        if (sortColumn == null || sortColumn == "")
            sortColumn = "id";
        if (pageSize == 0)
            pageSize = 50;
        var result = DAO.findByMany(SearchText,
                PageRequest.of(offset, pageSize).withSort(Sort.by(sortDir, sortColumn)));
        return result;
    }

    public CompletableFuture<CustomerModel> getById(Long id) {
        if (id == 0)
            return null;
        var customer = DAO.findById(id).get();
        return CompletableFuture.completedFuture(customer);
    }

    @Transactional
    public CompletableFuture<CustomerModel> createUpdate(CustomerModel item) {
        if (item.getDateJoined() == null || item.getDateJoined() == "") {
            item.setDateJoined(PersianCalendarHelper.getPersianDateTime());
        }
        // return CompletableFuture.completedFuture(DAO.save(item));
        if (item.getId() == 0) { // New Item
            String nativeQuery = "SELECT nextval('user_sequence_customers')";
            Long nextAvailableId = (Long) entityManager.createNativeQuery(nativeQuery).getSingleResult();
            nativeQuery = "INSERT INTO customers (id, fullname, companyname, emailaddress, postaddress, datejoined, descriptions) "
                    +
                    "VALUES (:id, :fullName, :companyName, :emailAddress, :postAddress, :dateJoined, :descriptions)";
            entityManager.createNativeQuery(nativeQuery)
                    .setParameter("id", nextAvailableId)
                    .setParameter("fullName", item.getFullName())
                    .setParameter("companyName", item.getCompanyName())
                    .setParameter("emailAddress", item.getEmailAddress())
                    .setParameter("postAddress", item.getPostAddress())
                    .setParameter("dateJoined", item.getDateJoined())
                    .setParameter("descriptions", item.getDescriptions())
                    .executeUpdate();
            item.setId(nextAvailableId);
            addPhoneNumbersToDb(item);

        } else if (item.getId() > 0) { // Update Item
            String nativeQuery = "UPDATE customers SET fullname = :fullName, companyname = :companyName, emailaddress = :emailAddress"
                    +
                    ", postaddress = :postAddress, datejoined = :dateJoined, descriptions = :descriptions WHERE id = :id";
            entityManager.createNativeQuery(nativeQuery)
                    .setParameter("id", item.getId())
                    .setParameter("fullName", item.getFullName())
                    .setParameter("companyName", item.getCompanyName())
                    .setParameter("emailAddress", item.getEmailAddress())
                    .setParameter("postAddress", item.getPostAddress())
                    .setParameter("dateJoined", item.getDateJoined())
                    .setParameter("descriptions", item.getDescriptions())
                    .executeUpdate();

            deletePhoneNumbersFromDb(item.getId());
            addPhoneNumbersToDb(item);
        }

        return CompletableFuture.completedFuture(item);
    }

    private void addPhoneNumbersToDb(CustomerModel item) {
        if (item.getPhoneNumbers() != null && !item.getPhoneNumbers().isEmpty()) {
            item.getPhoneNumbers().forEach((i) -> {
                String nativeQuery = "SELECT nextval('user_sequence_phonenumbers')";
                Long nextAvailableId = (Long) entityManager.createNativeQuery(nativeQuery)
                        .getSingleResult();
                nativeQuery = "INSERT INTO phonenumbers (id, customerid, phonenumber) VALUES (:id, :customerId, :phoneNumber)";
                entityManager.createNativeQuery(nativeQuery)
                        .setParameter("id", nextAvailableId)
                        .setParameter("customerId", item.getId())
                        .setParameter("phoneNumber", i.getPhoneNumber())
                        .executeUpdate();
                i.setId(nextAvailableId);
                i.setCustomerId(item.getId());
            });
        }
    }

    private void deletePhoneNumbersFromDb(long customerId) {
        String nativeQuery = "DELETE FROM phonenumbers WHERE customerid = :customerId";
        entityManager.createNativeQuery(nativeQuery)
                .setParameter("customerId", customerId)
                .executeUpdate();
    }

    @Transactional
    public void deleteById(long id) {
        deletePhoneNumbersFromDb(id);
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
