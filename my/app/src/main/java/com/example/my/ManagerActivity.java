package com.example.my;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


import com.example.my.fragment.ManagerFragment;

public class ManagerActivity extends AppCompatActivity {
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

}
