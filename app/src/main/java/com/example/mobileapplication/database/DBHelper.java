package com.example.mobileapplication.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.example.mobileapplication.Product;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserInfo.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }
    //change the DB version when upgrading the DB


    @Override
    public void onCreate(SQLiteDatabase db) {        //creating the table
        Log.d("workflow", "DB onCreate method Called");
        String SQL_CREATE_ROUTES_TABLE =
                "CREATE TABLE "
                        + RouteMaster.RoutesT.TABLE_NAME +
                        " ("
                        + RouteMaster.RoutesT.COLUMN_NAME_ROUTE_ID +
                        " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + RouteMaster.RoutesT.COLUMN_NAME_START_LOCATION +
                        " TEXT, "
                        + RouteMaster.RoutesT.COLUMN_NAME_END_LOCATION +
                        " TEXT, "
                        + RouteMaster.RoutesT.COLUMN_NAME_DISTANCE +
                        " REAL, "
                        + RouteMaster.RoutesT.COLUMN_NAME_CREATED_DATE +
                        " TEXT, "
                        + RouteMaster.RoutesT.COLUMN_NAME_MODIFIED_DATE +
                        " TEXT, "
                        + RouteMaster.RoutesT.COLUMN_NAME_IS_DEFAULT +
                        " INTEGER" + ")";


        String SQL_CREATE_ITEMS =
                "CREATE TABLE "
                        + ItemMaster.ItemsT.TABLE_NAME +
                        " ("
                        + ItemMaster.ItemsT.COLUMN_ItemCode +
                        " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + ItemMaster.ItemsT.COLUMN_ItemName +
                        " TEXT, "
                        + ItemMaster.ItemsT.COLUMN_ItemBrand +
                        " TEXT, "
                        + ItemMaster.ItemsT.COLUMN_ItemCount +
                        " INTEGER, "
                        + ItemMaster.ItemsT.COLUMN_ItemBuyPrice +
                        " REAL, "
                        + ItemMaster.ItemsT.COLUMN_ItemSellPrice +
                        " REAL, "
                        + ItemMaster.ItemsT.COLUMN_ItemDescription +
                        " TEXT"
                        + ")";

        String SQL_CREATE_CUSTOMER =
                "CREATE TABLE "
                        + CustomerMaster.CustomerT.TABLE_NAME +
                        " ("
                        + CustomerMaster.CustomerT.COLUMN_NAME_CUSTOMER_ID +
                        " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + CustomerMaster.CustomerT.COLUMN_NAME_CUSTOMER_NAME +
                        " TEXT, "
                        + CustomerMaster.CustomerT.COLUMN_NAME_STORE_NAME +
                        " TEXT, "
                        + CustomerMaster.CustomerT.COLUMN_NAME_MOBILE +
                        " INTEGER, "
                        + CustomerMaster.CustomerT.COLUMN_NAME_STREET_ADDRESS +
                        " TEXT, "
                        + CustomerMaster.CustomerT.COLUMN_NAME_CITY +
                        " TEXT, "
                        + CustomerMaster.CustomerT.COLUMN_NAME_CREATED_DATE +
                        " TEXT, "
                        + CustomerMaster.CustomerT.COLUMN_NAME_MODIFIED_DATE +
                        " TEXT, "
                        + CustomerMaster.CustomerT.COLUMN_NAME_PP_URL +
                        " TEXT, "
                        + CustomerMaster.CustomerT.COLUMN_NAME_SP_URL +
                        " TEXT, "
                        + CustomerMaster.CustomerT.COLUMN_NAME_CX_route +
                        " INTEGER,FOREIGN KEY"
                        + "("
                        + CustomerMaster.CustomerT.COLUMN_NAME_CX_route
                        + ") "
                        + " REFERENCES "
                        + RouteMaster.RoutesT.TABLE_NAME
                        + "("
                        + RouteMaster.RoutesT.COLUMN_NAME_ROUTE_ID
                        + ")"
                        + ")";

        String SQL_CREATE_SALES_TABLE =
                "CREATE TABLE "
                        + SalesMaster.SalesT.TABLE_NAME +
                        " ("
                        + SalesMaster.SalesT.COLUMN_NAME_INVOICE_ID +
                        " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + SalesMaster.SalesT.COLUMN_NAME_CUSTOMER_ID +
                        " INTEGER, "
                        + SalesMaster.SalesT.COLUMN_NAME_INVOICE_AMOUNT +
                        " REAL, "
                        + SalesMaster.SalesT.COLUMN_NAME_BALANCE +
                        " REAL, "
                        + SalesMaster.SalesT.COLUMN_NAME_DELIVERY_DATE +
                        " TEXT, "
                        + SalesMaster.SalesT.COLUMN_NAME_CREATED_DATE +
                        " TEXT, "
                        + SalesMaster.SalesT.COLUMN_NAME_IS_URGENT +
                        " TEXT, "
                        + "FOREIGN KEY"
                        + "("
                        + SalesMaster.SalesT.COLUMN_NAME_CUSTOMER_ID
                        + ") "
                        +" REFERENCES "
                        + CustomerMaster.CustomerT.TABLE_NAME
                        + "("
                        + CustomerMaster.CustomerT.COLUMN_NAME_CUSTOMER_ID
                        + ")"
                        + ")";

        String SQL_CREATE_SALES_ITEMS_TABLE =
                "CREATE TABLE "
                        + SalesItemsMaster.SalesItemsT.TABLE_NAME +
                        " ("
                        + SalesItemsMaster.SalesItemsT.COLUMN_NAME_INVOICE_ID +
                        " INTEGER,"
                        + SalesItemsMaster.SalesItemsT.COLUMN_NAME_ITEM_ID +
                        " INTEGER,"
                        + SalesItemsMaster.SalesItemsT.COLUMN_NAME_QTY +
                        " TEXT, "
                        + SalesItemsMaster.SalesItemsT.COLUMN_NAME_AMOUNT +
                        " TEXT, "
                        + "PRIMARY KEY"
                        +"("
                        + SalesItemsMaster.SalesItemsT.COLUMN_NAME_INVOICE_ID
                        +", "
                        + SalesItemsMaster.SalesItemsT.COLUMN_NAME_ITEM_ID
                        +"), "
                        + "FOREIGN KEY"
                        +"("
                        + SalesItemsMaster.SalesItemsT.COLUMN_NAME_INVOICE_ID
                        +") "
                        +" REFERENCES "
                        + SalesMaster.SalesT.TABLE_NAME
                        +"("
                        + SalesMaster.SalesT.COLUMN_NAME_INVOICE_ID
                        + "),"
                        + "FOREIGN KEY"
                        + "("
                        + SalesItemsMaster.SalesItemsT.COLUMN_NAME_ITEM_ID
                        + ") "
                        + " REFERENCES "
                        + ItemMaster.ItemsT.TABLE_NAME
                        + "("
                        + ItemMaster.ItemsT.COLUMN_ItemCode
                        + ")"
                        + ")";

        String SQL_CREATE_PAYMENT_TABLE =
                "CREATE TABLE "
                        + PaymentMaster.PaymentT.TABLE_NAME +
                        " ("
                        + PaymentMaster.PaymentT.COLUMN_NAME_INVOICE_ID +
                        " INTEGER, "
                        + PaymentMaster.PaymentT.COLUMN_NAME_PAYMENT_DATE +
                        " TEXT, "
                        + PaymentMaster.PaymentT.COLUMN_NAME_PAYMENT +
                        " TEXT, "
                        + "PRIMARY KEY"
                        + "("
                        + PaymentMaster.PaymentT.COLUMN_NAME_INVOICE_ID
                        + ", "
                        + PaymentMaster.PaymentT.COLUMN_NAME_PAYMENT_DATE
                        + "), "
                        + "FOREIGN KEY"
                        + "("
                        + SalesItemsMaster.SalesItemsT.COLUMN_NAME_INVOICE_ID
                        + ") "
                        +" REFERENCES "
                        + SalesMaster.SalesT.TABLE_NAME
                        + "("
                        + SalesMaster.SalesT.COLUMN_NAME_INVOICE_ID
                        + ")"
                        + ")";


        //defining the sql query
        db.execSQL(SQL_CREATE_ROUTES_TABLE);//Execute the table creation
        Log.d("DBcreation", "Db created succesfully");
        db.execSQL(SQL_CREATE_ITEMS);
        db.execSQL(SQL_CREATE_CUSTOMER); //Execute the customer table creation
        Log.d("DBcreation", SQL_CREATE_CUSTOMER);
        Log.d("workflow", "Customer Db created successfully");
        db.execSQL(SQL_CREATE_SALES_TABLE); //Execute the sales table creation
        Log.d("workflow", "Sales table created successfully");
        db.execSQL(SQL_CREATE_SALES_ITEMS_TABLE); //Execute the sales_items table creation
        Log.d("workflow", "SalesItems table created successfully");
        db.execSQL(SQL_CREATE_PAYMENT_TABLE); //Execute the payment table creation
        Log.d("workflow", "Payment table created successfully");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("workflow", "DB Onupgrade method Called");
        db.execSQL("DROP TABLE IF EXISTS " + CustomerMaster.CustomerT.TABLE_NAME);
       db.execSQL("DROP TABLE IF EXISTS " + RouteMaster.RoutesT.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ItemMaster.ItemsT.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SalesMaster.SalesT.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SalesItemsMaster.SalesItemsT.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PaymentMaster.PaymentT.TABLE_NAME);

      //  Create tables again
        onCreate(db);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getTimeStamp() {
        Log.d("workflow", "DB gettimestamop method Called");

        LocalDateTime myDateobj = LocalDateTime.now();
        DateTimeFormatter myformatobj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String timeStamp = myDateobj.format(myformatobj);
        return timeStamp;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public long addRoutes(String startloc, String endloc, float distance, String isdefault) //enter all the parameter to be added to DB
    {
        Log.d("workflow", "DB addRoutes method Called");

        String timeadd = getTimeStamp();
        Log.d("workflow", "DB gettimpstamp method Called");

        SQLiteDatabase db = getWritableDatabase();// get the data repository in writable mode

        ContentValues values = new ContentValues();  //create a new map of values , where column names the key
        values.put(RouteMaster.RoutesT.COLUMN_NAME_START_LOCATION, startloc);
        values.put(RouteMaster.RoutesT.COLUMN_NAME_END_LOCATION, endloc);
        values.put(RouteMaster.RoutesT.COLUMN_NAME_DISTANCE, distance);
        values.put(RouteMaster.RoutesT.COLUMN_NAME_CREATED_DATE, timeadd);
        values.put(RouteMaster.RoutesT.COLUMN_NAME_MODIFIED_DATE, "N/A");
        values.put(RouteMaster.RoutesT.COLUMN_NAME_IS_DEFAULT, isdefault);

        long newRowID = db.insert(RouteMaster.RoutesT.TABLE_NAME, null, values); //Insert a new row and returning the primary
        //key values of the new row

        Log.d("workflow", "DB addRoutes method Called finished");

        return newRowID;
    }

    public List<String> getstartStoplocation() {
        List<String> list = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + RouteMaster.RoutesT.TABLE_NAME
                + " ORDER BY " +
                RouteMaster.RoutesT.COLUMN_NAME_IS_DEFAULT
                + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1) + " - " + cursor.getString(2));//adding 2nd column data
                Log.d("workflow", cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return list;
    }

    public List<String> getroutelist() {
        List<String> list = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + RouteMaster.RoutesT.TABLE_NAME
                + " ORDER BY " +
                RouteMaster.RoutesT.COLUMN_NAME_IS_DEFAULT
                + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return list;
    }


    public int deleteRoute(String routeid) {
        Log.d("workflow", "DB delete route method Called");

        SQLiteDatabase db = getReadableDatabase();
        String selection = RouteMaster.RoutesT.COLUMN_NAME_ROUTE_ID + " = ? ";
        String[] selectionArgs = {routeid};


        int status = db.delete(RouteMaster.RoutesT.TABLE_NAME,   //table name
                selection,                         //where clause
                selectionArgs                       //selection clause
        );
        return status;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public int updateRoute(String routeid, String startloc, String endloc, float distance, String isdefault) { //define the attributes and parameters to be sent

        Log.d("workflow", "DB update route method Called");
        //  update route set is_default=0 where is_default=1
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        String timeup = getTimeStamp();

        values.put(RouteMaster.RoutesT.COLUMN_NAME_START_LOCATION, startloc);
        values.put(RouteMaster.RoutesT.COLUMN_NAME_END_LOCATION, endloc);
        values.put(RouteMaster.RoutesT.COLUMN_NAME_DISTANCE, distance);
        values.put(RouteMaster.RoutesT.COLUMN_NAME_MODIFIED_DATE, timeup);
        values.put(RouteMaster.RoutesT.COLUMN_NAME_IS_DEFAULT, isdefault);

        String selection = RouteMaster.RoutesT.COLUMN_NAME_ROUTE_ID + " = ? ";
        String[] selectionArgs = {routeid};

        int count = db.update(RouteMaster.RoutesT.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    public Cursor readAllRoutes() {
        Log.d("workflow", "DB read All Routes method Called");


        String query = "SELECT "
                + RouteMaster.RoutesT.TABLE_NAME +
                ".*, COUNT" +
                "("
                + CustomerMaster.CustomerT.TABLE_NAME +
                "."
                + CustomerMaster.CustomerT.COLUMN_NAME_CX_route +
                ") AS NumberOfRoutes From "
                + RouteMaster.RoutesT.TABLE_NAME +
                " LEFT JOIN "
                + CustomerMaster.CustomerT.TABLE_NAME +
                " ON "
                + CustomerMaster.CustomerT.TABLE_NAME +
                "."
                + CustomerMaster.CustomerT.COLUMN_NAME_CX_route +
                "="
                + RouteMaster.RoutesT.TABLE_NAME +
                "."
                + RouteMaster.RoutesT.COLUMN_NAME_ROUTE_ID +
                " GROUP BY "
                + RouteMaster.RoutesT.TABLE_NAME +
                "."
                + RouteMaster.RoutesT.COLUMN_NAME_ROUTE_ID
                + " ORDER BY " +
                RouteMaster.RoutesT.COLUMN_NAME_IS_DEFAULT
                + " DESC";

        Log.d("workflow", query);

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);

        }
        return cursor;
    }

    public Cursor topRoute() {
        String query = "SELECT " + RouteMaster.RoutesT.COLUMN_NAME_ROUTE_ID + ", "
                + RouteMaster.RoutesT.COLUMN_NAME_START_LOCATION + ", "
                + RouteMaster.RoutesT.COLUMN_NAME_END_LOCATION + ", "
                + RouteMaster.RoutesT.COLUMN_NAME_DISTANCE + ", "
                + " COUNT( " + CustomerMaster.CustomerT.COLUMN_NAME_CX_route
                + " ) AS nmb FROM " + RouteMaster.RoutesT.TABLE_NAME
                + " LEFT JOIN " + CustomerMaster.CustomerT.TABLE_NAME
                + " ON " + CustomerMaster.CustomerT.COLUMN_NAME_CX_route
                + " = " + RouteMaster.RoutesT.COLUMN_NAME_ROUTE_ID
                + " GROUP BY " + RouteMaster.RoutesT.COLUMN_NAME_ROUTE_ID
                + " ORDER BY nmb DESC LIMIT " + 1;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        Log.d("workflow", "Top Route: " + String.valueOf(cursor));
        return cursor;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public int update_def_route() {
        Log.d("workflow", "DB update_def_route method Called");

        String timeupdef = getTimeStamp();
        SQLiteDatabase db = getReadableDatabase();

        ContentValues values1 = new ContentValues();
        values1.put(RouteMaster.RoutesT.COLUMN_NAME_IS_DEFAULT, 0);
        values1.put(RouteMaster.RoutesT.COLUMN_NAME_MODIFIED_DATE, timeupdef);
        String selection1 = RouteMaster.RoutesT.COLUMN_NAME_IS_DEFAULT + " = '1' ";

        int inq1 = db.update(RouteMaster.RoutesT.TABLE_NAME,
                values1,
                selection1,
                null);
        return inq1;

    }


    public int update_def_route_on_create() {
        Log.d("workflow", "DB update_def_route_on_create method Called");

        SQLiteDatabase db = getReadableDatabase();

        ContentValues values1 = new ContentValues();
        values1.put(RouteMaster.RoutesT.COLUMN_NAME_IS_DEFAULT, 0);
        String selection1 = RouteMaster.RoutesT.COLUMN_NAME_IS_DEFAULT + " = '1' ";


        int inq1 = db.update(RouteMaster.RoutesT.TABLE_NAME,
                values1,
                selection1,
                null);
        return inq1;

    }

    //add an Item
    @RequiresApi(api = Build.VERSION_CODES.O)
    public long addItem(String itemname, String itembrand, int itemcount, double buyprice, double sellprice, String itemdescription) //enter all the parameter to be added to DB
    {
        Log.d("workflow", "DB addItems method Called");

        String timeadd = getTimeStamp();
        Log.d("workflow", "DB gettimpstamp method Called");

        SQLiteDatabase db = getWritableDatabase();// get the data repository in writable mode

        ContentValues values = new ContentValues();  //create a new map of values , where column names the key

        values.put(ItemMaster.ItemsT.COLUMN_ItemName, itemname);
        values.put(ItemMaster.ItemsT.COLUMN_ItemBrand, itembrand);
        values.put(ItemMaster.ItemsT.COLUMN_ItemCount, itemcount);
        values.put(ItemMaster.ItemsT.COLUMN_ItemBuyPrice, buyprice);
        values.put(ItemMaster.ItemsT.COLUMN_ItemSellPrice, sellprice);
        values.put(ItemMaster.ItemsT.COLUMN_ItemDescription, itemdescription);



        long newRowID = db.insert(ItemMaster.ItemsT.TABLE_NAME, null, values); //Insert a new row and returning the primary
        //key values of the new row

        Log.d("workflow", "DB addItem method Called finished");

        return newRowID;
    }


    public Cursor readAllItems() {
        Log.d("workflow", "DB read All Items method Called");

        String query = "SELECT * FROM " + ItemMaster.ItemsT.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor readOneItem(String itemCode) {
        Log.d("workflow", "readOneItem initiated");
        String[] selectionArgs = {itemCode};
        String query = "SELECT * FROM " + ItemMaster.ItemsT.TABLE_NAME + " WHERE " + ItemMaster.ItemsT.COLUMN_ItemCode + " = ? ";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, selectionArgs);
        Log.d("workflow", String.valueOf(cursor));
        return cursor;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public int updateItem(String id, String itemname, String itembrand, int itemcount, double buyprice, double sellprice, String description) { //define the attributes and parameters to be sent

        Log.d("workflow", "DB update item method Called");
        //  update route set is_default=0 where is_default=1
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        String timeup = getTimeStamp();

        values.put(ItemMaster.ItemsT.COLUMN_ItemName, itemname);
        values.put(ItemMaster.ItemsT.COLUMN_ItemBrand, itembrand);
        values.put(ItemMaster.ItemsT.COLUMN_ItemCount, itemcount);
        values.put(ItemMaster.ItemsT.COLUMN_ItemBuyPrice, buyprice);
        values.put(ItemMaster.ItemsT.COLUMN_ItemSellPrice, sellprice);
        values.put(ItemMaster.ItemsT.COLUMN_ItemDescription, description);

        String selection = ItemMaster.ItemsT.COLUMN_ItemCode + " = ? ";
        String[] selectionArgs = {id};

        int count = db.update(ItemMaster.ItemsT.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return count;
    }

    public void deleteItem(String itemcode) {
        Log.d("workflow", "DB delete item method Called");

        SQLiteDatabase db = getWritableDatabase();
        String selection = ItemMaster.ItemsT.COLUMN_ItemCode + " = ? ";
        String[] selectionArgs = {itemcode};
        db.delete(ItemMaster.ItemsT.TABLE_NAME,   //table name
                selection,                         //where clause
                selectionArgs                       //selection clause
        );

    }

    public Cursor topItem() {
        String query = "SELECT " + ItemMaster.ItemsT.COLUMN_ItemCode + ", "
                + ItemMaster.ItemsT.COLUMN_ItemName + ", "
                + ItemMaster.ItemsT.COLUMN_ItemBrand + ", "
                + ItemMaster.ItemsT.COLUMN_ItemDescription + ", "
                + " SUM( " + SalesItemsMaster.SalesItemsT.COLUMN_NAME_QTY
                + " ) AS itemQty FROM " + ItemMaster.ItemsT.TABLE_NAME
                + " LEFT JOIN " + SalesItemsMaster.SalesItemsT.TABLE_NAME
                + " ON " + ItemMaster.ItemsT.COLUMN_ItemCode
                + " = " + SalesItemsMaster.SalesItemsT.COLUMN_NAME_ITEM_ID
                + " GROUP BY " + SalesItemsMaster.SalesItemsT.COLUMN_NAME_ITEM_ID
                + " ORDER BY itemQty DESC LIMIT " + 1;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        Log.d("workflow", "Top Item: " + String.valueOf(cursor));
        return cursor;
    }

    //Update Customer in the DB
    @RequiresApi(api = Build.VERSION_CODES.O)
    public long updateCustomer(String customerID, String customerName, String storeName, int mobile, String streetAddress, String city, String profilePicUri, String storePicUri, String routeID) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        String currentTime = getTimeStamp();

        contentValues.put(CustomerMaster.CustomerT.COLUMN_NAME_CUSTOMER_NAME, customerName);
        contentValues.put(CustomerMaster.CustomerT.COLUMN_NAME_STORE_NAME, storeName);
        contentValues.put(CustomerMaster.CustomerT.COLUMN_NAME_MOBILE, mobile);
        contentValues.put(CustomerMaster.CustomerT.COLUMN_NAME_STREET_ADDRESS, streetAddress);
        contentValues.put(CustomerMaster.CustomerT.COLUMN_NAME_CITY, city);
        contentValues.put(CustomerMaster.CustomerT.COLUMN_NAME_MODIFIED_DATE, currentTime);
        contentValues.put(CustomerMaster.CustomerT.COLUMN_NAME_PP_URL, profilePicUri);
        contentValues.put(CustomerMaster.CustomerT.COLUMN_NAME_SP_URL, storePicUri);
        contentValues.put(CustomerMaster.CustomerT.COLUMN_NAME_CX_route, routeID);

        String selection = CustomerMaster.CustomerT.COLUMN_NAME_CUSTOMER_ID + " = ? ";
        String[] selectionArgs = {customerID};

        return db.update(CustomerMaster.CustomerT.TABLE_NAME,
                contentValues,
                selection,
                selectionArgs);
    }

    //Insert Customer to the DB
    @RequiresApi(api = Build.VERSION_CODES.O)
    public long insertCustomer(String cusName, String storeName, int mobile, String streetAddress, String city, String profileUri, String storeUri, String cxroute) {

        //cxroute

        Log.d("workflow", "DB insertCustomer method called");
        String currentTime = getTimeStamp();
        Log.d("workflow", "DB getTimeStamp method Called");

        SQLiteDatabase db = getWritableDatabase();// get the data repository in writable mode
        ContentValues values = new ContentValues();  //create a new map of values , where column names the key

        values.put(CustomerMaster.CustomerT.COLUMN_NAME_CUSTOMER_NAME, cusName);
        values.put(CustomerMaster.CustomerT.COLUMN_NAME_STORE_NAME, storeName);
        values.put(CustomerMaster.CustomerT.COLUMN_NAME_MOBILE, mobile);
        values.put(CustomerMaster.CustomerT.COLUMN_NAME_STREET_ADDRESS, streetAddress);
        values.put(CustomerMaster.CustomerT.COLUMN_NAME_CITY, city);
        values.put(CustomerMaster.CustomerT.COLUMN_NAME_CREATED_DATE, currentTime);
        values.put(CustomerMaster.CustomerT.COLUMN_NAME_PP_URL, profileUri);
        values.put(CustomerMaster.CustomerT.COLUMN_NAME_SP_URL, storeUri);
        values.put(CustomerMaster.CustomerT.COLUMN_NAME_CX_route, cxroute);

        long newRowID = db.insert(CustomerMaster.CustomerT.TABLE_NAME, null, values); //Insert a new row and returning the primary key values of the new row
        Log.d("workflow", "DB insertCustomer method Called finished");

        return newRowID;
    }

    //View all Customers in the DB
    public Cursor readAllCustomers() {
        String[] columns = {CustomerMaster.CustomerT.COLUMN_NAME_CUSTOMER_ID,
                CustomerMaster.CustomerT.COLUMN_NAME_CUSTOMER_NAME,
                CustomerMaster.CustomerT.COLUMN_NAME_STORE_NAME,
                CustomerMaster.CustomerT.COLUMN_NAME_STREET_ADDRESS,
                CustomerMaster.CustomerT.COLUMN_NAME_CITY,
                CustomerMaster.CustomerT.COLUMN_NAME_PP_URL,
                CustomerMaster.CustomerT.COLUMN_NAME_SP_URL};
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.query(CustomerMaster.CustomerT.TABLE_NAME, columns, null, null, null, null, null);
        }
        return cursor;
    }

    //Delete one customer in the DB
    public long deleteCustomer(String customerID) {
        SQLiteDatabase db = getReadableDatabase();
        String where = CustomerMaster.CustomerT.COLUMN_NAME_CUSTOMER_ID + " = ? ";
        String[] selectionArgs = {customerID};
        long result = db.delete(CustomerMaster.CustomerT.TABLE_NAME, where, selectionArgs);

        Log.d("workflow", String.valueOf(result));
        return result;
    }

    //View one customer in the DB
    public Cursor getExistingDataToUpdate(String customerID) {
        Log.d("workflow", "getExistingDataToUpdate initiated");
        Log.d("workflow", "uID " + customerID);
        String[] selectionArgs = {customerID};
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + CustomerMaster.CustomerT.COLUMN_NAME_CUSTOMER_NAME + ", "
                + CustomerMaster.CustomerT.COLUMN_NAME_STORE_NAME + ", "
                + CustomerMaster.CustomerT.COLUMN_NAME_MOBILE + ", "
                + CustomerMaster.CustomerT.COLUMN_NAME_STREET_ADDRESS + ", "
                + CustomerMaster.CustomerT.COLUMN_NAME_CITY + ", "
                + CustomerMaster.CustomerT.COLUMN_NAME_PP_URL + ", "
                + CustomerMaster.CustomerT.COLUMN_NAME_SP_URL + ", "
                + CustomerMaster.CustomerT.COLUMN_NAME_CX_route + ", "
                + RouteMaster.RoutesT.COLUMN_NAME_START_LOCATION + ", "
                + RouteMaster.RoutesT.COLUMN_NAME_END_LOCATION
                        + " FROM " + CustomerMaster.CustomerT.TABLE_NAME
                        + " LEFT JOIN " + RouteMaster.RoutesT.TABLE_NAME
                        + " ON " + CustomerMaster.CustomerT.COLUMN_NAME_CX_route
                        + " = " + RouteMaster.RoutesT.COLUMN_NAME_ROUTE_ID
                        + " WHERE " + CustomerMaster.CustomerT.COLUMN_NAME_CUSTOMER_ID + " = ?";
        Cursor cursor1 = db.rawQuery(query, selectionArgs);
        Log.d("workflow", String.valueOf(cursor1.toString()));
        return cursor1;
    }

    //View all details of one customer in the DB
    public Cursor readOneCustomer(String customerID) {
        Log.d("workflow", "readOneCustomer initiated");
        String[] selectionArgs = {customerID};
        String query = "SELECT " + CustomerMaster.CustomerT.COLUMN_NAME_CUSTOMER_ID + ", "
                + CustomerMaster.CustomerT.COLUMN_NAME_CUSTOMER_NAME + ", "
                + CustomerMaster.CustomerT.COLUMN_NAME_STORE_NAME + ", "
                + CustomerMaster.CustomerT.COLUMN_NAME_MOBILE + ", "
                + CustomerMaster.CustomerT.COLUMN_NAME_STREET_ADDRESS + ", "
                + CustomerMaster.CustomerT.COLUMN_NAME_CITY + ", "
                + CustomerMaster.CustomerT.TABLE_NAME + "." + CustomerMaster.CustomerT.COLUMN_NAME_CREATED_DATE + ", "
                + CustomerMaster.CustomerT.TABLE_NAME + "." +  CustomerMaster.CustomerT.COLUMN_NAME_MODIFIED_DATE + ", "
                + CustomerMaster.CustomerT.COLUMN_NAME_PP_URL + ", "
                + CustomerMaster.CustomerT.COLUMN_NAME_SP_URL + ", "
                + RouteMaster.RoutesT.COLUMN_NAME_START_LOCATION + ", "
                + RouteMaster.RoutesT.COLUMN_NAME_END_LOCATION
                        + " FROM " + CustomerMaster.CustomerT.TABLE_NAME
                        + " LEFT JOIN " + RouteMaster.RoutesT.TABLE_NAME
                        + " ON " + CustomerMaster.CustomerT.COLUMN_NAME_CX_route
                        + " = " + RouteMaster.RoutesT.COLUMN_NAME_ROUTE_ID
                        + " WHERE " + CustomerMaster.CustomerT.COLUMN_NAME_CUSTOMER_ID + " = ?";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, selectionArgs);
        Log.d("workflow", String.valueOf(cursor));
        return cursor;
    }

    //Get Top Customer
    public Cursor topCustomer() {
        String query = "SELECT " + CustomerMaster.CustomerT.COLUMN_NAME_CUSTOMER_ID + ", "
                + CustomerMaster.CustomerT.COLUMN_NAME_CUSTOMER_NAME + ", "
                + CustomerMaster.CustomerT.COLUMN_NAME_STORE_NAME + ", "
                + CustomerMaster.CustomerT.COLUMN_NAME_STREET_ADDRESS + ", "
                + CustomerMaster.CustomerT.COLUMN_NAME_CITY + ", "
                + CustomerMaster.CustomerT.COLUMN_NAME_PP_URL + ", "
                + CustomerMaster.CustomerT.COLUMN_NAME_SP_URL + ", "
                + " SUM( " + SalesItemsMaster.SalesItemsT.COLUMN_NAME_QTY + "*" + SalesItemsMaster.SalesItemsT.COLUMN_NAME_AMOUNT
                + " ) AS cash FROM " + CustomerMaster.CustomerT.TABLE_NAME
                + " LEFT JOIN " + SalesMaster.SalesT.TABLE_NAME
                + " ON " + CustomerMaster.CustomerT.TABLE_NAME + "." + CustomerMaster.CustomerT.COLUMN_NAME_CUSTOMER_ID
                + " = " + SalesMaster.SalesT.TABLE_NAME + "." + SalesMaster.SalesT.COLUMN_NAME_CUSTOMER_ID
                + " LEFT JOIN " + SalesItemsMaster.SalesItemsT.TABLE_NAME
                + " ON " + SalesMaster.SalesT.TABLE_NAME + "." + SalesMaster.SalesT.COLUMN_NAME_INVOICE_ID
                + " = " + SalesItemsMaster.SalesItemsT.TABLE_NAME + "." + SalesItemsMaster.SalesItemsT.COLUMN_NAME_INVOICE_ID
                + " GROUP BY " + CustomerMaster.CustomerT.COLUMN_NAME_CUSTOMER_ID
                + " ORDER BY cash DESC LIMIT " + 1;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        Log.d("workflow", "Top Customer: " + String.valueOf(cursor));
        return cursor;
    }

    //Get all customers loaded into spinner in sales order page
    public List<String> getAllCustomers(){
        List<String> customerslist = new ArrayList<String>();
        String selectQuery = "SELECT CUSTOMER_ID FROM " + CustomerMaster.CustomerT.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                customerslist.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return customerslist;
    }

    //Get all items loaded into spinner in sales order page
    public List<Product> getAllProducts(String search){
        List<Product> productlist = new ArrayList<Product>();
        String selectQuery = "SELECT ItemCode,ItemName,ItemSellPrice FROM " + ItemMaster.ItemsT.TABLE_NAME + " WHERE ItemName LIKE '%"+ search +"%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Product pro = new Product();
                pro.setItemCode(cursor.getInt(0));
                pro.setItemName(cursor.getString(1));
                pro.setPrice(cursor.getFloat(2));
                productlist.add(pro);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return productlist;
    }

    // Add sales order to DB
    @RequiresApi(api = Build.VERSION_CODES.O)
    public long addSalesOrder(Integer cusid, String date, String invamt, String bal, String isurgent)
    {
        Log.d("workflow", "DB addSalesOrder method called");

        String currentTime = getTimeStamp();
        Log.d("workflow", "DB gettimpstamp method called");

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(SalesMaster.SalesT.COLUMN_NAME_INVOICE_ID, inv_id);
        values.put(SalesMaster.SalesT.COLUMN_NAME_CUSTOMER_ID, cusid);
        values.put(SalesMaster.SalesT.COLUMN_NAME_DELIVERY_DATE, date);
        values.put(SalesMaster.SalesT.COLUMN_NAME_INVOICE_AMOUNT, invamt);
        values.put(SalesMaster.SalesT.COLUMN_NAME_BALANCE, bal);
        values.put(SalesMaster.SalesT.COLUMN_NAME_CREATED_DATE, currentTime);
        values.put(SalesMaster.SalesT.COLUMN_NAME_IS_URGENT, isurgent);

        //Insert new row and return the primary key value
        long newSalesRowID = db.insert(SalesMaster.SalesT.TABLE_NAME, null, values);

        Log.d("workflow", "DB addSalesOrder method call finished");

        return newSalesRowID;
    }

    public int deleteSalesOrder(String invid) {
        Log.d("workflow", "DB deleteSalesOrder method called");

        SQLiteDatabase db = getReadableDatabase();
        String selection = SalesMaster.SalesT.COLUMN_NAME_INVOICE_ID + " = ? ";
        String[] selectionArgs = {invid};

        int status=db.delete(SalesMaster.SalesT.TABLE_NAME,
                selection,
                selectionArgs
        );
        return status;
    }

    public Cursor getAllSalesOrders() {
        Log.d("workflow", "DB getAllSalesOrders method called");

        String query = "SELECT * FROM " + SalesMaster.SalesT.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor readAllSalesOrders() {
        Log.d("workflow", "DB readAllSalesOrders method called");


        String query = "SELECT "
                + SalesMaster.SalesT.TABLE_NAME+
                ".* From "
                +SalesMaster.SalesT.TABLE_NAME+
                " LEFT JOIN "
                +CustomerMaster.CustomerT.TABLE_NAME+
                " ON "
                +CustomerMaster.CustomerT.TABLE_NAME+
                "."
                +CustomerMaster.CustomerT.COLUMN_NAME_CUSTOMER_ID+
                "="
                +SalesMaster.SalesT.TABLE_NAME+
                "."
                +SalesMaster.SalesT.COLUMN_NAME_CUSTOMER_ID;

        Log.d("workflow",query);

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);

        }
        return cursor;
    }


    //Get all invoices loaded into spinner in payment page
    public List<String> getAllInvoices(){
        List<String> invoicelist = new ArrayList<String>();
        String selectQuery = "SELECT inv_id FROM " + SalesMaster.SalesT.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                invoicelist.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return invoicelist;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public long addPayment(String txtinv, float txtpay, String txtdate, float txtnewbal)
    {
        Log.d("workflow", "DB addPayment method called");

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PaymentMaster.PaymentT.COLUMN_NAME_INVOICE_ID, txtinv);
        values.put(PaymentMaster.PaymentT.COLUMN_NAME_PAYMENT, txtpay);
        values.put(PaymentMaster.PaymentT.COLUMN_NAME_PAYMENT_DATE, txtdate);
        //values.put(SalesMaster.SalesT.COLUMN_NAME_BALANCE, txtnewbal);

        long newRowID = db.insert(PaymentMaster.PaymentT.TABLE_NAME, null, values); //Insert a new row and returning the primary
        //key values of the new row

        Log.d("workflow", "DB addPayment method call finished");

        updateSalesBalance(txtinv, txtnewbal);
        return newRowID;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int updateSalesBalance(String invid, Float bal) {

        Log.d("workflow", "DB updateSalesBalance method called");

        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(SalesMaster.SalesT.COLUMN_NAME_BALANCE, bal);

        String selection = SalesMaster.SalesT.COLUMN_NAME_INVOICE_ID + " = ? ";
        String[] selectionArgs = {invid};

        int count = db.update(SalesMaster.SalesT.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

}
