package com.example.my.activity;

import android.content.Intent;
import android.os.Bundle;
import com.example.my.fragment.ManagerFragment;
import com.example.my.listview.Task;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter3.demo.R;

public class ManagerActivity extends AppCompatActivity implements ManagerFragment.OnItemSelectedListener{
    private static final int PAGE_COUNT = 2;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.manager);
        ViewPager pager=findViewById(R.id.view_pager);
        TabLayout tabLayout=findViewById(R.id.tabs);
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //此处根据用户的角色和点击的图标决定装载哪个fragment
            @Override
            public Fragment getItem(int i) {
                //后续会往newInstance函数里传参
                return ManagerFragment.newInstance();
            }

            @Override
            public int getCount() {
                return PAGE_COUNT;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                if(position==0){
                    return "任务";
                }
                else{
                    return "我的";
                }
            }
        });
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public void onItemSelected(Task task) {
        Intent i = new Intent(this, ManagerDetailActivity.class);
        i.putExtra("task", task);
        startActivity(i);
    }
}
