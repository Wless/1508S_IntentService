package com.example.wedless.myintentservice.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.wedless.myintentservice.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogoFragment extends Fragment {

    private ImageView image_logo;
    private FragmentManager fm;
    private FragmentTransaction trans;
    public LogoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = null;
        view=inflater.inflate(R.layout.fragment_logo,null);
        image_logo=view.findViewById(R.id.image_logo);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fm=getActivity().getSupportFragmentManager();
                trans=fm.beginTransaction();
                trans.replace(R.id.main_id,new LoginFragment());
                trans.commit();
            }
        },3000);
        return view;
    }

}
