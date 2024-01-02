package com.safadana.AvazehRetailManagement.DataLibraryCore.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.InvoicePaymentModel;

@Repository
public interface InvoicePaymentDAO extends JpaRepository<InvoicePaymentModel, Integer> {

    void deleteByInvoiceId(int invoiceId);
    List<InvoicePaymentModel> findByInvoiceId(int invoiceId);
}
