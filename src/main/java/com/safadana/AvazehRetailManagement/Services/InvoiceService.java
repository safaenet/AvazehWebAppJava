package com.safadana.AvazehRetailManagement.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.safadana.AvazehRetailManagement.DAO.InvoiceDAO;
import com.safadana.AvazehRetailManagement.DAO.InvoiceItemDAO;
import com.safadana.AvazehRetailManagement.Helpers.PersianCalendarHelper;
import com.safadana.AvazehRetailManagement.Models.InvoiceItemModel;
import com.safadana.AvazehRetailManagement.Models.InvoiceListModel;
import com.safadana.AvazehRetailManagement.Models.InvoiceModel;
import com.safadana.AvazehRetailManagement.Models.InvoiceModel_DTO;

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

    @SuppressWarnings({ "null", "unchecked" })
    public CompletableFuture<Page<InvoiceListModel>> getWithPagination(Optional<String> searchText,
            Optional<String> invoiceStatus, Optional<String> invoiceDate, Optional<Long> customerId,
            int offset, int pageSize, String sortColumn, String sortOrder) {
        Sort.Direction sortDir = sortOrder.toUpperCase().equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;

        String SearchText = "%";
        if (searchText != null && searchText.isPresent())
            SearchText = "%" + searchText.get().toUpperCase() + "%";

        if (sortColumn == null || sortColumn == "")
            sortColumn = "id";

        String InvoiceStatus = "ALL";
        if (invoiceStatus != null && invoiceStatus.isPresent())
            InvoiceStatus = invoiceStatus.get();

        String InvoiceDate = "%";
        if (invoiceDate != null && invoiceDate.isPresent())
            InvoiceDate = "%" + invoiceDate.get() + "%";

        long CustomerId = 0;
        if (customerId != null && customerId.isPresent())
            CustomerId = customerId.get();

        if (pageSize == 0)
            pageSize = 50;

        Query query = entityManager.createNamedQuery("findInvoiceListByMany")
                .setParameter("customerId", CustomerId)
                .setParameter("invoiceDate", InvoiceDate)
                .setParameter("invoiceStatus", InvoiceStatus)
                .setParameter("searchText", SearchText);
        int total = query.getResultList().size();
        query.setFirstResult(offset * pageSize);
        query.setMaxResults(pageSize);
        List<InvoiceListModel> list = query.getResultList();
        Page<InvoiceListModel> page = new PageImpl<>(list,
                PageRequest.of(offset, pageSize).withSort(Sort.by(sortDir, sortColumn)), total);
        CompletableFuture<Page<InvoiceListModel>> completedFuture = CompletableFuture.completedFuture(page);
        return completedFuture;
    }

    @SuppressWarnings("unchecked")
    public CompletableFuture<InvoiceModel_DTO> getById(long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String nativeQuery = "SELECT i.id, c.*, i.about, i.dateCreated, i.dateUpdated, i.dateUpdated, ii.*," +
                                        "p.*, i.discountType, i.discountValue, i.descriptions, i.prevInvoiceId, calcinvoiceprevbalance(:invoiceId), InvFwds.fwdFactorNum AS fwdInvoiceId " +
                                        "FROM invoices i " +
                                        "LEFT JOIN customers c ON i.customer_id = c.id " +
                                        "LEFT JOIN invoiceitems ii ON i.id = ii.invoiceid " +
                                        "LEFT JOIN invoicepayments p ON i.id = p.invoiceid " +
                                        "LEFT JOIN (SELECT baseI.id AS FactorNum, prevI.id AS fwdFactorNum FROM invoices baseI LEFT JOIN invoices prevI ON baseI.id = prevI.previnvoiceid) AS InvFwds ON InvFwds.FactorNum = i.id" +
                                        "WHERE i.id = :invoiceId";
                List<Object[]> results = entityManager.createNativeQuery(nativeQuery).setParameter("invoiceId", id).getResultList();

                InvoiceModel_DTO invoice = null;
                List<InvoiceItemModel> items = new ArrayList<>();

                for (Object[] row : results) {
                    if (invoice == null) {
                        invoice = new InvoiceModel_DTO();
                        invoice.setId((Long) row[0]);
                    }

                    InvoiceItemModel item = new InvoiceItemModel();
                    item.setId((Long) row[2]);
                    item.setDescriptions((String) row[3]);

                    items.add(item);
                }

                if (invoice != null) {
                    invoice.setItems(items);
                }

                return invoice;
            } catch (Exception e) {
                return null;
            }
        });
    }

    public CompletableFuture<InvoiceModel> createUpdateProduct(InvoiceModel item) {
        if (PersianCalendarHelper.isValidPersianDateTime(item.getDateCreated()) == false)
            item.setDateCreated(PersianCalendarHelper.getPersianDateTime());
        item.setDateUpdated(PersianCalendarHelper.getPersianDateTime());
        return CompletableFuture.completedFuture(DAO.save(item));
    }

    public void deleteById(long id) {
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

    public CompletableFuture<Boolean> updateInvoiceDateUpdated(long invoiceId) {
        InvoiceModel invoice;
        invoice = DAO.getReferenceById(invoiceId);
        invoice.setDateUpdated(PersianCalendarHelper.getPersianDateTime());
        DAO.save(invoice);
        return CompletableFuture.completedFuture(true);
    }
}
