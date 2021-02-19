package com.example.my.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.chapter3.demo.R;
import com.example.my.adapter.TaskAdapter;
import com.example.my.listview.Task;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;

import java.util.ArrayList;

public class TeamLeaderFragment extends Fragment {
    private ArrayList<Task> tasks;
    private ArrayList<Task> showTasks;
    private ArrayAdapter<Task> adapterItems;
    private TeamLeaderFragment.OnItemSelectedListener listener;
    //这里是组长名称
    private String leaderName;

    public interface OnItemSelectedListener {
        public void onItemSelected(Task i);
    }

    //MainActivity需要传入组长名称
    public static TeamLeaderFragment newInstance(ArrayList<Task> all,String leaderName) {
        TeamLeaderFragment mf = new TeamLeaderFragment();
        mf.tasks=new ArrayList<Task>();
        for(int i=0;i<all.size();i++){
            if((all.get(i).getGroup_leader().equals(leaderName))&&(all.get(i).getStatus().equals("待完成")||all.get(i).getStatus().equals("不合格"))){
                mf.tasks.add(all.get(i));
            }
        }
        mf.leaderName=leaderName;
        Bundle args = new Bundle();
        mf.setArguments(args);
        return mf;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof TeamLeaderFragment.OnItemSelectedListener) {
            listener = (TeamLeaderFragment.OnItemSelectedListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement ItemsListFragment.OnItemSelectedListener");
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create arraylist from item fixtures
        showTasks=new ArrayList<Task>(tasks);
        adapterItems = new TaskAdapter(getActivity(),
                R.layout.list_item, showTasks);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_teamleader, container, false);
        MaterialSpinner mMaterialSpinner=view.findViewById(R.id.spinner);
        TextView text1=view.findViewById(R.id.tnum1);
        TextView text2=view.findViewById(R.id.tnum2);


        mMaterialSpinner.setOnItemSelectedListener((spinner, position, id, item) ->
        {
            switch(position){
                //很奇怪。。这么写之后调试的时候没有问题，打包成apk使用就显示不了了
                case 0:
                    for(int i=0;i<tasks.size();i++){
                        if(!showTasks.contains(tasks.get(i))){
                            showTasks.add(tasks.get(i));
                        }
                    }
                    break;
                case 1:
                    update("待完成");
                    break;
                case 2:
                    update("不合格");
                    break;
            }
            adapterItems.notifyDataSetChanged();
        });
        //后续在此编辑超期任务数
        text1.setText("1");
        //在此编辑待完成任务数
        text2.setText("2");
        ListView lvItems = (ListView) view.findViewById(R.id.tlist);
        lvItems.setAdapter(adapterItems);
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position,
                                    long rowId) {
                // Retrieve item based on position
                Task i = adapterItems.getItem(position);
                // Fire selected event for item
                listener.onItemSelected(i);
            }
        });
        return view;
    }
    private void update(String s){
        Log.d("abc", s);
        for(int i=0;i<tasks.size();i++){
            if(tasks.get(i).getStatus().equals(s)){
                Log.d(tasks.get(i).getStatus(), s);
                if(!showTasks.contains(tasks.get(i))){
                    showTasks.add(tasks.get(i));
                }
            }
            if(!tasks.get(i).getStatus().equals(s)){
                showTasks.remove(tasks.get(i));
            }
        }
    }
}
