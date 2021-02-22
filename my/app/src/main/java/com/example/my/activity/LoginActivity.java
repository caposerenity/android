package com.example.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter3.demo.R;
import com.xuexiang.xui.widget.button.ButtonView;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;

public class LoginActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_login);
        SuperButton button=findViewById(R.id.btn_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:进行账户信息认证，保存到临时存储当中，获取用户角色和姓名，然后进行登录。
                //模拟用户角色0：系统管理员 1：生产部经理 2：组长 3：质检部经理 4：质检员 5：行政综合部
                MaterialEditText phoneText=findViewById(R.id.et_phone_number);
                String phone=phoneText.getText().toString();
                MaterialEditText pwdText=findViewById(R.id.et_password);
                String pwd=pwdText.getText().toString();
                login(0,"张东南");
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
}