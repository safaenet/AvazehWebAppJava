package com.safadana.AvazehRetailManagement.SecurityAndSettingsModels;

import lombok.Data;

@Data
public class UserPermissionsModel {
    private boolean canViewCustomersList = true;
    private boolean canViewCustomerDetails = true;
    private boolean canViewProductsList = true;
    private boolean canViewProductDetails = true;
    private boolean canViewInvoicesList = true;
    private boolean canViewInvoiceDetails = true;
    private boolean canViewTransactionsList = true;
    private boolean canViewTransactionDetails = true;
    private boolean canViewChequesList = true;
    private boolean canViewChequeDetails = true;

    private boolean canAddNewCustomer = true;
    private boolean canAddNewProduct = true;
    private boolean canAddNewInvoice = true;
    private boolean canAddNewTransaction = true;
    private boolean canAddNewCheque = true;

    private boolean canEditCustomer = true;
    private boolean canEditProduct = true;
    private boolean canEditInvoice = true;
    private boolean canEditTransaction = true;
    private boolean canEditCheque = true;

    private boolean canDeleteCustomer = true;
    private boolean canDeleteProduct = true;
    private boolean canDeleteInvoice = true;
    private boolean canDeleteInvoiceItem = true;
    private boolean canDeleteTransaction = true;
    private boolean canDeleteTransactionItem = true;
    private boolean canDeleteCheque = true;

    private boolean canPrintInvoice = true;
    private boolean canPrintTransaction = true;
    private boolean canViewNetProfits = true;
    private boolean canUseBarcodeReader = true;

    private boolean canManageItself = true;
    private boolean canManageOthers = true;
}
