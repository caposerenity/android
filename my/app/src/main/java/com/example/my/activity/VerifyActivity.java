package com.example.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.chapter3.demo.R;
import com.example.my.adapter.UserAdapter;
import com.example.my.listview.User;
import org.json.JSONObject;
import rxhttp.RxHttp;
import java.util.ArrayList;
import com.example.my.utils.roleConvert;

public class VerifyActivity extends AppCompatActivity{
    private ArrayAdapter<User> userAdapters;
    private ArrayList<User> users;
    private ArrayList<Integer> usersId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        users=new ArrayList<User>();
        RxHttp.get("http://192.168.1.106:8000/api/user/Visitor/filterPosition")
                .asList(String.class)
                .subscribe(res->{
                    if(res.size()!=0){
                        for (String leaderI : res) {
                            JSONObject j = new JSONObject(leaderI);
                            String name=j.getString("name").split("-")[0];
                            String role=j.getString("name").split("-")[1];
                            users.add(new User(name,roleConvert.roleEngToCN(role),j.getString("phone")));
                        }
                    }
                },throwable -> {

                });

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
