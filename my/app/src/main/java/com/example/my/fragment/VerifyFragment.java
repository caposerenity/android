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
import com.example.my.listview.User;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import org.json.JSONObject;
import rxhttp.RxHttp;
import com.example.my.utils.roleConvert;

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
                    if(resultSelectOption==1){
                        RxHttp.get("http://192.168.1.106:8000/api/user/"+item.getPhone()+"/drop")
                                .asString()
                                .observeOn(AndroidSchedulers.mainThread()) //指定在主线程回调
                                .subscribe(res -> {
                                    JSONObject j= new JSONObject(res);
                                    String message =j.getString("message");
                                    if(!message.equals("null")){
                                        Log.d("TAG", message);
                                        showSimpleWarningDialog(message);
                                    }else{
                                        showSimpleTipDialog("审核成功");
                                    }
                                }, throwable -> {
                                    showSimpleWarningDialog("网络不良,请重试");
                                });
                    }
                    else{
                        RxHttp.postJson("http://192.168.1.106:8000/api/user/changePos")
                            .add("phone",item.getPhone()).add("newPos",roleConvert.roleCNToEng(item.getRole()))
                            .asString()
                            .observeOn(AndroidSchedulers.mainThread()) //指定在主线程回调
                            .subscribe(res -> {
                                JSONObject j= new JSONObject(res);
                                String message =j.getString("message");
                                if(!message.equals("null")){
                                    Log.d("TAG", message);
                                    showSimpleWarningDialog(message);
                                }else{
                                    showSimpleTipDialog("审核成功");
                                }
                            }, throwable -> {
                                showSimpleWarningDialog("网络不良,请重试");
                            });
                    }
                    return false;
                })
                        .setTitleText("检查结果")
                        .setSelectOptions(resultSelectOption)
                        .build();
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
                        getActivity().finish();
                    }
                })
                .show();
    }
}
