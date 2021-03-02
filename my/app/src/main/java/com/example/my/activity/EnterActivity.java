package com.example.my.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EnterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPre=getSharedPreferences("config", MODE_PRIVATE);
        int id=sharedPre.getInt("user_id",0);
        if(id==0){
            Intent i=new Intent(this,LoginActivity.class);
            startActivity(i);
        }
        else{
            startActivity(new Intent(this,MainActivity.class));
        }
    }
}
