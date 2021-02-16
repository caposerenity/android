package com.example.my.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.chapter3.demo.R;
import com.example.my.activity.AddTaskActivity;
import com.example.my.activity.NoteActivity;
import com.example.my.adapter.TaskAdapter;
import com.example.my.listview.Task;

import java.util.ArrayList;

public class ProductorFragment extends Fragment {
    private ArrayList<Task> tasks;
    private ArrayList<Task> showTasks;
    private ArrayAdapter<Task> adapterItems;
    private OnItemSelectedListener listener;
    private static final int REQUEST_CODE_ADD_TASK = 1003;

    public interface OnItemSelectedListener {
        public void onItemSelected(Task i);
    }
    //后续在此传参给fragment
    public static ProductorFragment newInstance() {
        ProductorFragment mf = new ProductorFragment();
        Bundle args = new Bundle();
        mf.setArguments(args);
        return mf;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ProductorFragment.OnItemSelectedListener) {
            listener = (ProductorFragment.OnItemSelectedListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement ItemsListFragment.OnItemSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create arraylist from item fixtures
        tasks= Task.getItems();
        showTasks=new ArrayList<Task>(tasks);
        adapterItems = new TaskAdapter(getActivity(),
                R.layout.list_item, showTasks);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("producer", "onCreateView: ");
        View view=inflater.inflate(R.layout.fragment_producer, container, false);
        Spinner spinner=view.findViewById(R.id.pspinner);
        TextView text1=view.findViewById(R.id.pnum1);
        TextView text2=view.findViewById(R.id.pnum2);
        Button addbutton=view.findViewById(R.id.add_button);
        //后续在此编辑选择条件
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //后续在此传入超期任务数和待完成任务数
        text1.setText("1");
        text2.setText("2");
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        new Intent(getActivity(), AddTaskActivity.class),
                        REQUEST_CODE_ADD_TASK);
            }
        });
        ListView lvItems = (ListView) view.findViewById(R.id.plist);
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
