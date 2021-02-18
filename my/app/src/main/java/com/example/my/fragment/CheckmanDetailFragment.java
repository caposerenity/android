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
import com.example.my.activity.AddTaskActivity;
import com.example.my.activity.NoteActivity;
import com.example.my.listview.Task;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;

public class CheckmanDetailFragment extends Fragment {
    private Task item;
    private String[] Result;
    private int resultSelectOption = 0;
    private static final int REQUEST_CODE_ADD = 1002;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item = (Task) getArguments().getSerializable("task");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkman_detail, container, false);
        TextView name = view.findViewById(R.id.TaskName);
        TextView ddl2=view.findViewById(R.id.ddl2);
        TextView tl=view.findViewById(R.id.Teamleader);
        TextView note=view.findViewById(R.id.note);
        TextView maketime=view.findViewById(R.id.makeTime);
        TextView finish1=view.findViewById(R.id.finish1);
        name.append(item.getName());
        ddl2.append(item.getDdl2());
        tl.append(item.getTeamleader());
        note.append(item.getNote());
        maketime.append(item.getMaketime());
        finish1.append(item.getFinish1());
        Button button=view.findViewById(R.id.add_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        new Intent(getActivity(), NoteActivity.class),
                        REQUEST_CODE_ADD);
            }
        });
        Button submit=view.findViewById(R.id.submit);
        Result= new String[]{"合格", "不合格"};
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), (v, options1, options2, options3) -> {
                    resultSelectOption = options1;
                    return false;
                })
                        .setTitleText("检查结果")
                        .setSelectOptions(resultSelectOption)
                        .build();
                //TODO：将检查结果保存并修改任务状态
                pvOptions.setPicker(Result);
                pvOptions.show();
            }
        });
        return view;
    }

    public static CheckmanDetailFragment newInstance(Task item) {
        CheckmanDetailFragment fragmentDemo = new CheckmanDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("task", item);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }
}
