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
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.wedless.myintentservice.R;
import com.example.wedless.myintentservice.bean.User;
import com.example.wedless.myintentservice.service.MyService;
import com.example.wedless.myintentservice.util.CodeUtils;
import com.example.wedless.myintentservice.util.MyLog;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    private MyService.MyBinder myBinder;
    private EditText et_account;
    private EditText et_password;
    private EditText et_yz;
    private ImageButton ib_login;
    private ImageButton ib_register;
    private ImageButton yzimg;
    private MyServiceConnection conn;
    private User user=new User();
    private String ls;
    private Intent intent;
    private FragmentManager fm;
    private CodeUtils codeUtils;
    private FragmentTransaction trans;
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ib_login:
                user=new User();
                String account=et_account.getText().toString();
                String password=et_password.getText().toString();
                intent=new Intent(getActivity(),MyService.class);
                intent.putExtra("account",account);
                intent.putExtra("password",password);
                intent.setAction(MyService.ACTION_LOGIN);
                getActivity().startService(intent);
                conn=new MyServiceConnection();
                getActivity().bindService(intent,conn,Context.BIND_AUTO_CREATE);
                break;
            case R.id.ib_register:
                fm=getFragmentManager();
                trans=fm.beginTransaction();
                trans.replace(R.id.main_id,new RegisterFragment());
                trans.commit();
                break;
            case R.id.yzimg:
                codeUtils= CodeUtils.getInstance();
                yzimg.setImageBitmap(codeUtils.createBitmap());
                break;
        }
    }


    public class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            myBinder= (MyService.MyBinder) iBinder;
            ls=myBinder.loginReturn();
            if(ls!=null){
                if(ls.equals("noaccount")){
                    Toast.makeText(getActivity(), "用户名不存在,请重新输入!", Toast.LENGTH_SHORT).show();
                }else if(ls.equals("nopassword")){
                    Toast.makeText(getActivity(), "密码输入错误,请重新输入!", Toast.LENGTH_SHORT).show();
                }else if(ls.equals("ok")){
                    if(et_yz.getText().toString().equalsIgnoreCase(codeUtils.getCode())) {
                        Toast.makeText(getActivity(), "登录成功!", Toast.LENGTH_SHORT).show();
                        fm = getFragmentManager();
                        trans = fm.beginTransaction();
                        trans.replace(R.id.main_id, new ListFragment());
                        trans.commit();
                    }else{
                        Toast.makeText(getActivity(), "验证码错误,请重新输入! ", Toast.LENGTH_SHORT).show();
                    }
                }
            }else{
                Toast.makeText(getActivity(), "用户名不存在,请重新输入!", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = null;
        view=inflater.inflate(R.layout.fragment_login,null);
        et_account=view.findViewById(R.id.et_account);
        et_password=view.findViewById(R.id.et_password);
        et_yz=view.findViewById(R.id.yz);
        yzimg=view.findViewById(R.id.yzimg);
        codeUtils= CodeUtils.getInstance();
        yzimg.setImageBitmap(codeUtils.createBitmap());
        yzimg.setOnClickListener(this);
        ib_login=view.findViewById(R.id.ib_login);
        ib_login.setOnClickListener(this);
        ib_register=view.findViewById(R.id.ib_register);
        ib_register.setOnClickListener(this);
        return view;
    }

    public static final String ACTION_RECEVIER_INTENT_SERVICE = "com.simple.action.intentfilter.finish.recevier";
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        receiver = new MyReceiver();
        filter = new IntentFilter(ACTION_RECEVIER_INTENT_SERVICE);

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
            if(ls.equals("noaccount")){
                    Toast.makeText(getActivity(), "用户名不存在,请重新输入!", Toast.LENGTH_SHORT).show();
                }else if(ls.equals("nopassword")){
                    Toast.makeText(getActivity(), "密码输入错误,请重新输入!", Toast.LENGTH_SHORT).show();
                }else if(ls.equals("ok")){
                if(et_yz.getText().toString().equalsIgnoreCase(codeUtils.getCode())) {
                    Toast.makeText(getActivity(), "登录成功!", Toast.LENGTH_SHORT).show();
                    fm = getFragmentManager();
                    trans = fm.beginTransaction();
                    trans.replace(R.id.main_id, new ListFragment());
                    trans.commit();
                }else{
                    Toast.makeText(getActivity(), "验证码错误,请重新输入! ", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
