package com.example.my.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.chapter3.demo.R;
import com.example.my.activity.NoteActivity;
import com.example.my.listview.Task;

public class ExecutorDetailFragment extends Fragment {
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
        View view = inflater.inflate(R.layout.fragment_executor_detail, container, false);
        TextView name = view.findViewById(R.id.TaskName);
        TextView ddl1=view.findViewById(R.id.ddl1);
        TextView ddl2=view.findViewById(R.id.ddl2);
        TextView tl=view.findViewById(R.id.Teamleader);
        TextView checkman=view.findViewById(R.id.Checkman);
        TextView note=view.findViewById(R.id.note);
        TextView maketime=view.findViewById(R.id.makeTime);
        TextView finish1=view.findViewById(R.id.finish1);
        TextView finish2=view.findViewById(R.id.finish2);
        name.append(item.getTask_name());
        ddl1.append(item.getExpected_time());
        ddl2.append(item.getExpected_exam_time());
        tl.append(item.getGroup_leader());
        checkman.append(item.getQuality_inspector());
        note.append(item.getComments());
        maketime.append(item.getCreate_time());
        finish1.append(item.getFinish_time());
        finish2.append(item.getFinish_exam_time());
        Button button=view.findViewById(R.id.add_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        new Intent(getActivity(), NoteActivity.class),
                        REQUEST_CODE_ADD);
            }
        });
        Button button1=view.findViewById(R.id.submit);
        //TODO:提交任务，状态变更为已提交
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return view;
    }
    public static ExecutorDetailFragment newInstance(Task item) {
        ExecutorDetailFragment fragmentDemo = new ExecutorDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("task", item);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }
}
