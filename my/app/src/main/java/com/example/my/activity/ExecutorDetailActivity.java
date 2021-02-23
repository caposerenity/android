package com.example.my.activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.chapter3.demo.R;
import com.example.my.fragment.ExecutorDetailFragment;
import com.example.my.listview.Task;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

public class ExecutorDetailActivity extends FragmentActivity {
    ExecutorDetailFragment fragmentItemDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Task item= (Task) getIntent().getSerializableExtra("task");
        if (savedInstanceState == null) {
            fragmentItemDetail = ExecutorDetailFragment.newInstance(item);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.managerDetailContainer, fragmentItemDetail);
            ft.commit();
        }
    }

}
