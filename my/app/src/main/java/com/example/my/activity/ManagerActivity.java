package com.example.my.activity;

import android.content.Intent;
import android.os.Bundle;
import com.example.my.fragment.ManagerFragment;
import com.example.my.fragment.ProductorFragment;
import com.example.my.listview.Task;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter3.demo.R;

public class ManagerActivity extends AppCompatActivity implements ManagerFragment.OnItemSelectedListener,ProductorFragment.OnItemSelectedListener{
    private static final int PAGE_COUNT = 2;
    private int role;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        //此处模拟用户角色。0：系统管理员 1：生产部经理 2：组长 3：质检部经理 4：质检员 5：行政综合部
        role=1;
        setContentView(R.layout.manager);
        ViewPager pager=findViewById(R.id.view_pager);
        TabLayout tabLayout=findViewById(R.id.tabs);
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //此处根据用户点击的图标以及用户的角色动态决定装载哪个fragment
            @Override
            public Fragment getItem(int i) {
                //后续会往newInstance函数里传参
                switch (role){
                    case 0:
                        return ManagerFragment.newInstance();
                    default:
                        return ProductorFragment.newInstance();
                }
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
        Intent i = new Intent(this, SettingAcitivity.class);
        //i.putExtra("task", task);
        startActivity(i);
    }
}
