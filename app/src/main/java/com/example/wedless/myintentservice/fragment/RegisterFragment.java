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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.wedless.myintentservice.R;
import com.example.wedless.myintentservice.bean.User;
import com.example.wedless.myintentservice.service.MyService;
import com.example.wedless.myintentservice.util.MyLog;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private EditText et_account;
    private EditText et_password;
    private User user;
    private Intent intent;
    private ImageButton ib_zc;
    private String ls;
    private MyService.MyBinder myBinder;
    private FragmentManager fm;
    private FragmentTransaction trans;
    private MyServiceConnection conn;
    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        view=inflater.inflate(R.layout.fragment_register,null);
        et_account=view.findViewById(R.id.et_account);
        et_password=view.findViewById(R.id.et_password);
        ib_zc=view.findViewById(R.id.ib_register);
        ib_zc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.ib_register:
                        String account=et_account.getText().toString();
                        String password=et_password.getText().toString();
                        intent=new Intent(getActivity(),MyService.class);
                        intent.putExtra("account",account);
                        intent.putExtra("password",password);
                        intent.setAction(MyService.ACTION_REGISTER);
                        getActivity().startService(intent);
                        conn=new MyServiceConnection();
                        getActivity().bindService(intent,conn,Context.BIND_AUTO_CREATE);
                        /*Intent intent=new Intent(getActivity(), MyService.class);
                        intent.setAction(MyService.ACTION_REGISTER);
                        intent.putExtra("account",et_account.getText().toString());
                        intent.putExtra("password",et_password.getText().toString());
                        getActivity().startService(intent);
                        conn=new MyServiceConnection();
                        getActivity().bindService(intent,conn,Context.BIND_AUTO_CREATE);*/
                        break;
                }
            }
        });
        return view;
    }

    public class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            myBinder= (MyService.MyBinder) iBinder;
           /* ls=myBinder.loginReturn();
            if(ls.equals("ok")){
                Toast.makeText(getActivity(), "注册成功!", Toast.LENGTH_SHORT).show();
                fm=getFragmentManager();
                trans=fm.beginTransaction();
                trans.replace(R.id.main_id,new LoginFragment());
                trans.commit();
            }else{
                Toast.makeText(getActivity(), "注册失败", Toast.LENGTH_SHORT).show();
            }*/
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }
/*
    public static final String ACTION_RECEVIER_INTENT_SERVICE_REGISTER = "com.simple.action.intentfilter.finish.recevier.register";
*/
   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        receiver = new MyReceiver();
        filter = new IntentFilter(ACTION_RECEVIER_INTENT_SERVICE_REGISTER);
        this.getActivity().registerReceiver(receiver, filter);
    }*/

  /*  private MyReceiver receiver;
    private IntentFilter filter;
*/
   /* private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            MyLog.show("onReceiver");
            ls = intent.getStringExtra(MyService.KEY_RETURN_JSON);
            if(ls.equals("ok")){
                Toast.makeText(getActivity(), "注册成功!", Toast.LENGTH_SHORT).show();
                fm=getFragmentManager();
                trans=fm.beginTransaction();
                trans.replace(R.id.main_id,new LoginFragment());
                trans.commit();
            }else{
                Toast.makeText(getActivity(), "注册失败", Toast.LENGTH_SHORT).show();
            }
        }
    }*/

    public static final String ACTION_RECEVIER_INTENT_SERVICE_REGISTER = "com.simple.action.intentfilter.finish.recevier.register";
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        receiver = new MyReceiver();
        filter = new IntentFilter(ACTION_RECEVIER_INTENT_SERVICE_REGISTER);
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
                Toast.makeText(getActivity(), "注册成功!", Toast.LENGTH_SHORT).show();
                fm=getFragmentManager();
                trans=fm.beginTransaction();
                trans.replace(R.id.main_id,new LoginFragment());
                trans.commit();
            }else{
                Toast.makeText(getActivity(), "注册失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
