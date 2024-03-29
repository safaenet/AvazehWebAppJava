package com.safadana.AvazehRetailManagement.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.safadana.AvazehRetailManagement.Models.InvoicePaymentModel;

@Repository
public interface InvoicePaymentDAO extends JpaRepository<InvoicePaymentModel, Long> {

    void deleteByInvoiceId(Long invoiceId);
    List<InvoicePaymentModel> findByInvoiceId(Long invoiceId);
}
