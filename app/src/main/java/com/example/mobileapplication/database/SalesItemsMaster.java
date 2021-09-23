package com.example.mobileapplication.database;

import android.provider.BaseColumns;

public class SalesItemsMaster {

    public SalesItemsMaster() {
    }

    public static class SalesItemsT implements BaseColumns {
        public static final String TABLE_NAME = "sales_items";
        public static final String COLUMN_NAME_INVOICE_ID = "inv_id";
        public static final String COLUMN_NAME_ITEM_ID = "item_id";
        public static final String COLUMN_NAME_QTY = "qty";
        public static final String COLUMN_NAME_AMOUNT = "amount";
    }

}
