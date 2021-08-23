package com.example.mobileapplication.database;

import android.provider.BaseColumns;

public class ItemMaster {
    public ItemMaster() {
    }

    public static class  ItemsT implements BaseColumns {
        public static final String TABLE_NAME = "Items";
        public static final String COLUMN_ItemCode = "ItemCode";
        public static final String COLUMN_ItemName = "ItemName";
        public static final String COLUMN_ItemBrand = "ItemBrand";
        public static final String COLUMN_ItemCount = "ItemCount";
        public static final String COLUMN_ItemBuyPrice = "ItemBuyPrice";
        public static final String COLUMN_ItemSellPrice = "ItemSellPrice";
        public static final String COLUMN_ItemDescription = "ItemDescription";

    }
}
