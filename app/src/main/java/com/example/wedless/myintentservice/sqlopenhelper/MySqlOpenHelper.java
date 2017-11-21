package com.example.wedless.myintentservice.sqlopenhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Wedless on 2017/11/7.
 */

public class MySqlOpenHelper extends SQLiteOpenHelper{
    private String mName;
    private int mVersion;
    public static final String TABLE="goods";
    public static final String TABLE_LIST="SELECT * FROM "+TABLE+"";
    public static final String CREATE_TABLE="CREATE TABLE "+TABLE+"(id integer primary key autoincrement,num text,name text,price text)";
    public MySqlOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mName=name;
        mVersion=version;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
