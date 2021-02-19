package com.example.my.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.chapter3.demo.R;
import com.example.my.activity.testconnect;
import com.example.my.adapter.TaskAdapter;
import com.example.my.listview.Task;
import com.xuexiang.xui.utils.SnackbarUtils;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;

import java.util.ArrayList;

public class ManagerFragment extends Fragment {
    private ArrayList<Task> tasks;
    private ArrayList<Task> showTasks;
    private ArrayAdapter<Task> adapterItems;
    private OnItemSelectedListener listener;

    public interface OnItemSelectedListener {
        public void onItemSelected(Task i);
    }

    public static ManagerFragment newInstance(ArrayList<Task> tasks) {
        ManagerFragment mf = new ManagerFragment();
        mf.tasks=tasks;
        Bundle args = new Bundle();
        mf.setArguments(args);
        return mf;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) activity;
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
        View view=inflater.inflate(R.layout.fragment_manager, container, false);
        MaterialSpinner mMaterialSpinner=view.findViewById(R.id.spinner);
        TextView text=view.findViewById(R.id.mnum);


        mMaterialSpinner.setOnItemSelectedListener((spinner, position, id, item) ->
        {
            switch(position){
                    case 0:
                        for(int i=0;i<tasks.size();i++){
                            if(!showTasks.contains(tasks.get(i))){
                                showTasks.add(tasks.get(i));
                            }
                        }
                        break;
                    case 1:
                        testconnect.sendByOKHttp();
                        update("待完成");
                        break;
                    case 2:
                        update("待质检");
                        break;
                    case 3:
                        update("质检中");
                        break;
                    case 4:
                        update("不合格");
                        break;
                    case 5:
                        update("待提交客户");
                        break;
                    case 6:
                        update("已提交客户");
                        break;
                }
            adapterItems.notifyDataSetChanged();
        });
        //TODO:编辑超期任务数
        text.setText("1");
        ListView lvItems = (ListView) view.findViewById(R.id.mlist);
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
            if(tasks.get(i).getTag().equals(s)){
                Log.d(tasks.get(i).getTag(), s);
                if(!showTasks.contains(tasks.get(i))){
                    showTasks.add(tasks.get(i));
                }
            }
            if(!tasks.get(i).getTag().equals(s)){
                showTasks.remove(tasks.get(i));
            }
        }
    }
}
