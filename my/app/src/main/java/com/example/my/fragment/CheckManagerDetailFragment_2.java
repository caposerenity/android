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
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;

public class CheckManagerDetailFragment_2 extends Fragment {
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
        View view = inflater.inflate(R.layout.fragment_checkmanager2_detail, container, false);
        TextView name = view.findViewById(R.id.TaskName);
        TextView checkman=view.findViewById(R.id.Checkman);
        TextView ddl2=view.findViewById(R.id.ddl2);
        TextView tl=view.findViewById(R.id.Teamleader);
        TextView note=view.findViewById(R.id.note);
        TextView maketime=view.findViewById(R.id.makeTime);
        TextView finish1=view.findViewById(R.id.finish1);
        name.append(item.getTask_name());
        ddl2.append(item.getExpected_exam_time());
        tl.append(item.getGroup_leader());
        note.append(item.getComments());
        maketime.append(item.getCreate_time());
        finish1.append(item.getFinish_time());
        Button button=view.findViewById(R.id.add_button);
        checkman.append(item.getQuality_inspector());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
        return view;
    }
    public static CheckManagerDetailFragment_2 newInstance(Task item) {
        CheckManagerDetailFragment_2 fragmentDemo = new CheckManagerDetailFragment_2();
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
