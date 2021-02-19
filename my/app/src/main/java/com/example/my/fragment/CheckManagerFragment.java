package com.example.my.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.chapter3.demo.R;
import com.example.my.listview.Task;
import com.xuexiang.xui.widget.tabbar.TabSegment;

import java.util.ArrayList;

public class CheckManagerFragment extends Fragment {
    private static final int PAGE_COUNT = 3;
    private ArrayList<Task> tasks;
    private String myName;

    public static CheckManagerFragment newInstance(String Checkman,ArrayList<Task> all) {
        CheckManagerFragment mf = new CheckManagerFragment();
        mf.tasks=new ArrayList<Task>();
        mf.myName=Checkman;
        for(int i=0;i<all.size();i++){
            if((all.get(i).getStatus().equals("待质检")||all.get(i).equals("质检中"))){
                mf.tasks.add(all.get(i));
            }
        }
        Bundle args = new Bundle();
        mf.setArguments(args);
        return mf;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_checkmanager, container, false);
        ViewPager viewPager=view.findViewById(R.id.contentViewPager);
        TabSegment tab=view.findViewById(R.id.tabSegment);
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                switch (i){
                    case 0:
                        ArrayList<Task> args=new ArrayList<Task>();
                        for(int j=0;j<tasks.size();j++){
                            if(tasks.get(j).getStatus().equals("待质检")){
                                args.add(tasks.get(j));
                            }
                        }
                        return CheckManagerFragment_1.newInstance(args);
                    case 1:
                        ArrayList<Task> args2=new ArrayList<Task>();
                        for(int j=0;j<tasks.size();j++){
                            if(tasks.get(j).getStatus().equals("质检中")){
                                args2.add(tasks.get(j));
                            }
                        }
                        return CheckManagerFragment_1.newInstance(args2);
                    default:
                        Log.d("？", "hahaha ");
                        return CheckmanFragment.newInstance(myName,tasks);
                }
            }

            @Override
            public int getCount() {
                return PAGE_COUNT;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                if(position==0){
                    return "待分配";
                }
                else if(position==1){
                    return "质检中";
                }
                return "我的质检";
            }
        });
        tab.setupWithViewPager(viewPager);
        return view;

    }
}
