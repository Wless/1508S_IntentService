package com.example.wedless.myintentservice.service;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.wedless.myintentservice.bean.Goods;
import com.example.wedless.myintentservice.bean.User;
import com.example.wedless.myintentservice.fragment.AddFragment;
import com.example.wedless.myintentservice.fragment.LoginFragment;
import com.example.wedless.myintentservice.fragment.MessageFragment;
import com.example.wedless.myintentservice.fragment.RegisterFragment;
import com.example.wedless.myintentservice.httpurlconnection.MyUrlConnection;
import com.example.wedless.myintentservice.provider.MyContentProvider;
import com.example.wedless.myintentservice.sqlopenhelper.MySqlOpenHelper;
import com.example.wedless.myintentservice.util.MyLog;

import java.util.ArrayList;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class MyService extends IntentService {
    private MySqlOpenHelper mySqliteOpenHalper;
    private SQLiteDatabase db;
    private MyBinder myBinder;
    private int id;
    private User dbuser=new User();
    private Goods goods;
    private ArrayList<Goods> lists=new ArrayList<>();
    private ArrayList<Goods> dblists=new ArrayList<>();
    private String ls="";
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_FOO = "com.example.wedless.myintentservice.service.action.FOO";
    public static final String ACTION_LOGIN = "com.example.wedless.myintentservice.service.action.BAZ";
    public static final String ACTION_INSERT = "com.example.wedless.myintentservice.service.action.Insert";
    public static final String ACTION_DELETE = "com.example.wedless.myintentservice.service.action.Delete";
    public static final String ACTION_BAZ = "com.example.wedless.myintentservice.service.action.LOGIN";
    public static final String ACTION_UPDATE = "com.example.wedless.myintentservice.service.action.UPDATE";
    public static final String ACTION_QUERY = "com.example.wedless.myintentservice.service.action.Query";
    public static final String ACTION_QUERYBYID = "com.example.wedless.myintentservice.service.action.QueryById";
    public static final String ACTION_REGISTER = "com.example.wedless.myintentservice.service.action.Register";

    // TODO: Rename parameters
    public static final String EXTRA_PARAM1 = "com.example.wedless.myintentservice.service.extra.PARAM1";
    public static final String EXTRA_PARAM2 = "com.example.wedless.myintentservice.service.extra.PARAM2";

    public MyService() {
        super("MyService");
    }
    public class MyBinder extends Binder{
        public String loginReturn(){
            String returnls=ls;
            return returnls;
        }
        public ArrayList<Goods> getList(){
            ArrayList<Goods> alist=new ArrayList<>();
            alist=lists;
            return alist;
        }
        public Goods getGoods(){
            Goods g=new Goods();
            g=goods;
            return g;
        }
        public String getLs(){
            return ls;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        myBinder=new MyBinder();
        return myBinder;
    }


    public static final String KEY_RETURN_JSON = "return";
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }else if(ACTION_LOGIN.equals(action)){
                dbuser.setAccount(intent.getStringExtra("account"));
                dbuser.setPassword(intent.getStringExtra("password"));
                this.ls= MyUrlConnection.login(dbuser);

                intent = new Intent(LoginFragment.ACTION_RECEVIER_INTENT_SERVICE);
                intent.putExtra(KEY_RETURN_JSON, this.ls);
                this.sendBroadcast( intent );
            }else if(ACTION_REGISTER.equals(action)){
                dbuser.setAccount(intent.getStringExtra("account"));
                dbuser.setPassword(intent.getStringExtra("password"));
                this.ls=MyUrlConnection.register(dbuser);
                intent = new Intent(RegisterFragment.ACTION_RECEVIER_INTENT_SERVICE_REGISTER);
                intent.putExtra(KEY_RETURN_JSON, this.ls);
                this.sendBroadcast( intent );
            }else if (ACTION_QUERY.equals(action)){
                this.lists=MyUrlConnection.query();
                MyLog.show(lists.get(0).getName());
                getContentResolver().delete(Uri.parse("content://com.simple.contact.provider.intent/goods"),null,null);
                for(Goods g:lists){
                    ContentValues values=new ContentValues();
                    values.put("id",g.getId());
                    values.put("num",g.getNum());
                    values.put("name",g.getName());
                    values.put("price",g.getPrice());
                    getContentResolver().insert(Uri.parse("content://com.simple.contact.provider.intent/goods"),values);
                }
                lists.clear();
                Cursor cursor=getContentResolver().query(Uri.parse("content://com.simple.contact.provider.intent/goods"),null,null,null,null);
                while(cursor.moveToNext()){
                    Goods g=new Goods();
                    g.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    g.setNum(cursor.getString(cursor.getColumnIndex("num")));
                    g.setName(cursor.getString(cursor.getColumnIndex("name")));
                    g.setPrice(cursor.getString(cursor.getColumnIndex("price")));
                    lists.add(g);
                }
            }else if(ACTION_INSERT.equals(action)){
                goods.setNum(intent.getStringExtra("num"));
                goods.setName(intent.getStringExtra("name"));
                goods.setPrice(intent.getStringExtra("price"));
                this.ls=MyUrlConnection.insert(goods);
                intent = new Intent(AddFragment.ACTION_RECEVIER_INTENT_SERVICE_INSERT);
                intent.putExtra(KEY_RETURN_JSON, this.ls);
                this.sendBroadcast( intent );
            }else if(ACTION_QUERYBYID.equals(action)){
                id=intent.getIntExtra("id",0);
                Cursor cursor=getContentResolver().query(Uri.parse("content://com.simple.contact.provider.intent/goods"),null,"id="+id,null,null);
                while(cursor.moveToNext()){
                    goods=new Goods();
                    goods.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    goods.setNum(cursor.getString(cursor.getColumnIndex("num")));
                    goods.setName(cursor.getString(cursor.getColumnIndex("name")));
                    goods.setPrice(cursor.getString(cursor.getColumnIndex("price")));
                }
            }else if(ACTION_DELETE.equals(action)){
                int ids=this.id;
                ls=MyUrlConnection.delete(ids);
                intent = new Intent(MessageFragment.ACTION_RECEVIER_INTENT_SERVICE_DELETE);
                intent.putExtra(KEY_RETURN_JSON, this.ls);
                this.sendBroadcast( intent );
            }else if(ACTION_UPDATE.equals(action)){
                int ids=this.id;
                MyLog.show("sda"+ids);
                String num=intent.getStringExtra("num");
                String name=intent.getStringExtra("name");
                String price=intent.getStringExtra("price");
                Goods g=new Goods();
                g.setId(ids);
                g.setNum(num);
                g.setName(name);
                g.setPrice(price);
                ls=MyUrlConnection.update(g);
            }

        }
    }
    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
