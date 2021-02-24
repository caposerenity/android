package com.example.my.fragment;
import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.chapter3.demo.R;
import com.example.my.activity.NoteActivity;
import com.example.my.listview.Task;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import org.json.JSONObject;
import rxhttp.RxHttp;

import java.util.ArrayList;
import java.util.List;

public class CheckManagerDetailFragment_1 extends Fragment {
    private Task item;
    private List<String> Result;
    private List<Integer> ResultId;
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
        View view = inflater.inflate(R.layout.fragment_checkmanager1_detail, container, false);
        TextView name = view.findViewById(R.id.TaskName);
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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
        Button submit=view.findViewById(R.id.submit);
        SharedPreferences sharedPre=getActivity().getSharedPreferences("config", getActivity().MODE_PRIVATE);
        int user_id = sharedPre.getInt("user_id",-1);
        String user_name=sharedPre.getString("name","");
        Result= new ArrayList<String>();
        ResultId= new ArrayList<Integer>();
        Result.add(user_name);
        ResultId.add(user_id);
        RxHttp.get("http://10.0.2.2:8000/api/user/Quality_inspector/filterPosition")
                .asList(String.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res->{
                    if(res.size()!=0){
                        //Log.d("TAG", res.toString());
                        //后续处理
                        for (String leaderI : res) {
                            JSONObject j = new JSONObject(leaderI);
                            Result.add(j.getString("name"));
                            ResultId.add(j.getInt("user_id"));
                        }
                    }
                },throwable -> {
                    showSimpleWarningDialog("查询质检员失败，请稍后重试");
                });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), (v, options1, options2, options3) -> {
                    resultSelectOption = options1;
                    RxHttp.postJson("http://10.0.2.2:8000/api/task/modifytask")
                            .add("task_id",item.getTask_id()).add("status","examing").add("quality_inspector",ResultId.get(resultSelectOption))
                            .asString()
                            .observeOn(AndroidSchedulers.mainThread()) //指定在主线程回调
                            .subscribe(res -> {
                                JSONObject j= new JSONObject(res);
                                String message =j.getString("message");
                                if(!message.equals("null")){
                                    Log.d("TAG", message);
                                    showSimpleWarningDialog(message);
                                }else{
                                    showSimpleTipDialog("修改成功");
                                }
                            }, throwable -> {
                                showSimpleWarningDialog("网络不良,请重试");
                            });
                    return false;
                })
                        .setTitleText("分配质检员")
                        .setSelectOptions(resultSelectOption)

                        .build();
                pvOptions.setPicker(Result);
                pvOptions.show();

            }
        });
        return view;
    }
    public static CheckManagerDetailFragment_1 newInstance(Task item) {
        CheckManagerDetailFragment_1 fragmentDemo = new CheckManagerDetailFragment_1();
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
    public void showSimpleWarningDialog(String message) {
        new MaterialDialog.Builder(getContext())
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
        new MaterialDialog.Builder(getContext())
                .iconRes(R.drawable.icon_tip)
                .title("提示")
                .content(message)
                .positiveText("确定")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        //finish();
                    }
                })
                .show();
    }

}
