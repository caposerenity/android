package com.example.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter3.demo.R;
import com.example.my.adapter.UserAdapter;
import com.example.my.listview.User;

import java.util.ArrayList;

public class VerifyActivity extends AppCompatActivity{
    private ArrayAdapter<User> userAdapters;
    private ArrayList<User> users;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        //TODO:在此获取待审核的全部用户
        users=User.getItems();
        userAdapters=new UserAdapter(this,R.layout.verify_item,users);
        ListView list=findViewById(R.id.list);
        list.setAdapter(userAdapters);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User u=userAdapters.getItem(position);
                jumpToDetail(u);
            }
        });
    }
    private void jumpToDetail(User u){
        Intent i=new Intent(VerifyActivity.this, VerifyDetailActivity.class);
        i.putExtra("user",u);
        startActivity(i);
    };
}
