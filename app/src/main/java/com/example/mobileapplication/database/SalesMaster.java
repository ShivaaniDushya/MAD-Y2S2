package com.example.mobileapplication.database;

import android.provider.BaseColumns;

public class SalesMaster {

    public SalesMaster() {
    }

    public static class SalesT implements BaseColumns {
        public static final String TABLE_NAME = "sales";
        public static final String COLUMN_NAME_INVOICE_ID = "inv_id";
        public static final String COLUMN_NAME_CUSTOMER_ID = "cus_id";
        public static final String COLUMN_NAME_BALANCE = "balance";
        public static final String COLUMN_NAME_DELIVERY_DATE = "exp_del_date";
        public static final String COLUMN_NAME_CREATED_DATE = "created_date";
    }

}
