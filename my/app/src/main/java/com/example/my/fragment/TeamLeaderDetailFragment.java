package com.example.my.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.chapter3.demo.R;
import com.example.my.activity.NoteActivity;
import com.example.my.listview.Task;
import com.example.my.utils.XToastUtils;
import com.xuexiang.xui.widget.picker.widget.TimePickerView;
import com.xuexiang.xui.widget.picker.widget.builder.TimePickerBuilder;
import com.xuexiang.xui.widget.picker.widget.configure.TimePickerType;
import com.xuexiang.xui.widget.picker.widget.listener.OnTimeSelectListener;
import com.xuexiang.xutil.data.DateUtils;

import java.util.Calendar;
import java.util.Date;

public class TeamLeaderDetailFragment extends Fragment {
    private Task item;
    private static final int REQUEST_CODE_ADD = 1002;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item = (Task) getArguments().getSerializable("task");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teamleader_detail, container, false);
        TextView name = view.findViewById(R.id.TaskName);
        TextView note=view.findViewById(R.id.note);
        TextView maketime=view.findViewById(R.id.makeTime);
        TextView ddl1=view.findViewById(R.id.ddl1);
        name.append(item.getTask_name());
        ddl1.append(item.getExpected_time());
        note.append(item.getComments());
        maketime.append(item.getCreate_time());
        Button button=view.findViewById(R.id.add_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
        Button submit=view.findViewById(R.id.submit);
        //Todo:提交到质检部，将任务状态改为待质检,编辑任务完成时间
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return view;
    }
    public static TeamLeaderDetailFragment newInstance(Task item) {
        TeamLeaderDetailFragment fragmentDemo = new TeamLeaderDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("task", item);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }
    private void add(){
        String note=item.getComments();
        String id=item.getTask_id();
        Intent i=new Intent(getActivity(),NoteActivity.class);
        i.putExtra("note",note);
        i.putExtra("id",id);
        startActivity(i);
    }
}
