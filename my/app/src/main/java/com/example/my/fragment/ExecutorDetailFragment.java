package com.example.my.fragment;

import android.content.Intent;
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
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import org.json.JSONObject;
import rxhttp.RxHttp;

public class ExecutorDetailFragment extends Fragment {
    private Task item;
    private static final int REQUEST_CODE_ADD = 1002;
    public static TextView note;

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
        note=view.findViewById(R.id.note);
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
                add();
            }
        });
        Button button1=view.findViewById(R.id.submit);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxHttp.postJson("http://10.0.2.2:8000/api/task/modifytask")
                        .add("task_id",item.getTask_id()).add("status","submitted")
                        .asString()
                        .observeOn(AndroidSchedulers.mainThread()) //指定在主线程回调
                        .subscribe(res -> {
                            JSONObject j= new JSONObject(res);
                            String message =j.getString("message");
                            if(!message.equals("null")){
                                Log.d("TAG", message);
                                showSimpleWarningDialog(message);
                            }else{
                                showSimpleTipDialog("提交成功");
                            }
                        }, throwable -> {
                            showSimpleWarningDialog("网络不良,请重试");
                        });
                //getActivity().finish();
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
                        getActivity().finish();
                    }
                })
                .show();
    }
}
