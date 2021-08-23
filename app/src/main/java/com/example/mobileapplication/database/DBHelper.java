package com.example.mobileapplication.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserInfo.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    //change the DB version when upgrading the DB


    @Override
    public void onCreate(SQLiteDatabase db) {        //creating the table
        Log.d("workflow","DB onCreate method Called");
        String SQL_CREATE_ENTRIES =
                "CREATE TABLE "
                        + RouteMaster.RoutesT.TABLE_NAME +
                        " ("
                        + RouteMaster.RoutesT.COLUMN_NAME_ROUTE_ID  +
                        " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + RouteMaster.RoutesT.COLUMN_NAME_START_LOCATION +
                        " TEXT, "
                        +  RouteMaster.RoutesT.COLUMN_NAME_END_LOCATION +
                        " TEXT, "
                        +  RouteMaster.RoutesT.COLUMN_NAME_DISTANCE +
                        " REAL, "
                        +  RouteMaster.RoutesT.COLUMN_NAME_CREATED_DATE +
                        " TEXT, "
                        +  RouteMaster.RoutesT.COLUMN_NAME_MODIFIED_DATE +
                        " TEXT, "
                        +  RouteMaster.RoutesT.COLUMN_NAME_IS_DEFAULT +
                        " INTEGER"+")"
                ;


        String SQL_CREATE_ITEMS =
                "CREATE TABLE "
                        + ItemMaster.ItemsT.TABLE_NAME +
                        " ("
                        + ItemMaster.ItemsT.COLUMN_ItemCode  +
                        " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + ItemMaster.ItemsT.COLUMN_ItemName +
                        " TEXT, "
                        +  ItemMaster.ItemsT.COLUMN_ItemBrand +
                        " TEXT, "
                        +  ItemMaster.ItemsT.COLUMN_ItemCount +
                        " INTEGER, "
                        +  ItemMaster.ItemsT.COLUMN_ItemBuyPrice +
                        " REAL, "
                        +  ItemMaster.ItemsT.COLUMN_ItemSellPrice +
                        " REAL, "
                        +  ItemMaster.ItemsT.COLUMN_ItemDescription +
                        " TEXT"+")"
                ;


        //defining the sql query
        db.execSQL(SQL_CREATE_ENTRIES);//Execute the table creation
        Log.d("DBcreation","Db created succesfully");

        db.execSQL(SQL_CREATE_ITEMS);


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("workflow","DB Onupgrade method Called");
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String gettimestamop(){
        Log.d("workflow","DB gettimestamop method Called");

        LocalDateTime myDateobj=LocalDateTime.now();
        DateTimeFormatter myformatobj=DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String timeStamp= myDateobj.format(myformatobj) ;
        return timeStamp;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public long addRoutes(String startloc, String endloc, float distance,String isdefault) //enter all the parameter to be added to DB
    {
        Log.d("workflow","DB addRoutes method Called");

        String timeadd=gettimestamop();
        Log.d("workflow","DB gettimpstamp method Called");

        SQLiteDatabase db = getWritableDatabase();// get the data repository in writable mode

        ContentValues values = new ContentValues();  //create a new map of values , where column names the key
        values.put(RouteMaster.RoutesT.COLUMN_NAME_START_LOCATION,startloc);
        values.put(RouteMaster.RoutesT.COLUMN_NAME_END_LOCATION,endloc);
        values.put(RouteMaster.RoutesT.COLUMN_NAME_DISTANCE,distance);
        values.put(RouteMaster.RoutesT.COLUMN_NAME_CREATED_DATE,timeadd);
        values.put(RouteMaster.RoutesT.COLUMN_NAME_MODIFIED_DATE,"N/A");
        values.put(RouteMaster.RoutesT.COLUMN_NAME_IS_DEFAULT,isdefault);

        long newRowID = db.insert(RouteMaster.RoutesT.TABLE_NAME, null, values); //Insert a new row and returning the primary
        //key values of the new row

        Log.d("workflow","DB addRoutes method Called finished");

        return newRowID;
    }




    public void deleteRoute(String routeid){
        Log.d("workflow","DB delete route method Called");

        SQLiteDatabase db=getReadableDatabase();
        String selection= RouteMaster.RoutesT.COLUMN_NAME_ROUTE_ID+" = ? ";
        String [] selectionArgs={routeid};
        db.delete(RouteMaster.RoutesT.TABLE_NAME,   //table name
                selection,                         //where clause
                selectionArgs                       //selection clause
        );
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public int updateRoute(String routeid, String startloc, String endloc, float distance,String isdefault ){ //define the attributes and parameters to be sent

        Log.d("workflow","DB update route method Called");
      //  update route set is_default=0 where is_default=1
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values=new ContentValues();
        String timeup=gettimestamop();

        values.put(RouteMaster.RoutesT.COLUMN_NAME_START_LOCATION,startloc);
        values.put(RouteMaster.RoutesT.COLUMN_NAME_END_LOCATION,endloc);
        values.put(RouteMaster.RoutesT.COLUMN_NAME_DISTANCE,distance);
        values.put(RouteMaster.RoutesT.COLUMN_NAME_MODIFIED_DATE,timeup);
        values.put(RouteMaster.RoutesT.COLUMN_NAME_IS_DEFAULT,isdefault);

        String selection= RouteMaster.RoutesT.COLUMN_NAME_ROUTE_ID+" = ? ";
        String[] selectionArgs ={routeid};

        int count= db.update(RouteMaster.RoutesT.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    public Cursor readAllRoutes(){
        Log.d("workflow","DB read All Routes method Called");

       String query ="SELECT * FROM "+ RouteMaster.RoutesT.TABLE_NAME;
       SQLiteDatabase db= this.getReadableDatabase();


       Cursor cursor=null;
       if(db!=null){
           cursor=db.rawQuery(query,null);
       }
       return cursor;
    }


    //@piyoshila use this method to get routes details with the default flag
    //If a particular route is default is default flag will be 1
    public Cursor readRouteData(){

        Log.d("workflow","DB readRouteData method Called");
        String query =
                "SELECT "
                        + RouteMaster.RoutesT.COLUMN_NAME_ROUTE_ID +
                        " , "
                        + RouteMaster.RoutesT.COLUMN_NAME_START_LOCATION +
                        " , "
                        + RouteMaster.RoutesT.COLUMN_NAME_END_LOCATION +
                        " , "
                        + RouteMaster.RoutesT.COLUMN_NAME_IS_DEFAULT +
                        " FROM "
                        + RouteMaster.RoutesT.TABLE_NAME;

        SQLiteDatabase db= this.getReadableDatabase();

        Cursor cursor=null;
        if(db!=null){
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int update_def_route(){
        Log.d("workflow","DB update_def_route method Called");

        String timeupdef=gettimestamop();
        SQLiteDatabase db = getReadableDatabase();

        ContentValues values1=new ContentValues();
        values1.put(RouteMaster.RoutesT.COLUMN_NAME_IS_DEFAULT,0);
        values1.put(RouteMaster.RoutesT.COLUMN_NAME_MODIFIED_DATE,timeupdef);
        String selection1= RouteMaster.RoutesT.COLUMN_NAME_IS_DEFAULT+" = '1' ";

        int inq1=db.update(RouteMaster.RoutesT.TABLE_NAME,
                values1,
                selection1,
                null);
        return inq1;

    }


    public int update_def_route_on_create(){
        Log.d("workflow","DB update_def_route_on_create method Called");

        SQLiteDatabase db = getReadableDatabase();

        ContentValues values1=new ContentValues();
        values1.put(RouteMaster.RoutesT.COLUMN_NAME_IS_DEFAULT,0);
        String selection1= RouteMaster.RoutesT.COLUMN_NAME_IS_DEFAULT+" = '1' ";


        int inq1=db.update(RouteMaster.RoutesT.TABLE_NAME,
                values1,
                selection1,
                null);
        return inq1;

    }

}

