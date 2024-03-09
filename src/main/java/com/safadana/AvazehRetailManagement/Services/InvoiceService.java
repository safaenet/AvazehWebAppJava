package com.safadana.AvazehRetailManagement.Services;

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
import com.safadana.AvazehRetailManagement.Models.CustomerModel;
import com.safadana.AvazehRetailManagement.Models.InvoiceItemModel_DTO;
import com.safadana.AvazehRetailManagement.Models.InvoiceListModel;
import com.safadana.AvazehRetailManagement.Models.InvoiceModel;
import com.safadana.AvazehRetailManagement.Models.InvoiceModel_DTO;
import com.safadana.AvazehRetailManagement.Models.InvoicePaymentModel;
import com.safadana.AvazehRetailManagement.Models.InvoiceSpecsUpdate_DTO;

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

    public CompletableFuture<InvoiceModel_DTO> getById(long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                InvoiceModel_DTO invoice = (InvoiceModel_DTO) entityManager.createNamedQuery("loadSingleInvoiceSpecs")
                        .setParameter("invoiceId", id).getSingleResult();
                CustomerModel customer = (CustomerModel) entityManager.createNamedQuery("loadCustomerByInvoiceId").setParameter("invoiceId", id).getSingleResult();
                invoice.setCustomer(customer);
                invoice.setItems(getInvoiceItemsByInvoiceId(id));
                invoice.setPayments(getInvoicePaymentsByInvoiceId(id));
                return invoice;
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        });
    }

    public List<InvoiceItemModel_DTO> getInvoiceItemsByInvoiceId(long id) {
        List<InvoiceItemModel_DTO> items = entityManager
                .createQuery("SELECT NEW com.safadana.AvazehRetailManagement.Models.InvoiceItemModel_DTO(ii.id, ii.invoiceId, ii.product.id AS productId, ii.product.productName AS productName,"  +
                "ii.buyPrice, ii.sellPrice, ii.countString, ii.countValue, ii.dateCreated, ii.dateUpdated, ii.delivered, ii.descriptions) FROM InvoiceItemModel ii WHERE ii.invoiceId = :invoiceId ORDER BY ii.dateCreated DESC",
                        InvoiceItemModel_DTO.class)
                .setParameter("invoiceId", id).getResultList();
        return items;
    }

    public List<InvoicePaymentModel> getInvoicePaymentsByInvoiceId(long id) {
        List<InvoicePaymentModel> payments = entityManager
                .createQuery("SELECT ip FROM InvoicePaymentModel ip WHERE ip.invoiceId = :invoiceId",
                        InvoicePaymentModel.class)
                .setParameter("invoiceId", id).getResultList();
        return payments;
    }

    public CompletableFuture<Integer> createUpdate(InvoiceSpecsUpdate_DTO item) {
        if (PersianCalendarHelper.isValidPersianDateTime(item.getDateCreated()) == false)
            item.setDateCreated(PersianCalendarHelper.getPersianDateTime());
        item.setDateUpdated(PersianCalendarHelper.getPersianDateTime());
        
        return CompletableFuture.supplyAsync(() -> {
            try {
                int afftectedRows = DAO.updateInvoiceSpecs(item.getId(), item.getCustomerId(), item.getAbout(), item.getDateCreated(), item.getDateUpdated(),
                item.getDiscountType(), item.getDiscountValue(), item.getDescriptions(), item.getPrevInvoiceId());
                return afftectedRows;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        });
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
