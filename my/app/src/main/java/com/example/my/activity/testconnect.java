package com.example.my.activity;

import android.util.Log;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class testconnect {
    public static void sendByOKHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://10.0.2.2:8000/api/user/hello").build();
                try {
                    Response response = client.newCall(request).execute();//发送请求
                    String result = response.body().string();
                    Log.d("123OKhttp", "result: " + result);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
