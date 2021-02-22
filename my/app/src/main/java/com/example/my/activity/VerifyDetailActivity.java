package com.example.my.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.chapter3.demo.R;
import com.example.my.fragment.CheckmanDetailFragment;
import com.example.my.fragment.VerifyFragment;
import com.example.my.listview.Task;
import com.example.my.listview.User;

public class VerifyDetailActivity extends FragmentActivity {
    VerifyFragment fragmentItemDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("here!!", "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        User item= (User) getIntent().getSerializableExtra("user");
        if (savedInstanceState == null) {
            fragmentItemDetail = VerifyFragment.newInstance(item);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.managerDetailContainer, fragmentItemDetail);
            ft.commit();
        }
    }
}
