package com.safadana.AvazehRetailManagement;

public class Enums {

    public enum ChequeStatus {
        NONE(0),
        ENDORSED(1), //Sold
        RETURNED(2), //NonSufficientFund
        CASHED(3);
    
        private final int value;
    
        ChequeStatus(int value) {
            this.value = value;
        }
    
        public int getValue() {
            return value;
        }
    }
    

    public enum ChequeQueryStatus {
        NOTCASHED(0),
        CASHED(1),
        ENDORSED(2), //Sold
        RETURNED(3),
        FROMNOWON(4);
    
        private final int value;
    
        ChequeQueryStatus(int value) {
            this.value = value;
        }
    
        public int getValue() {
            return value;
        }
    }

    public enum DiscountTypes {
        PERCENT(0),
        AMOUNT(1);
    
        private final int value;
    
        DiscountTypes(int value) {
            this.value = value;
        }
    
        public int getValue() {
            return value;
        }
    }

    public enum InvoiceFinancialStatus {
        BALANCED(0),
        DEPTOR(1),
        CREDITOR(2),
        OVERDUE(3); //Outstanding
    
        private final int value;
    
        InvoiceFinancialStatus(int value) {
            this.value = value;
        }
    
        public int getValue() {
            return value;
        }
    }

    public enum TransactionFinancialStatus {
        BALANCED(0),
        POSITIVE(1),
        NEGATIVE(2);
    
        private final int value;
    
        TransactionFinancialStatus(int value) {
            this.value = value;
        }
    
        public int getValue() {
            return value;
        }
    }

    public enum OrderType {
        ASC(0),
        DESC(1);
    
        private final int value;
    
        OrderType(int value) {
            this.value = value;
        }
    
        public int getValue() {
            return value;
        }
    }
}
