package com.example.wedless.myintentservice.fragment;


import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wedless.myintentservice.R;
import com.example.wedless.myintentservice.bean.Goods;
import com.example.wedless.myintentservice.service.MyService;
import com.example.wedless.myintentservice.util.MyLog;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment implements View.OnClickListener {
    private EditText et_num;
    private EditText et_name;
    private EditText et_price;
    private Button btn_update;
    private Button btn_delete;
    private Intent intent;
    private Handler handler;
    private Goods goods=new Goods();
    private MyServiceConnection conn;
    private String ls="";
    private int id;
    private FragmentManager fm;
    private FragmentTransaction trans;
    private MyService.MyBinder myBinder;
    public static MessageFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt("ids",id);
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public MessageFragment() {
        // Required empty public constructor
    }
    public class MyServiceConnection implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            myBinder= (MyService.MyBinder) iBinder;
            Goods g=myBinder.getGoods();
            goods=g;
            et_num.setText(goods.getNum().toString());
            et_name.setText(goods.getName().toString());
            et_price.setText(goods.getPrice().toString());
            ls=myBinder.getLs();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        view=inflater.inflate(R.layout.fragment_message,null);
        et_num=view.findViewById(R.id.et_num);
        et_name=view.findViewById(R.id.et_name);
        et_price=view.findViewById(R.id.et_price);
        btn_delete=view.findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(this);
        btn_update=view.findViewById(R.id.btn_update);
        btn_update.setOnClickListener(this);
        Bundle b = this.getArguments();
        id=b.getInt("ids");
        intent = new Intent(getActivity(), MyService.class);
        intent.putExtra("id",id);
        intent.setAction(MyService.ACTION_QUERYBYID);
        getActivity().startService(intent);
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                conn=new MyServiceConnection();
                getActivity().bindService(intent,conn,Context.BIND_AUTO_CREATE);
            }
        },1000);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_delete:
               /* Bundle b=new Bundle();
                id=b.getInt("goods_id");*/
                intent = new Intent(getActivity(), MyService.class);
                intent.putExtra("ids",id);
                intent.setAction(MyService.ACTION_DELETE);
                getActivity().startService(intent);
                conn = new MyServiceConnection();
                getActivity().bindService(intent, conn, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btn_update:
                intent=new Intent(getActivity(),MyService.class);
                intent.putExtra("ids",id);
                intent.putExtra("num",et_num.getText().toString());
                intent.putExtra("name",et_name.getText().toString());
                intent.putExtra("price",et_price.getText().toString());
                intent.setAction(MyService.ACTION_UPDATE);
                getActivity().startService(intent);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                      intent =new Intent(getActivity(),MyService.class);
                      conn=new MyServiceConnection();
                      getActivity().bindService(intent,conn,Context.BIND_AUTO_CREATE);
                        if(ls.equals("ok")){
                            Toast.makeText(getActivity(), "修改成功!", Toast.LENGTH_SHORT).show();
                            fm=getFragmentManager();
                            trans=fm.beginTransaction();
                            trans.replace(R.id.main_id,new ListFragment());
                            trans.commit();
                        }else{
                            Toast.makeText(getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                },1000);
                break;
        }
    }
    public static final String ACTION_RECEVIER_INTENT_SERVICE_DELETE = "com.simple.action.intentfilter.finish.recevier.delete";
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        receiver = new MyReceiver();
        filter = new IntentFilter(ACTION_RECEVIER_INTENT_SERVICE_DELETE);
        this.getActivity().registerReceiver(receiver, filter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.getActivity().unregisterReceiver(receiver);
    }

    private MyReceiver receiver;
    private IntentFilter filter;

    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            MyLog.show("onReceiver");
            ls = intent.getStringExtra(MyService.KEY_RETURN_JSON);
            if(ls.equals("ok")){
                Toast.makeText(getActivity(), "删除成功!", Toast.LENGTH_SHORT).show();
                fm=getFragmentManager();
                trans=fm.beginTransaction();
                trans.replace(R.id.main_id,new ListFragment());
                trans.commit();
            }else{
                Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
