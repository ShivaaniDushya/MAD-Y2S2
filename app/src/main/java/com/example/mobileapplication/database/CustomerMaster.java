package com.example.mobileapplication.database;

import android.provider.BaseColumns;

public class CustomerMaster {
    public CustomerMaster() {
    }

    public static class CustomerT implements BaseColumns {
        public static final String TABLE_NAME = "customer";
        public static final String COLUMN_NAME_CUSTOMER_ID = "customer_id";
        public static final String COLUMN_NAME_CUSTOMER_NAME = "customer_name";
        public static final String COLUMN_NAME_STORE_NAME = "store_name";
        public static final String COLUMN_NAME_MOBILE = "mobile";
        public static final String COLUMN_NAME_STREET_ADDRESS = "street_address";
        public static final String COLUMN_NAME_CITY = "city";
        public static final String COLUMN_NAME_CREATED_DATE = "created_date";
        public static final String COLUMN_NAME_MODIFIED_DATE = "modified_date";
        public static final String COLUMN_NAME_PP_URL = "pp_url";
        public static final String COLUMN_NAME_SP_URL = "sp_url";
        public static final String COLUMN_NAME_CX_route = "cust_route";
    }
}
