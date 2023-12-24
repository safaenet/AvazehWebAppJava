package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import lombok.Data;

import java.util.List;


import com.safadana.AvazehRetailManagement.SharedLibrary.Enums.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "invoices")
@Data
@NamedNativeQuery(name = "InvoiceModel.findByMany", query = "WITH RECURSIVE InvoiceHierarchy AS (" +
                        "SELECT i.id, i.customer_id AS customerId, c.fullname AS customerFullName, i.about, i.datecreated, i.dateupdated, i.isActive, i.descriptions," +
                        "SFS AS totalInvoiceSum, PT AS totalInvoicePayments, i.previnvoiceid, CAST(0 AS double precision) AS prevInvoiceBalance, InvFwds.fwdFactorNum AS fwdInvoiceId " +
                        "FROM invoices i " +
                        "INNER JOIN customers c ON i.customer_id = c.id " +
                        "LEFT JOIN (SELECT COALESCE(ROUND(CASE WHEN i.discounttype = 0 THEN SUM(ii.countvalue * ii.sellprice) - (i.discountvalue / 100 * SUM(ii.countvalue * ii.sellprice)) WHEN i.discounttype = 1 THEN SUM(ii.countvalue * ii.sellprice) - i.discountvalue END), 0) AS SFS, i.Id AS SFN  FROM invoiceitems ii RIGHT JOIN invoices i ON ii.invoiceid = i.Id GROUP BY i.id, i.discounttype, i.discountvalue) AS InvSums ON InvSums.SFN = i.id " +
                        "LEFT JOIN (SELECT COALESCE(SUM(ip.payamount), 0) AS PT, i.id AS PFN FROM invoicepayments ip RIGHT JOIN invoices i ON ip.invoiceid = i.id GROUP BY i.id) AS InvPays ON InvPays.PFN = i.id " +
                        "LEFT JOIN (SELECT baseI.id AS FactorNum, prevI.id AS fwdFactorNum FROM invoices baseI LEFT JOIN invoices prevI ON baseI.id = prevI.previnvoiceid) AS InvFwds ON InvFwds.FactorNum = i.id " +
                        "WHERE i.id = InvSums.SFN AND i.id = PFN AND i.id = InvFwds.FactorNum AND i.previnvoiceid IS NULL " +

                        "UNION ALL " +

                        "SELECT i.id, i.customer_id AS customerId, c.fullname AS customerFullName, i.about, i.datecreated, i.dateupdated, i.isActive, i.descriptions," +
                        "SFS AS totalInvoiceSum, PT AS totalInvoicePayments, i.previnvoiceid, prevInvoice.invBalance AS prevInvoiceBalance, InvFwds.fwdFactorNum AS fwdInvoiceId " +
                        "FROM invoices i " +
                        "INNER JOIN customers c ON i.customer_id = c.id " +
                        "LEFT JOIN (" +
                                "SELECT i.id, InvSums.SFS - InvPays.PT AS invBalance " +
                                "FROM invoices i " +
                                "LEFT JOIN (SELECT COALESCE(ROUND(CASE WHEN i.discounttype = 0 THEN SUM(ii.countvalue * ii.sellprice) - (i.discountvalue / 100 * SUM(ii.countvalue * ii.sellprice)) WHEN i.discounttype = 1 THEN SUM(ii.countvalue * ii.sellprice) - i.discountvalue END), 0) AS SFS, i.Id AS SFN  FROM invoiceitems ii RIGHT JOIN invoices i ON ii.invoiceid = i.Id GROUP BY i.id, i.discounttype, i.discountvalue) AS InvSums ON InvSums.SFN = i.id " +
                                "LEFT JOIN (SELECT COALESCE(SUM(ip.payamount), 0) AS PT, i.id AS PFN FROM invoicepayments ip RIGHT JOIN invoices i ON ip.invoiceid = i.id GROUP BY i.id) AS InvPays ON InvPays.PFN = i.id " +
                        ") AS prevInvoice ON i.previnvoiceid = prevInvoice.id " +
                        "LEFT JOIN (SELECT ROUND(CASE WHEN i.discounttype = 0 THEN SUM(ii.countvalue * ii.sellprice) - (i.discountvalue / 100 * SUM(ii.countvalue * ii.sellprice)) WHEN i.discounttype = 1 THEN SUM(ii.countvalue * ii.sellprice) - i.discountvalue END) AS SFS, i.Id AS SFN  FROM invoiceitems ii RIGHT JOIN invoices i ON ii.invoiceid = i.Id GROUP BY i.id, i.discounttype, i.discountvalue) AS InvSums ON InvSums.SFN = i.id " +
                        "LEFT JOIN (SELECT COALESCE(SUM(ip.payamount), 0) AS PT, i.id AS PFN FROM invoicepayments ip RIGHT JOIN invoices i ON ip.invoiceid = i.id GROUP BY i.id) AS InvPays ON InvPays.PFN = i.id " +
                        "LEFT JOIN (SELECT baseI.id AS FactorNum, prevI.id AS fwdFactorNum FROM invoices baseI LEFT JOIN invoices prevI ON baseI.id = prevI.previnvoiceid) AS InvFwds ON InvFwds.FactorNum = i.id " +
                        "JOIN InvoiceHierarchy ih ON i.previnvoiceid = ih.id " +
                        "WHERE i.id = InvSums.SFN AND i.id = PFN AND i.id = InvFwds.FactorNum " +
                        ") " +

                        "SELECT * FROM InvoiceHierarchy ih " 
                        // "(:lifeStatus = 'ALL' OR (:lifeStatus = 'ACTIVE' AND ih.isactive = true) OR (:lifeStatus = 'INACTIVE' AND ih.isactive = false)) AND " +
                        // "(:invoiceId <= 0 OR ih.id = :invoiceId OR ih.previnvoiceid = :invoiceId OR ih.fwdInvoiceId = :invoiceId) AND " +
                        // "(:customerId <= 0 OR ih.customerId = :customerId) AND " +
                        // "(:date = 'ALL' OR ih.datecreated like :date OR ih.dateupdated LIKE :date) AND " +
                        // "(:finStatus = 'ALL' OR  " +
                        //         "(:finStatus = 'BALANCED' AND ih.totalInvoiceSum - ih.totalInvoicePayments + ih.prevInvoiceBalance = 0) OR " +
                        //         "(:finStatus = 'DEPTOR' AND ih.totalInvoiceSum - ih.totalInvoicePayments + ih.prevInvoiceBalance > 0) OR " +
                        //         "(:finStatus = 'CREDITOR' AND ih.totalInvoiceSum - ih.totalInvoicePayments + ih.prevInvoiceBalance < 0) OR " +
                        //         "(:finStatus = 'OVERDUE' AND ih.fwdInvoiceId IS NULL AND ih.totalInvoiceSum - ih.totalInvoicePayments + ih.prevInvoiceBalance <> 0)) AND " +
                        // "(:searchText = '%' OR ih.customerFullName LIKE :searchText OR ih.about LIKE :searchText OR ih.descriptions LIKE :searchText)"
                        , resultClass = InvoiceListModel.class)
public class InvoiceModel {
    @Id
    private int id;

    @ManyToOne(optional = false)
    private CustomerModel customer;

    @Column(length = 50)
    private String about;

    @Column(length = 20, nullable = false)
    private String dateCreated;

    @Column(length = 20)
    private String dateUpdated;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "invoiceId")
    private List<InvoiceItemModel> items;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "invoiceId")
    private List<InvoicePaymentModel> payments;

    @Column(nullable = false)
    private DiscountTypes discountType = DiscountTypes.AMOUNT;

    @Column(nullable = false)
    private double discountValue = 0;

    @Column(columnDefinition="TEXT")
    private String descriptions;

    @Column(nullable = false)
    private boolean isActive = true;

    @OneToOne
    @JoinColumn(name = "prevInvoiceId")
    private InvoiceModel prevInvoice;

    public double getTotalItemsBuySum() {
        return items == null || items.isEmpty() ? 0
                : items.stream().mapToDouble(InvoiceItemModel::getTotalBuyValue).sum();
    }

    public double getTotalItemsSellSum() {
        return items == null || items.isEmpty() ? 0
                : items.stream().mapToDouble(InvoiceItemModel::getTotalSellValue).sum();
    }

    public double getTotalDiscountAmount() {
        return discountType == DiscountTypes.PERCENT ? (getTotalItemsSellSum() * discountValue / 100) : discountValue;
    }

    public double getTotalInvoiceSum() {
        return getTotalItemsSellSum() - getTotalDiscountAmount();
    }

    public double getTotalPayments() {
        return payments == null || payments.isEmpty() ? 0
                : payments.stream().mapToDouble(InvoicePaymentModel::getPayAmount).sum();
    }

    public double getTotalInvoiceBalance() {
        return getTotalInvoiceSum() - getTotalPayments();
    }

    public double getTotalBalance() {
        if(prevInvoice == null) return getTotalInvoiceBalance();
        else return getTotalInvoiceBalance() + prevInvoice.getTotalInvoiceBalance();
    }

    public double getNetProfit() {
        return getTotalInvoiceSum() - getTotalItemsBuySum();
    }

    public double getCurrentProfit() {
        return getNetProfit() - getTotalInvoiceBalance();
    }

    public InvoiceFinancialStatus getInvoiceFinancialStatus() {
        return getTotalBalance() == 0 ? InvoiceFinancialStatus.BALANCED
                : getTotalBalance() > 0 ? InvoiceFinancialStatus.DEPTOR : InvoiceFinancialStatus.CREDITOR;
    }
}