package com.safadana.AvazehRetailManagement.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safadana.AvazehRetailManagement.DAO.InvoiceItemDAO;
import com.safadana.AvazehRetailManagement.Helpers.PersianCalendarHelper;
import com.safadana.AvazehRetailManagement.Models.InvoiceItemModel;
import com.safadana.AvazehRetailManagement.Models.RecentSellPriceModel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;

@Service
public class InvoiceItemService {
    @Autowired
    InvoiceItemDAO DAO;
    @Autowired
    InvoiceService invoiceService;
    @PersistenceContext
    private EntityManager entityManager;

    public CompletableFuture<InvoiceItemModel> getById(long id) {
        return CompletableFuture.completedFuture(DAO.findById(id).get());
    }

    public CompletableFuture<InvoiceItemModel> createUpdate(InvoiceItemModel item) {
        if (item.getDateCreated() == null || item.getDateCreated() == "") item.setDateCreated(PersianCalendarHelper.getPersianDateTime());
        item.setDateUpdated(PersianCalendarHelper.getPersianDateTime());
        InvoiceItemModel invoiceItem = DAO.save(item);
        if(invoiceItem != null) invoiceService.updateInvoiceDateUpdated(invoiceItem.getInvoiceId());
        return CompletableFuture.completedFuture(invoiceItem);
    }

    public void deleteById(long id) {
        InvoiceItemModel item = DAO.getReferenceById(id);
        invoiceService.updateInvoiceDateUpdated(item.getInvoiceId());
        DAO.deleteById(id);
    }

    public CompletableFuture<List<RecentSellPriceModel>> getRecentSellPrices(Long customerId, Long productId, int maxRecord) {
        TypedQuery<Tuple> query = entityManager.createQuery(
            "SELECT ii.sellPrice AS sellPrice, ii.dateCreated AS dateSold " +
            "FROM InvoiceModel i " +
            "LEFT JOIN i.customer c LEFT JOIN i.items ii LEFT JOIN ii.product p " +
            "WHERE c.id = :customerId AND p.id = :productId " +
            "ORDER BY ii.dateCreated DESC",
            Tuple.class);
        query.setParameter("customerId", customerId);
        query.setParameter("productId", productId);
        query.setMaxResults(maxRecord);
        List<Tuple> results = query.getResultList();
        List<RecentSellPriceModel> recentSellPrices = new ArrayList<>();
        for (Tuple tuple : results) {
            long sellPrice = tuple.get("sellPrice", long.class);
            String dateSold = tuple.get("dateSold", String.class);
            RecentSellPriceModel recentSellPrice = new RecentSellPriceModel(sellPrice, dateSold);
            recentSellPrices.add(recentSellPrice);
        }
        CompletableFuture<List<RecentSellPriceModel>> completedFuture = CompletableFuture.completedFuture(recentSellPrices);
        return completedFuture;
    }
}
