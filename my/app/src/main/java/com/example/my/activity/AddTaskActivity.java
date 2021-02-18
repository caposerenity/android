package com.example.my.activity;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter3.demo.R;
import com.example.my.utils.XToastUtils;
import com.xuexiang.xui.widget.button.ButtonView;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.TimePickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;
import com.xuexiang.xui.widget.picker.widget.builder.TimePickerBuilder;
import com.xuexiang.xui.widget.picker.widget.configure.TimePickerType;
import com.xuexiang.xui.widget.picker.widget.listener.OnTimeSelectListener;
import com.xuexiang.xutil.data.DateUtils;

import java.util.Calendar;
import java.util.Date;

import static com.xuexiang.xui.XUI.getContext;

public class AddTaskActivity extends AppCompatActivity {
    EditText taskName;
    ButtonView teamLeader;
    ButtonView ddl1;
    ButtonView ddl2;
    TextView note;
    Button submit;
    private String[] teamLeaderOption;
    private int leaderSelectOption = 0;
    private TimePickerView ddl1TimePickerDialog;
    private TimePickerView ddl2TimePickerDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        taskName=findViewById(R.id.taskName);
        teamLeader=findViewById(R.id.select_teamleader);
        ddl1=findViewById(R.id.select_ddl1);
        ddl2=findViewById(R.id.select_ddl2);
        note=findViewById(R.id.edit_note);
        submit=findViewById(R.id.submit);
        //TODO:填入第一个组长名
        teamLeader.setText("单击选择组长");
        ddl1.setText("2020-02-28 12:00:00");
        ddl2.setText("2020-02-29 12:00:00");
        ddl1.setOnClickListener(new View.OnClickListener() {
            //TODO:修改完成截止时间
            @Override
            public void onClick(View view) {
                if (ddl1TimePickerDialog == null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(DateUtils.string2Date("2013-07-08 12:32:46", DateUtils.yyyyMMddHHmmss.get()));
                    ddl1TimePickerDialog = new TimePickerBuilder(AddTaskActivity.this, new OnTimeSelectListener() {
                        @Override
                        public void onTimeSelected(Date date, View v) {
                            XToastUtils.toast(DateUtils.date2String(date, DateUtils.yyyyMMddHHmmss.get()));
                            ddl1.setText(DateUtils.date2String(date,DateUtils.yyyyMMddHHmmss.get()));
                        }
                    })
                            .setTimeSelectChangeListener(date -> Log.i("pvTime", "onTimeSelectChanged"))
                            .setType(TimePickerType.ALL)
                            .setTitleText("时间选择")
                            .isDialog(true)
                            .setOutSideCancelable(false)
                            .setDate(calendar)
                            .build();
                }
                ddl1TimePickerDialog.show();
            }
        });
        ddl2.setOnClickListener(new View.OnClickListener() {
            //TODO:修改质检截止时间的操作
            @Override
            public void onClick(View view) {
                if (ddl2TimePickerDialog == null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(DateUtils.string2Date("2013-07-08 12:32:46", DateUtils.yyyyMMddHHmmss.get()));
                    ddl2TimePickerDialog = new TimePickerBuilder(AddTaskActivity.this, new OnTimeSelectListener() {
                        @Override
                        public void onTimeSelected(Date date, View v) {
                            XToastUtils.toast(DateUtils.date2String(date, DateUtils.yyyyMMddHHmmss.get()));
                            ddl2.setText(DateUtils.date2String(date,DateUtils.yyyyMMddHHmmss.get()));
                        }
                    })
                            .setTimeSelectChangeListener(date -> Log.i("pvTime", "onTimeSelectChanged"))
                            .setType(TimePickerType.ALL)
                            .setTitleText("时间选择")
                            .isDialog(true)
                            .setOutSideCancelable(false)
                            .setDate(calendar)
                            .build();
                }
                ddl2TimePickerDialog.show();
            }
        });
        //TODO:获取组长的操作
        teamLeaderOption= new String[]{"张东南", "张西北", "张东北大茬子"};
        teamLeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OptionsPickerView pvOptions = new OptionsPickerBuilder(AddTaskActivity.this, (v, options1, options2, options3) -> {
                    teamLeader.setText(teamLeaderOption[options1]);
                    leaderSelectOption = options1;
                    return false;
                })
                        .setTitleText("选择组长")
                        .setSelectOptions(leaderSelectOption)
                        .build();
                pvOptions.setPicker(teamLeaderOption);
                pvOptions.show();
            }
        });
        //TODO:提交后存入数据库的操作
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=taskName.getText().toString();
                String leader=teamLeader.getText().toString();
                String time1=ddl1.getText().toString();
                String time2=ddl2.getText().toString();
                String notes=note.getText().toString();
                finish();
            }
        });
    }
}
