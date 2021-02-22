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
import com.example.my.listview.User;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;

import org.w3c.dom.Text;

public class VerifyFragment extends Fragment {
    private User item;
    private static final int REQUEST_CODE_ADD = 1003;
    private String[] Result;
    private int resultSelectOption = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item = (User) getArguments().getSerializable("user");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_verify_detail, container, false);
        TextView name=view.findViewById(R.id.name);
        TextView role=view.findViewById(R.id.role);
        TextView phone=view.findViewById(R.id.phone);
        Button button=view.findViewById(R.id.submit);
        name.append(item.getName());
        role.append(item.getRole());
        phone.append(item.getPhone());
        Result= new String[]{"通过", "不通过"};
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), (v, options1, options2, options3) -> {
                    resultSelectOption = options1;
                    return false;
                })
                        .setTitleText("检查结果")
                        .setSelectOptions(resultSelectOption)
                        .build();
                //TODO：将检查结果保存并修改任务状态.0:合格。1：不合格
                pvOptions.setPicker(Result);
                pvOptions.show();
            }
        });
        return view;
    }
    public static VerifyFragment newInstance(User item) {
        VerifyFragment fragmentDemo = new VerifyFragment();
        Bundle args = new Bundle();
        args.putSerializable("user", item);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }
}
