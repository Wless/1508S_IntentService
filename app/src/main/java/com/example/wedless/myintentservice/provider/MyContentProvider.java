package com.example.wedless.myintentservice.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.wedless.myintentservice.sqlopenhelper.MySqlOpenHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MyContentProvider extends ContentProvider {
    private MySqlOpenHelper mySqliteOpenHalper;
    private SQLiteDatabase db;
    private InputStream is;
    private  Properties prop;
    private  String prefix;
    private  String authorities;
    private  String split;
    private  String path_contact;
    //private static UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);

    public String uri;

    //public static final int MATCHER_CONTACT=1;
    //static {
      //  uriMatcher.addURI(AUTHORITIES,PATH_CONTACT,MATCHER_CONTACT);
    //}
    public MyContentProvider() {

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return db.delete(MySqlOpenHelper.TABLE,null,null);
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }
   @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        db.insert(MySqlOpenHelper.TABLE,null,values);
        return null;
    }
    @Override
    public boolean onCreate() {
        initDataBase();
        AssetManager manager=getContext().getAssets();
        try {

            is=manager.open("appConfig.properties");
            prop=new Properties();
            prop.load(is);
            authorities=prop.getProperty("authorities");

            prefix=prop.getProperty("prefix");
            split=prop.getProperty("split");
            path_contact=prop.getProperty("path_contact");
            uri=prefix+authorities+split+path_contact;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void initDataBase() {
        mySqliteOpenHalper=new MySqlOpenHelper(this.getContext(),"db_goods",null,1);
        db=mySqliteOpenHalper.getWritableDatabase();
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
            Cursor cursor=null;
            cursor=db.query("goods",null,selection,null,null,null,null);
            return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
       return  db.update(MySqlOpenHelper.TABLE,values,selection,null);
    }
}
