package com.safadana.AvazehRetailManagement.DataLibraryCore.Services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.safadana.AvazehRetailManagement.DataLibraryCore.DAO.InvoiceDAO;
import com.safadana.AvazehRetailManagement.DataLibraryCore.DAO.InvoiceItemDAO;
import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.InvoiceListModel;
import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.InvoiceModel;
import com.safadana.AvazehRetailManagement.SharedLibrary.Helpers.PersianCalendarHelper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
public class InvoiceService {
    @Autowired
    InvoiceDAO DAO;
    @Autowired
    InvoiceItemDAO itemDAO;
    @PersistenceContext
    private EntityManager entityManager;

    public CompletableFuture<List<InvoiceModel>> getAll() {
        return CompletableFuture.completedFuture(DAO.findAll());
    }

    public CompletableFuture<Page<InvoiceListModel>> getWithPagination(String lifeStatus, Long invoiceId, Long customerId, String date, String finStatus, String searchText,
        int offset, int pageSize, String sortColumn, String sortOrder) {
        Sort.Direction sortDir = sortOrder.toUpperCase().equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        if(lifeStatus == null || lifeStatus == "") lifeStatus = "ALL";
        if(date == null || date == "") date = "ALL"; //else date = "%" + date + "%";
        if(finStatus == null || finStatus == "") finStatus = "ALL";
        if(searchText == null || searchText == "") searchText = "%"; else searchText = "%" + searchText.toUpperCase() + "%";
        if(sortColumn == null || sortColumn == "") sortColumn = "id";
        if(pageSize == 0) pageSize = 50;
        Query query = entityManager.createNamedQuery("findInvoiceListByMany")
        .setParameter("lifeStatus", lifeStatus)
        .setParameter("invoiceId", invoiceId)
        .setParameter("customerId", customerId)
        .setParameter("date", date)
        .setParameter("finStatus", finStatus)
        .setParameter("searchText", searchText);
        int total = query.getResultList().size();
        query.setFirstResult(offset * pageSize);
        query.setMaxResults(pageSize);
        @SuppressWarnings("unchecked")
        List<InvoiceListModel> list = query.getResultList();
        Page<InvoiceListModel> page = new PageImpl<>(list, PageRequest.of(offset, pageSize).withSort(Sort.by(sortDir, sortColumn)), total);
        CompletableFuture<Page<InvoiceListModel>> completedFuture = CompletableFuture.completedFuture(page);
        return completedFuture;
    }

    public CompletableFuture<InvoiceModel> getById(Long id) {
        return CompletableFuture.completedFuture(DAO.findById(id).get());
    }

    public CompletableFuture<InvoiceModel> createUpdateProduct(InvoiceModel item) {
        if (PersianCalendarHelper.isValidPersianDateTime(item.getDateCreated()) == false)
            item.setDateCreated(PersianCalendarHelper.getPersianDateTime());
        item.setDateUpdated(PersianCalendarHelper.getPersianDateTime());
        return CompletableFuture.completedFuture(DAO.save(item));
    }

    public void deleteById(Long id) {
        itemDAO.deleteByInvoiceId(id);
        DAO.deleteById(id);
    }

    public CompletableFuture<List<String>> getInvoiceAbouts() {
        return DAO.getInvoiceAbouts();
    }

    public CompletableFuture<List<InvoiceListModel>> getPrevInvoices(Long invoiceId, Long customerId) {
        Query query = entityManager.createNamedQuery("findPrevInvoiceList")
        .setParameter("invoiceId", invoiceId)
        .setParameter("customerId", customerId);
        @SuppressWarnings("unchecked")
        List<InvoiceListModel> list = query.getResultList();
        CompletableFuture<List<InvoiceListModel>> completedFuture = CompletableFuture.completedFuture(list);
        return completedFuture;
    }

    public CompletableFuture<Boolean> updateInvoiceDateUpdated(Long invoiceId){
        InvoiceModel invoice;
        invoice = DAO.getReferenceById(invoiceId);
        invoice.setDateUpdated(PersianCalendarHelper.getPersianDateTime());
        DAO.save(invoice);
        return CompletableFuture.completedFuture(true);
    }
}
