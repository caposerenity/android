package com.example.my.activity;

import android.util.Log;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;


public class testconnect {


    public static String getRequest(String url) {
        //url ="http://10.0.2.2:8000/api/user/hello"
        String finalUrl = url;
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(finalUrl).build();
                try {
                    Response response = client.newCall(request).execute();//发送请求
                    String result = response.body().string();
                    Log.d("123OKhttp", "result: " + result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return finalUrl;
    }


    public static String postRequest(String url, RequestBody requestBody) {
        //RequestBody构造格式:RequestBody requestBody = new FormBody.Builder().add("param1", "value1").add("param2", "value2").build();
        //url ="http://10.0.2.2:8000/api/user/hello"
        String finalUrl = url;

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(finalUrl).post(requestBody).addHeader("contentType","application/json;charset=UTF-8").build();
                try {
                    Response response = client.newCall(request).execute();//发送请求
                    String result = response.body().string();
                    Log.d("123OKhttp", "result: " + result);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return finalUrl;
    }
}





