package com.example.wedless.myintentservice.fragment;


import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
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
import com.example.wedless.myintentservice.service.MyService;
import com.example.wedless.myintentservice.util.MyLog;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment implements View.OnClickListener {
    private EditText et_num;
    private EditText et_name;
    private EditText et_price;
    private Button btn_add;
    private Intent intent;
    private MyServiceConnection conn;
    private MyService.MyBinder myBinder;
    private String ls;
    private FragmentManager fm;
    private FragmentTransaction trans;
    public AddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_add, null);
        et_num = view.findViewById(R.id.et_num);
        et_name = view.findViewById(R.id.et_name);
        et_price = view.findViewById(R.id.et_price);
        btn_add = view.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                String num = et_num.getText().toString();
                String name = et_name.getText().toString();
                String price = et_price.getText().toString();
                intent = new Intent(getActivity(), MyService.class);
                intent.putExtra("num", num);
                intent.putExtra("name", name);
                intent.putExtra("price", price);
                intent.setAction(MyService.ACTION_INSERT);
                getActivity().startService(intent);
                conn = new MyServiceConnection();
                getActivity().bindService(intent, conn, Context.BIND_AUTO_CREATE);
                break;
        }
    }

    public class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            myBinder = (MyService.MyBinder) iBinder;

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }

    public static final String ACTION_RECEVIER_INTENT_SERVICE_INSERT = "com.simple.action.intentfilter.finish.recevier.insert";
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        receiver = new MyReceiver();
        filter = new IntentFilter(ACTION_RECEVIER_INTENT_SERVICE_INSERT);
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
                Toast.makeText(getActivity(), "新增成功!", Toast.LENGTH_SHORT).show();
                fm=getFragmentManager();
                trans=fm.beginTransaction();
                trans.replace(R.id.main_id,new ListFragment());
                trans.commit();
            }else{
                Toast.makeText(getActivity(), "新增失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
