package com.example.my.fragment;

import android.app.Activity;
import android.os.Bundle;
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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CheckManagerFragment_1 extends Fragment {
    private ArrayList<Task> tasks;
    private ArrayList<Task> showTasks;
    private ArrayAdapter<Task> adapterItems;
    private OnItemSelectedListener listener;

    public interface OnItemSelectedListener {
        public void onItemSelected(Task i);
    }
    public static CheckManagerFragment_1 newInstance(ArrayList<Task> all) {
        CheckManagerFragment_1 mf = new CheckManagerFragment_1();
        mf.tasks=new ArrayList<Task>(all);
        Bundle args = new Bundle();
        mf.setArguments(args);
        return mf;
    }
    @Override
    public void onAttach(@NotNull Activity activity) {
        super.onAttach(activity);
        if (activity instanceof CheckManagerFragment_1.OnItemSelectedListener) {
            listener = (CheckManagerFragment_1.OnItemSelectedListener) activity;
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
        View view=inflater.inflate(R.layout.fragment_checkmanager1, container, false);
        TextView text=view.findViewById(R.id.mnum);

        //TODO:编辑超期任务数.此处是质检超期
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
}
