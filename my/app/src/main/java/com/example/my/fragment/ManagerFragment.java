package com.example.my.fragment;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.Toast;

import com.example.chapter3.demo.R;
import com.example.my.listview.Task;

import java.util.ArrayList;

public class ManagerFragment extends Fragment {
    private ArrayList<Task> tasks;
    private ArrayList<Task> showTasks;
    private ArrayAdapter<Task> adapterItems;
    private ListView lvItems;
    private OnItemSelectedListener listener;

    public interface OnItemSelectedListener {
        public void onItemSelected(Task i);
    }

    //后续在此传参给fragment
    public static ManagerFragment newInstance() {
        ManagerFragment mf = new ManagerFragment();
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
        tasks= Task.getItems();
        showTasks=new ArrayList<Task>(tasks);
        adapterItems = new ArrayAdapter<Task>(getActivity(),
                R.layout.list_item, showTasks);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_manager, container, false);
        Spinner spinner=view.findViewById(R.id.mspinner);
        TextView text=view.findViewById(R.id.mnum);
        //后续在此编辑选择条件
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(""+tasks.size(), "test: ");
                switch(position){
                    case 0:
                        for(int i=0;i<tasks.size();i++){
                            if(!showTasks.contains(tasks.get(i))){
                                showTasks.add(tasks.get(i));
                            }
                        }
                        Toast.makeText(getActivity(),"全部",Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        for(int i=0;i<tasks.size();i++){
                            if(tasks.get(i).getTag().equals("已提交")){
                                if(!showTasks.contains(tasks.get(i))){
                                    showTasks.add(tasks.get(i));
                                }
                            }
                            if(!tasks.get(i).getTag().equals("已提交")){
                                if(showTasks.contains(tasks.get(i))){
                                    showTasks.remove(tasks.get(i));
                                }
                            }
                        }
                        Toast.makeText(getActivity(),"已提交",Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        for(int i=0;i<tasks.size();i++){
                            if(!tasks.get(i).getTag().equals("已提交")){
                                Log.d(tasks.get(i).getName(), "test: ");
                                if(!showTasks.contains(tasks.get(i))){
                                    showTasks.add(tasks.get(i));
                                }
                            }
                            if(tasks.get(i).getTag().equals("已提交")){
                                if(showTasks.contains(tasks.get(i))){
                                    showTasks.remove(tasks.get(i));
                                }
                            }
                        }
                        Toast.makeText(getActivity(),"未提交",Toast.LENGTH_LONG).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //后续在此编辑超期任务数
        text.setText("1");
        lvItems = (ListView) view.findViewById(R.id.mlist);
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
}
