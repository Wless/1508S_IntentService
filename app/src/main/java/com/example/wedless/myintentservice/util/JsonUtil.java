package com.example.wedless.myintentservice.util;

import com.example.wedless.myintentservice.bean.Goods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Wedless on 2017/11/17.
 */

public class JsonUtil {
    public static String login(String json) throws JSONException {
        String ls="";
        JSONObject jo=new JSONObject(json);
        ls=jo.getString("return");
        return ls;
    }
    public static ArrayList<Goods> list(String json) throws JSONException {
        ArrayList<Goods> lists=new ArrayList<>();
        JSONArray ja=new JSONArray(json);
        for (int i = 0; i <ja.length() ; i++) {
            Goods g=new Goods();
            JSONObject jo=ja.getJSONObject(i);
            g.setId(jo.getInt("id"));
            g.setNum(jo.getString("num"));
            g.setName(jo.getString("name"));
            g.setPrice(jo.getString("price"));
            lists.add(g);
        }
        return lists;
    }
}
