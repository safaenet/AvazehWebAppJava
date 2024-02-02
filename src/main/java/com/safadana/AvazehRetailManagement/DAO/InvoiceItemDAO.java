package com.safadana.AvazehRetailManagement.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.safadana.AvazehRetailManagement.Models.InvoiceItemModel;

@Repository
public interface InvoiceItemDAO extends JpaRepository<InvoiceItemModel, Long> {

    void deleteByInvoiceId(Long invoiceId);
    List<InvoiceItemModel> findByInvoiceId(Long invoiceId);
}
