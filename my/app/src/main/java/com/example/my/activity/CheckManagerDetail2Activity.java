package com.example.my.activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.chapter3.demo.R;
import com.example.my.fragment.CheckManagerDetailFragment_2;
import com.example.my.listview.Task;


public class CheckManagerDetail2Activity extends FragmentActivity {
    CheckManagerDetailFragment_2 fragmentItemDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Task item= (Task) getIntent().getSerializableExtra("task");
        if (savedInstanceState == null) {
            fragmentItemDetail = CheckManagerDetailFragment_2.newInstance(item);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.managerDetailContainer, fragmentItemDetail);
            ft.commit();
        }
    }
}
