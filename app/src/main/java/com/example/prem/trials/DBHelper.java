package com.example.prem.trials;

import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    private final static String DATABASE_NAME="mydatabase";
    private final static int DATABASE_VERSION=1;
    private final static String DATABASE_TABLE="sample";
    private final static String NAME= "name";
    private final static String NUMBER="number";
    private final static String sqlquery="create table "+DATABASE_TABLE+"("+NAME+" varchar(20),"+NUMBER+" varchar(25));";
    DBHelper (Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context=context;

    }
    public  void onCreate(SQLiteDatabase db)
    {

        db.execSQL(sqlquery);
        Toast.makeText(context,"Inside on Create Method",Toast.LENGTH_SHORT).show();

    }
    public  void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {
        String query="drop table if exists "+DATABASE_TABLE+";";
        db.execSQL(query);
        onCreate(db);
        Toast.makeText(context,"Inside on Upgrade Method",Toast.LENGTH_SHORT).show();

    }


}