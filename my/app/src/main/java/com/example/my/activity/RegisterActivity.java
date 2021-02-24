package com.example.my.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter3.demo.R;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import org.json.JSONException;
import org.json.JSONObject;
import rxhttp.RxHttp;

import java.util.concurrent.atomic.AtomicInteger;

public class RegisterActivity extends AppCompatActivity {
    private String name;
    private String phone;
    private String pwd;
    private String ValidPwd;
    private SuperButton submit;
    private MaterialSpinner mMaterialSpinner;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_register);
        MaterialEditText phoneText=findViewById(R.id.et_phone_number);
        MaterialEditText nameText=findViewById(R.id.et_user_id);
        MaterialEditText pwdText=findViewById(R.id.et_new_code);
        MaterialEditText ValidPwdText=findViewById(R.id.et_confirm_code);
        mMaterialSpinner=findViewById(R.id.spinner);
        submit=findViewById(R.id.btn_register);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone=phoneText.getText().toString();
                name=nameText.getText().toString();
                pwd=pwdText.getText().toString();
                ValidPwd=ValidPwdText.getText().toString();
                int role=mMaterialSpinner.getSelectedIndex();
                //获取用户角色。0：系统管理员 1：生产部经理 2：组长 3：质检部经理 4：质检员 5：行政综合部
                switch (role) {
                    case 0:
                        name+="-Admin";
                        break;
                    case 1:
                        name+="-Produce_manager";
                        break;
                    case 2:
                        name+="-GroupLeader";
                        break;
                    case 3:
                        name+="-Quality_manager";
                        break;
                    case 4:
                        name+="-Quality_inspector";
                        break;
                    case 5:
                        name+="-Comprehensive_depart";
                        break;
                }
                if(!pwd.equals(ValidPwd)){
                    showSimpleWarningDialog("两次密码不一致");
                }
                else{
                    JSONObject json = new JSONObject();
                    try {
                        json.put("name", name);
                        json.put("phone", phone);
                        json.put("password", pwd);
                        Log.d(phone, "? ");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    RxHttp.postJson("http://192.168.1.106:8000/api/user/register")
                            .addAll(String.valueOf(json))
                            .asString()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(res -> {
                                JSONObject j= new JSONObject(res);
                                String message =j.getString("message");
                                if(!message.equals("null")){
                                    Log.d("TAG", message);
                                    showSimpleWarningDialog(message);
                                }else{
                                    showSimpleTipDialog("请等待管理员审核");
                                    finish();
                                }
                            }, throwable -> {
                                //失败回调
                                showSimpleWarningDialog("注册失败，请重试");
                            });
                }
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
                    }
                })
                .show();
    }
}
