package com.example.mobileapplication.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserInfo.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
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
                        " TEXT"+")"
                ;



        //defining the sql query
        db.execSQL(SQL_CREATE_ENTRIES); //Execute the table creation
        Log.d("DBcreation","Db created succesfully");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addRoutes(String startloc, String endloc,float distance) //enter all the parameter to be added to DB
    {
        Log.d("DBcreation","Executiong data add");

        SQLiteDatabase db = getWritableDatabase();// get the data repository in writable mode

        ContentValues values = new ContentValues();  //create a new map of values , where column names the key
        values.put(RouteMaster.RoutesT.COLUMN_NAME_START_LOCATION,startloc);
        values.put(RouteMaster.RoutesT.COLUMN_NAME_END_LOCATION,endloc);
        values.put(RouteMaster.RoutesT.COLUMN_NAME_DISTANCE,distance);

        long newRowID = db.insert(RouteMaster.RoutesT.TABLE_NAME, null, values); //Insert a new row and returning the primary
        //key values of the new row

        Log.d("DBcreation","Executiong data add return:"+newRowID);

        return newRowID;
    }
    /*
    public List readAllInfo(String req) //can pass parameter if required readAllInfo(String req)

    {
        Log.d("DBcreation","Calling Read all info");

        SQLiteDatabase db = getReadableDatabase();// get the data repository to readable mode

        String[] projection = {UsersMaster.Users._ID, UsersMaster.Users.COLUMN_NAME_USERNAME, UsersMaster.Users.COLUMN_NAME_PASSWORD};
        //defining which columns of the DB will be used

        String sortOrder = UsersMaster.Users.COLUMN_NAME_USERNAME + " DESC";

        Cursor cursor = db.query(                 //precompile query need to pass parameters
                UsersMaster.Users.TABLE_NAME,   //table to query
                projection,                     //column to return
                null,                   ///column for where clause
                null,               //the values for where clause
                null,                   //dont group by rows
                null,                   //dont filter by row groups
                sortOrder                       //the sort order
        );

        List userNames = new ArrayList<>();
        List passwords = new ArrayList<>();

        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_USERNAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_PASSWORD));

            userNames.add(username);//adding the retrieved data to list
            userNames.add(password);
        }
        cursor.close();
        Log.i("DBcreation", "Read" + userNames);



        if(req=="user") {
            return userNames;
        }
        else if (req=="password"){
            return passwords;
        }
        else{
            return null;
        }


    }
    */
    public void deleteRoute(String routeid){
        SQLiteDatabase db=getReadableDatabase();
        String selection= RouteMaster.RoutesT.COLUMN_NAME_ROUTE_ID+" = ? ";
        String [] selectionArgs={routeid};
        db.delete(RouteMaster.RoutesT.TABLE_NAME,   //table name
                selection,                         //where clause
                selectionArgs                       //selection clause
        );
    }


    public int updateRoute(String routeid,String startloc, String endloc,float distance) { //define the attributes and parameters to be sent
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values=new ContentValues();

        values.put(RouteMaster.RoutesT.COLUMN_NAME_START_LOCATION,startloc);
        values.put(RouteMaster.RoutesT.COLUMN_NAME_END_LOCATION,endloc);
        values.put(RouteMaster.RoutesT.COLUMN_NAME_DISTANCE,distance);

        String selection= RouteMaster.RoutesT.COLUMN_NAME_ROUTE_ID+" = ? ";
        String[] selectionArgs ={routeid};

        int count= db.update(RouteMaster.RoutesT.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }



}












