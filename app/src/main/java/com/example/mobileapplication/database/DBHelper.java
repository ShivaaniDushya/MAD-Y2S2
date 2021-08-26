package com.example.mobileapplication.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserInfo.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    //change the DB version when upgrading the DB


    @Override
    public void onCreate(SQLiteDatabase db) {        //creating the table
        Log.d("workflow", "DB onCreate method Called");
        String SQL_CREATE_ROUTE =
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
                        " TEXT" + ")";


        //defining all the sql queries here
        db.execSQL(SQL_CREATE_ROUTE); //Execute the route table creation
        Log.d("workflow", "Route Db created successfully");
        db.execSQL(SQL_CREATE_CUSTOMER); //Execute the customer table creation
        Log.d("workflow", "Customer Db created successfully");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("workflow", "DB Onupgrade method Called");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getTimeStamp() {
        Log.d("workflow", "DB getTimeStamp method Called");

        LocalDateTime myDateobj = LocalDateTime.now();
        DateTimeFormatter myformatobj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        return myDateobj.format(myformatobj);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public long addRoutes(String startloc, String endloc, float distance, String isdefault) //enter all the parameter to be added to DB
    {
        Log.d("workflow", "DB addRoutes method Called");

        String timeadd = getTimeStamp();
        Log.d("workflow", "DB getTimeStamp method Called");

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


    public void deleteRoute(String routeid) {
        Log.d("workflow", "DB delete route method Called");

        SQLiteDatabase db = getReadableDatabase();
        String selection = RouteMaster.RoutesT.COLUMN_NAME_ROUTE_ID + " = ? ";
        String[] selectionArgs = {routeid};
        db.delete(RouteMaster.RoutesT.TABLE_NAME,   //table name
                selection,                         //where clause
                selectionArgs                       //selection clause
        );
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

        return db.update(RouteMaster.RoutesT.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public Cursor readAllRoutes() {
        Log.d("workflow", "DB read All Routes method Called");

        String query = "SELECT * FROM " + RouteMaster.RoutesT.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    //Update Customer in the DB
    @RequiresApi(api = Build.VERSION_CODES.O)
    public long updateCustomer(String customerID, String customerName, String storeName, int mobile, String streetAddress, String city, String profilePicUri, String storePicUri) {
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

        String selection = CustomerMaster.CustomerT.COLUMN_NAME_CUSTOMER_ID + " = ? ";
        String[] selectionArgs = {customerID};

        return db.update(CustomerMaster.CustomerT.TABLE_NAME,
                contentValues,
                selection,
                selectionArgs);
    }

    //Insert Customer to the DB
    @RequiresApi(api = Build.VERSION_CODES.O)
    public long insertCustomer(String cusName, String storeName, int mobile, String streetAddress, String city, String profileUri, String storeUri) {
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
        String[] columns = {CustomerMaster.CustomerT.COLUMN_NAME_CUSTOMER_NAME,
                CustomerMaster.CustomerT.COLUMN_NAME_STORE_NAME,
                CustomerMaster.CustomerT.COLUMN_NAME_MOBILE,
                CustomerMaster.CustomerT.COLUMN_NAME_STREET_ADDRESS,
                CustomerMaster.CustomerT.COLUMN_NAME_CITY,
                CustomerMaster.CustomerT.COLUMN_NAME_PP_URL,
                CustomerMaster.CustomerT.COLUMN_NAME_SP_URL};
        String where = CustomerMaster.CustomerT.COLUMN_NAME_CUSTOMER_ID + " = ? ";
        String[] selectionArgs = {customerID};
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(CustomerMaster.CustomerT.TABLE_NAME, columns, where, selectionArgs, null, null, null);
        Log.d("workflow", String.valueOf(cursor));
        return cursor;
    }

    //View all details of one customer in the DB
    public Cursor readOneCustomer(String customerID) {
        Log.d("workflow", "readOneCustomer initiated");
        String[] selectionArgs = {customerID};
        String query = "SELECT * FROM " + CustomerMaster.CustomerT.TABLE_NAME + " WHERE " + CustomerMaster.CustomerT.COLUMN_NAME_CUSTOMER_ID + " = ? ";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, selectionArgs);
        Log.d("workflow", String.valueOf(cursor));
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

        return db.update(RouteMaster.RoutesT.TABLE_NAME,
                values1,
                selection1,
                null);

    }


    public int update_def_route_on_create() {
        Log.d("workflow", "DB update_def_route_on_create method Called");

        SQLiteDatabase db = getReadableDatabase();

        ContentValues values1 = new ContentValues();
        values1.put(RouteMaster.RoutesT.COLUMN_NAME_IS_DEFAULT, 0);
        String selection1 = RouteMaster.RoutesT.COLUMN_NAME_IS_DEFAULT + " = '1' ";

        return db.update(RouteMaster.RoutesT.TABLE_NAME,
                values1,
                selection1,
                null);

    }

}
