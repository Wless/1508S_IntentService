package com.example.wedless.myintentservice.fragment;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.wedless.myintentservice.R;
import com.example.wedless.myintentservice.bean.Goods;
import com.example.wedless.myintentservice.service.MyService;
import com.example.wedless.myintentservice.util.MyAdapter;
import com.example.wedless.myintentservice.util.MyLog;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements View.OnClickListener {
    private ListView listshow;
    private MyService.MyBinder myBinder;
    private MyServiceConnection conn;
    private Handler handler;
    private MyAdapter myAdapter;
    private Button btn_select;
    private Button btn_add;
    private FragmentManager fm;
    private FragmentTransaction trans;
    private ArrayList<Goods> lists=new ArrayList<>();
    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_select:
                Intent intent=new Intent(getActivity(), MyService.class);
                intent.setAction(MyService.ACTION_QUERY);
                getActivity().startService(intent);
                conn=new MyServiceConnection();
                getActivity().bindService(intent,conn, Context.BIND_AUTO_CREATE);
                handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        myAdapter=new MyAdapter(getContext(),R.layout.fragment_list,lists);
                        listshow.setAdapter(myAdapter);
                    }
                },1000);
                myAdapter=new MyAdapter(getContext(),R.layout.fragment_list,lists);
                listshow.setAdapter(myAdapter);
                break;
            case R.id.btn_add:
                fm=getFragmentManager();
                trans=fm.beginTransaction();
                trans.replace(R.id.main_id,new AddFragment());
                trans.commit();
                break;
        }
    }

    public class MyServiceConnection implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            myBinder= (MyService.MyBinder) iBinder;
            lists=myBinder.getList();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        view=inflater.inflate(R.layout.fragment_list,null);
        listshow=view.findViewById(R.id.list_show);
        btn_select=view.findViewById(R.id.btn_select);
        btn_select.setOnClickListener(this);
        btn_add=view.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        Intent intent=new Intent(getActivity(), MyService.class);
        intent.setAction(MyService.ACTION_QUERY);
        getActivity().startService(intent);
        conn=new MyServiceConnection();
        getActivity().bindService(intent,conn, Context.BIND_AUTO_CREATE);
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                myAdapter=new MyAdapter(getContext(),R.layout.fragment_list,lists);
                listshow.setAdapter(myAdapter);
            }
        },1000);
        initlist();
        return view;
    }

    private void initlist() {
        listshow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                fm=getFragmentManager();
                trans=fm.beginTransaction();
                trans.replace(R.id.main_id,MessageFragment.newInstance(lists.get(i).getId()));
                trans.commit();
            }
        });
    }

}
