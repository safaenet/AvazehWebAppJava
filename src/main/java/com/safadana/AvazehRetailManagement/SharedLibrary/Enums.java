package com.safadana.AvazehRetailManagement.SharedLibrary;

public class Enums {
    public enum SqlSearchMode {
        AND, OR
    }

    public enum ChequeEventTypes {
        None(0),
        Holding(1),
        Sold(2),
        NonSufficientFund(3),
        Cashed(4);
    
        private final int value;
    
        ChequeEventTypes(int value) {
            this.value = value;
        }
    
        public int getValue() {
            return value;
        }
    }
    

    public enum ChequeListQueryStatus {
        NotCashed(0),
        Cashed(1),
        Sold(2),
        NonSufficientFund(3),
        FromNowOn(4);
    
        private final int value;
    
        ChequeListQueryStatus(int value) {
            this.value = value;
        }
    
        public int getValue() {
            return value;
        }
    }

    public enum DiscountTypes {
        Percent(0),
        Amount(1);
    
        private final int value;
    
        DiscountTypes(int value) {
            this.value = value;
        }
    
        public int getValue() {
            return value;
        }
    }

    public enum InvoiceFinancialStatus {
        Balanced(0),
        Deptor(1),
        Creditor(2),
        Outstanding(3);
    
        private final int value;
    
        InvoiceFinancialStatus(int value) {
            this.value = value;
        }
    
        public int getValue() {
            return value;
        }
    }

    public enum InvoiceLifeStatus {
        Active(0),
        Inactive(1),
        Archive(2),
        Deleted(3);
    
        private final int value;
    
        InvoiceLifeStatus(int value) {
            this.value = value;
        }
    
        public int getValue() {
            return value;
        }
    }

    public enum TransactionFinancialStatus {
        Balanced(0),
        Positive(1),
        Negative(2);
    
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
    
    public enum ProductStatus {
        InActive(0),
        Active(1);
    
        private final int value;
    
        ProductStatus(int value) {
            this.value = value;
        }
    
        public int getValue() {
            return value;
        }
    }

    public enum SqlQuerySearchMode {
        Forward(0),
        Backward(1);
    
        private final int value;
    
        SqlQuerySearchMode(int value) {
            this.value = value;
        }
    
        public int getValue() {
            return value;
        }
    }
    
}
