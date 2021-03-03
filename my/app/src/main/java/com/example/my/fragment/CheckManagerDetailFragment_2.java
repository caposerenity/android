package com.example.my.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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
import com.example.my.utils.XToastUtils;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;

import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rxhttp.RxHttp;

import static com.xuexiang.xutil.XUtil.runOnUiThread;

public class CheckManagerDetailFragment_2 extends Fragment {
    private Task item;
    private static final int REQUEST_CODE_ADD = 1002;
    private TextView note;

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
        note=view.findViewById(R.id.note);
        TextView maketime=view.findViewById(R.id.makeTime);
        TextView finish1=view.findViewById(R.id.finish1);
        name.append(item.getTask_name());
        ddl2.append(item.getExpected_exam_time());
        note.append(item.getComments());
        maketime.append(item.getCreate_time());
        finish1.append(item.getFinish_time());
        Button button=view.findViewById(R.id.add_button);
        if(item.getQuality_inspector()!=null&& !item.getQuality_inspector().equals("null")&& !item.getQuality_inspector().equals("")) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("http://3s784625n5.qicp.vip:80/api/user/" + item.getQuality_inspector() + "/getNameById").build();
                    try {
                        Response response = client.newCall(request).execute();//发送请求
                        String result = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                checkman.append(result);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
        if(item.getGroup_leader()!=null&& !item.getGroup_leader().equals("null")&& !item.getGroup_leader().equals("")) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("http://3s784625n5.qicp.vip:80/api/user/" + item.getGroup_leader() + "/getNameById").build();
                    try {
                        Response response = client.newCall(request).execute();//发送请求
                        String result = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tl.append(result);
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
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
        showInputDialog();
    }
    private void showInputDialog() {
        new MaterialDialog.Builder(getContext())
                .iconRes(R.drawable.icon_tip)
                .title("修改备注")
                .inputType(
                        InputType.TYPE_CLASS_TEXT)
                .input(
                        "",
                        item.getComments(),
                        false,
                        ((dialog, input) -> XToastUtils.toast(input.toString())))
                .positiveText("确定")
                .negativeText("取消")
                .onPositive(
                        (dialog, which) -> {
                            String content=dialog.getInputEditText().getText().toString();
                            String id=item.getTask_id();
                            RxHttp.postJson("http://3s784625n5.qicp.vip:80/api/task/modifytask")
                                    .add("task_id",id).add("comments",content)
                                    .asString()
                                    .observeOn(AndroidSchedulers.mainThread()) //指定在主线程回调
                                    .subscribe(res -> {
                                        JSONObject j= new JSONObject(res);
                                        String message =j.getString("message");
                                        if(!message.equals("null")){
                                            Log.d("TAG", message);
                                            showSimpleWarningDialog(message);
                                        }else{
                                            note.setText(content);
                                            showSimpleTipDialog("修改成功");
                                        }
                                    }, throwable -> {
                                        showSimpleWarningDialog("网络不良,请重试");
                                    });
                        }
                )
                .cancelable(false)
                .show();
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
                        getActivity().finish();
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
                    }
                })
                .show();
    }
}
