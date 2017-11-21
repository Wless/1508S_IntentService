package com.example.wedless.myintentservice.httpurlconnection;

import com.example.wedless.myintentservice.bean.Goods;
import com.example.wedless.myintentservice.bean.User;
import com.example.wedless.myintentservice.util.JsonUtil;
import com.example.wedless.myintentservice.util.MyLog;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Wedless on 2017/11/17.
 */

public class MyUrlConnection  {
    private static final String URL_LOGIN="http://10.101.1.2:8080/user/login";
    private static final String URL_REGISTER="http://10.101.1.2:8080/user/register";
    private static final String URL_QUERY="http://10.101.1.2:8080/goods/list";
    private static final String URL_INSERT="http://10.101.1.2:8080/goods/add";
    private static final String URL_DELETE="http://10.101.1.2:8080/goods/delete";
    private static final String URL_UPDATE="http://10.101.1.2:8080/goods/update";
    private static HttpURLConnection conn;
    private static InputStream ips;
    private static OutputStream ops;
    private static String ls;
    private static ArrayList<Goods> lists;
    public static String login(User user){
        String ls="";
        try {
            URL url=new URL(URL_LOGIN);
            conn= (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.connect();
            ops=conn.getOutputStream();
            String infoStr="";
            String account="account="+user.getAccount();
            String password="password="+user.getPassword();
            infoStr=account+"&"+password;
            PrintWriter pw=new PrintWriter(new OutputStreamWriter(ops));
            pw.write(infoStr);
            pw.flush();
            pw.close();
            MyLog.show(infoStr);
            ips=conn.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(ips));
            StringBuffer sb=new StringBuffer();
            String line="";
            if((line=br.readLine())!=null){
                sb.append(line.toString());
            }
            MyLog.show(line);
            try {
                ls= JsonUtil.login(sb.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(ips!=null){
                try {
                    ips.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                conn.disconnect();
            }
        }
        return ls;
    }
    public static String register(User user){
        String ls="";
        try {
            URL url=new URL(URL_REGISTER);
            conn= (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.connect();
            ops=conn.getOutputStream();
            String infoStr="";
            String account="account="+user.getAccount();
            String password="password="+user.getPassword();
            infoStr=account+"&"+password;
            PrintWriter pw=new PrintWriter(new OutputStreamWriter(ops));
            pw.write(infoStr);
            pw.flush();
            pw.close();
            MyLog.show(infoStr);
            ips=conn.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(ips));
            StringBuffer sb=new StringBuffer();
            String line="";
            if((line=br.readLine())!=null){
                sb.append(line.toString());
            }
            MyLog.show(line);
            try {
                ls= JsonUtil.login(sb.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(ips!=null){
                try {
                    ips.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                conn.disconnect();
            }
        }
        return ls;
    }

    public static ArrayList<Goods> query(){
        try {
            URL url=new URL(URL_QUERY);
            conn= (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.connect();
            ips=conn.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(ips));
            StringBuffer sb=new StringBuffer();
            String line="";
            if((line=br.readLine())!=null){
                sb.append(line.toString());
            }
            MyLog.show(line);
            try {
                lists=JsonUtil.list(sb.toString());
                return lists;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(ips!=null){
                try {
                    ips.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                conn.disconnect();
            }
        }
        return lists;
    }

    public static String insert(Goods goods) {
        String ls="";
        try {
            URL url=new URL(URL_INSERT);
            conn= (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.connect();
            ops=conn.getOutputStream();
            String infoStr="";
            String num="num="+goods.getNum();
            String name="name="+goods.getName();
            String price="price="+goods.getPrice();
            infoStr=num+"&"+name+"&"+price;
            PrintWriter pw=new PrintWriter(new OutputStreamWriter(ops));
            pw.write(infoStr);
            pw.flush();
            pw.close();
            MyLog.show(infoStr);
            ips=conn.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(ips));
            StringBuffer sb=new StringBuffer();
            String line="";
            if((line=br.readLine())!=null){
                sb.append(line.toString());
            }
            MyLog.show(line);
            try {
                ls= JsonUtil.login(sb.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(ips!=null){
                try {
                    ips.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                conn.disconnect();
            }
        }
        return ls;
    }

    public static String delete(int id) {
        String ls="";
        try {
            URL url=new URL(URL_DELETE);
            conn= (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.connect();
            ops=conn.getOutputStream();
            String infoStr="";
            infoStr="id="+id;
            PrintWriter pw=new PrintWriter(new OutputStreamWriter(ops));
            pw.write(infoStr);
            pw.flush();
            pw.close();
            MyLog.show(infoStr);
            ips=conn.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(ips));
            StringBuffer sb=new StringBuffer();
            String line="";
            if((line=br.readLine())!=null){
                sb.append(line.toString());
            }
            MyLog.show(line);
            try {
                ls= JsonUtil.login(sb.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(ips!=null){
                try {
                    ips.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                conn.disconnect();
            }
        }
        return ls;
    }

    public static String update(Goods goods) {
        String ls="";
        try {
            URL url=new URL(URL_UPDATE);
            conn= (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.connect();
            ops=conn.getOutputStream();
            String infoStr="";
            String id="id="+goods.getId();
            String num="num="+goods.getNum();
            String name="name="+goods.getName();
            String price="price="+goods.getPrice();
            infoStr=id+"&"+num+"&"+name+"&"+price;
            PrintWriter pw=new PrintWriter(new OutputStreamWriter(ops));
            pw.write(infoStr);
            pw.flush();
            pw.close();
            MyLog.show(infoStr);
            ips=conn.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(ips));
            StringBuffer sb=new StringBuffer();
            String line="";
            if((line=br.readLine())!=null){
                sb.append(line.toString());
            }
            MyLog.show(line);
            try {
                ls= JsonUtil.login(sb.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(ips!=null){
                try {
                    ips.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                conn.disconnect();
            }
        }
        return ls;
    }
}
