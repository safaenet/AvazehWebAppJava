package com.safadana.AvazehRetailManagement.Services;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safadana.AvazehRetailManagement.DAO.InvoicePaymentDAO;
import com.safadana.AvazehRetailManagement.Helpers.PersianCalendarHelper;
import com.safadana.AvazehRetailManagement.Models.InvoicePaymentModel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class InvoicePaymentService {
    @Autowired
    InvoicePaymentDAO DAO;
    @Autowired
    InvoiceService invoiceService;
    @PersistenceContext
    private EntityManager entityManager;

    public CompletableFuture<InvoicePaymentModel> getById(Long id) {
        return CompletableFuture.completedFuture(DAO.findById(id).get());
    }

    public CompletableFuture<InvoicePaymentModel> createUpdate(InvoicePaymentModel item) {
        if (item.getDateCreated() == null || item.getDateCreated() == "") item.setDateCreated(PersianCalendarHelper.getPersianDateTime());
        item.setDateUpdated(PersianCalendarHelper.getPersianDateTime());
        InvoicePaymentModel invoiceItem = DAO.save(item);
        if(invoiceItem != null) invoiceService.updateInvoiceDateUpdated(invoiceItem.getInvoiceId());
        return CompletableFuture.completedFuture(invoiceItem);
    }

    public void deleteById(Long id) {
        InvoicePaymentModel item = DAO.getReferenceById(id);
        invoiceService.updateInvoiceDateUpdated(item.getInvoiceId());
        DAO.deleteById(id);
    }
}
