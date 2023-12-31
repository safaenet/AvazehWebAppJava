package com.safadana.AvazehRetailManagement.SharedLibrary.DalModels;

import lombok.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.safadana.AvazehRetailManagement.SharedLibrary.Enums.*;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;

@SqlResultSetMapping(
    name = "InvoiceListMapping",
    classes = @ConstructorResult(
        columns = { 
            @ColumnResult(name = "id", type = int.class),
            @ColumnResult(name = "customerId", type = int.class),
            @ColumnResult(name = "customerFullName", type = String.class),
            @ColumnResult(name = "about", type = String.class),
            @ColumnResult(name = "dateCreated", type = String.class),
            @ColumnResult(name = "dateUpdated", type = String.class),
            @ColumnResult(name = "isActive", type = boolean.class),
            @ColumnResult(name = "descriptions", type = String.class),
            @ColumnResult(name = "totalInvoiceSum", type = double.class),
            @ColumnResult(name = "totalInvoicePayments", type = double.class),
            @ColumnResult(name = "prevInvoiceId", type = Integer.class),
            @ColumnResult(name = "prevInvoiceBalance", type = double.class),
            @ColumnResult(name = "fwdInvoiceId", type = Integer.class)
        },
    targetClass = InvoiceListModel.class
    )
)
@NamedNativeQuery(name = "findByMany", query = "WITH RECURSIVE InvoiceHierarchy AS (" +
                        "SELECT i.id, i.customer_id AS customerId, c.fullname AS customerFullName, i.about, i.datecreated, i.dateupdated, i.isactive, i.descriptions," +
                        "SFS AS totalInvoiceSum, PT AS totalInvoicePayments, i.previnvoiceid, CAST(0.0 AS double precision) AS prevInvoiceBalance, InvFwds.fwdFactorNum AS fwdInvoiceId " +
                        "FROM invoices i " +
                        "INNER JOIN customers c ON i.customer_id = c.id " +
                        "LEFT JOIN (SELECT COALESCE(ROUND(CASE WHEN i.discounttype = 0 THEN SUM(ii.countvalue * ii.sellprice) - (i.discountvalue / 100 * SUM(ii.countvalue * ii.sellprice)) WHEN i.discounttype = 1 THEN SUM(ii.countvalue * ii.sellprice) - i.discountvalue END), 0) AS SFS, i.Id AS SFN  FROM invoiceitems ii RIGHT JOIN invoices i ON ii.invoiceid = i.Id GROUP BY i.id, i.discounttype, i.discountvalue) AS InvSums ON InvSums.SFN = i.id " +
                        "LEFT JOIN (SELECT COALESCE(SUM(ip.payamount), 0) AS PT, i.id AS PFN FROM invoicepayments ip RIGHT JOIN invoices i ON ip.invoiceid = i.id GROUP BY i.id) AS InvPays ON InvPays.PFN = i.id " +
                        "LEFT JOIN (SELECT baseI.id AS FactorNum, prevI.id AS fwdFactorNum FROM invoices baseI LEFT JOIN invoices prevI ON baseI.id = prevI.previnvoiceid) AS InvFwds ON InvFwds.FactorNum = i.id " +
                        "WHERE i.id = InvSums.SFN AND i.id = PFN AND i.id = InvFwds.FactorNum AND i.previnvoiceid IS NULL " +

                        "UNION ALL " +

                        "SELECT i.id, i.customer_id AS customerId, c.fullname AS customerFullName, i.about, i.datecreated, i.dateupdated, i.isactive, i.descriptions," +
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

                        "SELECT ih.id, ih.customerId, ih.customerFullName, ih.about, ih.datecreated AS dateCreated, ih.dateupdated AS dateUpdated, ih.isactive AS isActive, ih.descriptions, ih.totalInvoiceSum, " +
                        "ih.totalInvoicePayments, ih.previnvoiceid AS prevInvoiceId, ih.prevInvoiceBalance, ih.fwdInvoiceId FROM InvoiceHierarchy ih "
                        // +"WHERE " +
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
                        , resultSetMapping = "InvoiceListMapping")
@Entity
@Table(name = "invoices")
@Data
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prevInvoiceId")
    private InvoiceModel prevInvoice;

    @JsonIgnore
    public double getTotalItemsBuySum() {
        return items == null || items.isEmpty() ? 0
                : items.stream().mapToDouble(InvoiceItemModel::getTotalBuyValue).sum();
    }

    @JsonIgnore
    public double getTotalItemsSellSum() {
        return items == null || items.isEmpty() ? 0
                : items.stream().mapToDouble(InvoiceItemModel::getTotalSellValue).sum();
    }

    @JsonIgnore
    public double getTotalDiscountAmount() {
        return discountType == DiscountTypes.PERCENT ? (getTotalItemsSellSum() * discountValue / 100) : discountValue;
    }

    @JsonIgnore
    public double getTotalInvoiceSum() {
        return getTotalItemsSellSum() - getTotalDiscountAmount();
    }

    @JsonIgnore
    public double getTotalPayments() {
        return payments == null || payments.isEmpty() ? 0
                : payments.stream().mapToDouble(InvoicePaymentModel::getPayAmount).sum();
    }

    @JsonIgnore
    public double getTotalInvoiceBalance() {
        return getTotalInvoiceSum() - getTotalPayments();
    }

    @JsonIgnore
    public double getTotalBalance() {
        if(prevInvoice == null) return getTotalInvoiceBalance();
        else return getTotalInvoiceBalance() + prevInvoice.getTotalInvoiceBalance();
    }

    @JsonIgnore
    public double getNetProfit() {
        return getTotalInvoiceSum() - getTotalItemsBuySum();
    }

    @JsonIgnore
    public double getCurrentProfit() {
        return getNetProfit() - getTotalInvoiceBalance();
    }

    @JsonIgnore
    public InvoiceFinancialStatus getInvoiceFinancialStatus() {
        return getTotalBalance() == 0 ? InvoiceFinancialStatus.BALANCED
                : getTotalBalance() > 0 ? InvoiceFinancialStatus.DEPTOR : InvoiceFinancialStatus.CREDITOR;
    }
}