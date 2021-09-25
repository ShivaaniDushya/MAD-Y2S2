package com.example.mobileapplication.database;

import android.provider.BaseColumns;

public class PaymentMaster {

    public PaymentMaster() {
    }

    public static class PaymentsT implements BaseColumns {
        public static final String TABLE_NAME = "payments";
        public static final String COLUMN_NAME_INVOICE_ID = "inv_id";
        public static final String COLUMN_NAME_INVOICE_AMOUNT = "inv_amount";
        public static final String COLUMN_NAME_PAYMENT = "payment";
        public static final String COLUMN_NAME_PAYMENT_DATE = "date";
        public static final String COLUMN_NAME_BALANCE = "balance";
    }

}
