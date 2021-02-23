package com.example.my.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter3.demo.R;
import com.xuexiang.xui.widget.button.ButtonView;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import org.json.JSONObject;
import rxhttp.RxHttp;
import com.example.my.utils.roleConvert;
public class LoginActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_login);
        SuperButton button=findViewById(R.id.btn_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //模拟用户角色0：系统管理员 1：生产部经理 2：组长 3：质检部经理 4：质检员 5：行政综合部
                MaterialEditText phoneText=findViewById(R.id.et_phone_number);
                String phone=phoneText.getText().toString();
                MaterialEditText pwdText=findViewById(R.id.et_password);
                String pwd=pwdText.getText().toString();

                RxHttp.postJson("http://10.0.2.2:8000/api/user/login")
                        .add("phone",phone)
                        .add("password",pwd)
                        .asString()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(res -> {
                            JSONObject j= new JSONObject(res);
                            String message =j.getString("message");

                            if(!message.equals("null")){
                                Log.d("TAG", message);
                                showSimpleWarningDialog(message);
                            }else{
                                String content=j.getString("content");
                                JSONObject c= new JSONObject(content);
                                String user_name=c.getString("name");
                                String position=c.getString("position");
                                int user_id=c.getInt("user_id");

                                SharedPreferences sharedPre=getSharedPreferences("config", MODE_PRIVATE);
                                //获取Editor对象
                                SharedPreferences.Editor edit = sharedPre.edit();
                                //设置参数
                                edit.putInt("user_id",user_id);
                                edit.putString("phone",phone);
                                edit.putString("password",pwd);
                                edit.putString("name",user_name);
                                edit.putString("position",position);
                                //提交
                                edit.commit();
                                login(roleConvert.roleStrToNum(position),user_name);
                            }
                        }, throwable -> {
                            //失败回调
                            showSimpleWarningDialog("登录失败，请重试");
                        });

            }
        });
        ButtonView button2=findViewById(R.id.btn_register);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册界面
                register();
            }
        });
    }
    private void login(int role,String name){
        Intent i=new Intent(this,MainActivity.class);
        i.putExtra("role",role);
        i.putExtra("name",name);

        startActivity(i);
    }
    private void register(){
        Intent i=new Intent(this,RegisterActivity.class);
        startActivity(i);
    }
    private void showSimpleWarningDialog(String message) {
        new MaterialDialog.Builder(this)
                .iconRes(R.drawable.icon_warning)
                .title("提示")
                .content(message)
                .positiveText("确定")
                .show();
    }

}
