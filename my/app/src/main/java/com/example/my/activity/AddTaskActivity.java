package com.example.my.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter3.demo.R;
import com.example.my.utils.XToastUtils;
import com.xuexiang.xui.widget.button.ButtonView;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.TimePickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;
import com.xuexiang.xui.widget.picker.widget.builder.TimePickerBuilder;
import com.xuexiang.xui.widget.picker.widget.configure.TimePickerType;
import com.xuexiang.xui.widget.picker.widget.listener.OnTimeSelectListener;
import com.xuexiang.xutil.data.DateUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http.RealResponseBody;
import org.json.JSONObject;
import rxhttp.RxHttp;

import static com.xuexiang.xui.XUI.getContext;

public class AddTaskActivity extends AppCompatActivity {
    EditText taskName;
    ButtonView teamLeader;
    ButtonView ddl1;
    ButtonView ddl2;
    TextView note;
    Button submit;
    private int leaderSelectOption = 0;
    private TimePickerView ddl1TimePickerDialog;
    private TimePickerView ddl2TimePickerDialog;
    private List<String> teamLeaderOption=new ArrayList<>();
    private List<Integer> teamLeaderId =new ArrayList<>();
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
        teamLeader.setText("单击选择组长");
        ddl1.setText("2020-02-28 12:00:00");
        ddl2.setText("2020-02-29 12:00:00");

        ddl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ddl1TimePickerDialog == null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(DateUtils.getNowDate());
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
            @Override
            public void onClick(View view) {
                if (ddl2TimePickerDialog == null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(DateUtils.getNowDate());
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


        teamLeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxHttp.get("http://3s784625n5.qicp.vip:80/api/user/GroupLeader/filterPosition")
                        .asList(String.class)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(res->{
                            if(res.size()!=0){

                                Log.d("TAG", res.toString());
                                //后续处理
                                for (int i=0;i<res.size();i++){
                                    String leaderI=res.get(i);
                                    JSONObject j= new JSONObject(leaderI);
                                    teamLeaderOption.add(j.getString("name"));
                                    teamLeaderId.add(j.getInt("user_id"));
                                }

                                OptionsPickerView pvOptions = new OptionsPickerBuilder(AddTaskActivity.this, (v, options1, options2, options3) -> {
                                    teamLeader.setText(teamLeaderOption.get(options1));
                                    leaderSelectOption = options1;
                                    return false;
                                })
                                        .setTitleText("选择组长")
                                        .setSelectOptions(leaderSelectOption)
                                        .build();
                                pvOptions.setPicker(teamLeaderOption);
                                pvOptions.show();
                            }else{
                                showSimpleWarningDialog("未查询到组长");
                            }
                        },throwable -> {
                            showSimpleWarningDialog("网络错误,请重试");
                        });
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=taskName.getText().toString();
                Integer leader=null;
                if(teamLeaderId.size()!=0){
                    leader=teamLeaderId.get(teamLeaderOption.indexOf(teamLeader.getText().toString()));
                }
                teamLeaderId.clear();
                teamLeaderOption.clear();
                String time1 = ddl1.getText().toString();
                String time2=ddl2.getText().toString();
                String notes=note.getText().toString();

                RxHttp.postJson("http://3s784625n5.qicp.vip:80/api/task/addtask")
                        .add("task_name",name).add("expected_time",time1).add("expected_exam_time",time2)
                        .add("group_leader",leader).add("comments",notes)
                        .asString()
                        .observeOn(AndroidSchedulers.mainThread()) //指定在主线程回调
                        .subscribe(res -> {
                            JSONObject j= new JSONObject(res);
                            String message =j.getString("message");
                            if(!message.equals("null")){
                                Log.d("TAG", message);
                                showSimpleWarningDialog(message);
                            }
                            else{
                                showSimpleTipDialog("添加成功");
                            }
                        }, throwable -> {
                            showSimpleWarningDialog("网络不良,请重试");
                        });


            }
        });
    }

    public void showSimpleWarningDialog(String message) {
        new MaterialDialog.Builder(this)
                .iconRes(R.drawable.icon_warning)
                .title("提示")
                .content(message)
                .positiveText("确定")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
    public void showSimpleTipDialog(String message) {
        new MaterialDialog.Builder(this)
                .iconRes(R.drawable.icon_tip)
                .title("提示")
                .content(message)
                .positiveText("确定")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .show();
    }
}
