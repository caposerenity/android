package com.example.my.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter3.demo.R;
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

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_register);
        SuperButton button=findViewById(R.id.btn_register);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialEditText phoneText=findViewById(R.id.et_phone_number);
                String phone=phoneText.getText().toString();
                MaterialEditText nameText=findViewById(R.id.et_user_id);
                name=nameText.getText().toString();
                MaterialEditText pwdText=findViewById(R.id.et_new_code);
                String pwd=pwdText.getText().toString();
                MaterialEditText ValidPwdText=findViewById(R.id.et_confirm_code);
                String ValidPwd=ValidPwdText.getText().toString();
                MaterialSpinner mMaterialSpinner=findViewById(R.id.spinner);
                //获取用户角色。0：系统管理员 1：生产部经理 2：组长 3：质检部经理 4：质检员 5：行政综合部
                mMaterialSpinner.setOnItemSelectedListener((spinner, position, id, item) ->
                {
                    //此处根据position设置角色
                    switch (position) {
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

                });
                SuperButton submit=findViewById(R.id.btn_register);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO:提交申请信息到数据库，并弹出弹框提示待审核，管理员要申请
                        if(!pwd.equals(ValidPwd)){
                            showSimpleWarningDialog("两次密码不一致");
                        }
                        JSONObject json = new JSONObject();
                        try {
                            json.put("name", name);
                            json.put("phone", phone);
                            json.put("password", pwd);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        RxHttp.postJson("http://10.0.2.2:8000/api/user/register")
                                .addAll(String.valueOf(json))
                                .asString()
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
                .show();
    }
    public void showSimpleTipDialog(String message) {
        new MaterialDialog.Builder(this)
                .iconRes(R.drawable.icon_tip)
                .title("提示")
                .content(message)
                .positiveText("确定")
                .show();
    }
}
