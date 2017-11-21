package com.example.wedless.myintentservice.util;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.wedless.myintentservice.R;
import com.example.wedless.myintentservice.bean.Goods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wedless on 2017/11/17.
 */

public class MyAdapter extends ArrayAdapter {
    private Context mContext;
    private int mResource;
    private ArrayList<Goods> mlist;
    public MyAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList objects) {
        super(context, resource, objects);
        mContext=context;
        mResource=resource;
        mlist=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;
        LayoutInflater layoutInflater=LayoutInflater.from(mContext);
        view=layoutInflater.inflate(R.layout.list_view_item,null);
        TextView tvnum=view.findViewById(R.id.tv_num);
        TextView tvname=view.findViewById(R.id.tv_name);
        TextView tvprice=view.findViewById(R.id.tv_price);
        tvnum.setText("    "+mlist.get(position).getNum());
        tvname.setText("              "+mlist.get(position).getName());
        tvprice.setText("           "+mlist.get(position).getPrice());
        return view;
    }
}
