package com.safadana.AvazehRetailManagement.SharedLibrary.SecurityAndSettingsModels;

import com.safadana.AvazehRetailManagement.SharedLibrary.Enums.OrderType;

import lombok.Data;

@Data
public class UserSettingsModel {
    private String colorNewItem = "#FFF5F533";
    private String colorSoldItem = "#ff66e6ff";
    private String colorNonSufficientFundItem = "#ffff9c7a";
    private String colorCashedItem = "#ff94ffb6";
    private String colorChequeNotification = "#fff4ff8c";
    private String colorUpdatedItem = "#FFDEDEDE";
    private String colorBalancedItem = "#ff94ffb6";
    private String colorDeptorItem = "#ffff9c7a";
    private String colorCreditorItem = "#ff7ad3ff";
    private String colorInactiveItem = "#ffc9c9c9";
    private String colorArchivedItem = "#ffffe0a3";
    private String colorDeletedItem = "#ffff6b6b";
    private String colorNegativeProfit = "#ffffadad";
    private String colorPositiveItem = "#ff7ad3ff";
    private String colorNegativeItem = "#ffff9c7a";

    private int dataGridFontSize = 12;
    private int chequeListPageSize = 50;
    private OrderType chequeListQueryOrderType = OrderType.DESC;
    private int chequeNotifyDays = 2;
    private boolean chequeNotify = true;
    private int invoicePageSize = 50;
    private OrderType invoiceListQueryOrderType = OrderType.DESC;
    private OrderType invoiceDetailQueryOrderType = OrderType.DESC;
    private int transactionListPageSize = 50;
    private int transactionDetailPageSize = 50;
    private OrderType transactionListQueryOrderType = OrderType.DESC;
    private OrderType transactionDetailQueryOrderType = OrderType.DESC;
    private boolean autoSelectPersianLanguage;
    private int transactionShortcut1Id = 0;
    private int transactionShortcut2Id = 0;
    private int transactionShortcut3Id = 0;
    private String transactionShortcut1Name = "میانبر یک";
    private String transactionShortcut2Name = "میانبر دو";
    private String transactionShortcut3Name = "میانبر سه";
    private boolean askToAddNotExistingProduct = true;
    private boolean searchWhenTyping;
    private int customerListPageSize = 100;
    private OrderType customerListQueryOrderType = OrderType.DESC;
    private int productListPageSize = 100;
    private OrderType productListQueryOrderType = OrderType.DESC;
}
