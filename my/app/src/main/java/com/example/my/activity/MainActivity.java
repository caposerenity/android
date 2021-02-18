package com.example.my.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.my.fragment.CheckManagerFragment;
import com.example.my.fragment.CheckManagerFragment_1;
import com.example.my.fragment.CheckmanFragment;
import com.example.my.fragment.ExecutorFragment;
import com.example.my.fragment.ManagerFragment;
import com.example.my.fragment.ProductorFragment;
import com.example.my.fragment.TeamLeaderFragment;
import com.example.my.listview.Task;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import com.example.my.activity.testconnect;

import com.example.chapter3.demo.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ManagerFragment.OnItemSelectedListener,ProductorFragment.OnItemSelectedListener,TeamLeaderFragment.OnItemSelectedListener,CheckmanFragment.OnItemSelectedListener,ExecutorFragment.OnItemSelectedListener, CheckManagerFragment_1.OnItemSelectedListener {
    private static final int PAGE_COUNT = 2;
    private int role;
    private String name;

    private void checkNeedPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //多个权限一起申请
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, 1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        checkNeedPermissions();
        //TODO:获取用户角色。0：系统管理员 1：生产部经理 2：组长 3：质检部经理 4：质检员 5：行政综合部
        role=0;
        //TODO:获取用户姓名
        name="张东南";
        setContentView(R.layout.manager);
        ViewPager pager=findViewById(R.id.view_pager);
        TabLayout tabLayout=findViewById(R.id.tabs);
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                //TODO:获取全部任务
                ArrayList<Task> tasks=Task.getItems();
                switch (role){
                    case 0:
                        return ManagerFragment.newInstance(tasks);
                    case 1:
                        return ProductorFragment.newInstance(tasks);
                    case 2:
                        return TeamLeaderFragment.newInstance(tasks,"张西秀");
                    case 3:
                        return CheckManagerFragment.newInstance("张东南",tasks);
                    case 4:
                        return CheckmanFragment.newInstance("张东南",tasks);
                    default:
                        return ExecutorFragment.newInstance(tasks);
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

        if(role==0||role==1){
            Intent i = new Intent(this, ManagerDetailActivity.class);
            i.putExtra("task", task);
            startActivity(i);
        }
        else if(role==2){
            Intent i = new Intent(this, TeamLeaderDetailActivity.class);
            i.putExtra("task", task);
            startActivity(i);
        }
        else if(role==3){
            if(task.getTag().equals("待质检")){
                Intent i = new Intent(this, CheckManagerDetail1Activity.class);
                i.putExtra("task", task);
                startActivity(i);
            }
            //TODO：
            else if(task.getCheckman().equals(name)){
                Intent i = new Intent(this, CheckmanDetailActivity.class);
                i.putExtra("task", task);
                startActivity(i);
            }
            else{
                Intent i = new Intent(this, CheckManagerDetail2Activity.class);
                i.putExtra("task", task);
                startActivity(i);
            }
        }
        else if(role==4){
            Intent i = new Intent(this, CheckmanDetailActivity.class);
            i.putExtra("task", task);
            startActivity(i);
        }
        else if(role==5){
            Intent i = new Intent(this, ExecutorDetailActivity.class);
            i.putExtra("task", task);
            startActivity(i);
        }
    }
}
