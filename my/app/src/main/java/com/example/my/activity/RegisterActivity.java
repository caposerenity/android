package com.example.my.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter3.demo.R;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;

import java.util.concurrent.atomic.AtomicInteger;

public class RegisterActivity extends AppCompatActivity {
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
                String name=nameText.getText().toString();
                MaterialEditText pwdText=findViewById(R.id.et_new_code);
                String pwd=pwdText.getText().toString();
                MaterialSpinner mMaterialSpinner=findViewById(R.id.spinner);
                //获取用户角色。0：系统管理员 1：生产部经理 2：组长 3：质检部经理 4：质检员 5：行政综合部
                mMaterialSpinner.setOnItemSelectedListener((spinner, position, id, item) ->
                {
                    //此处根据position设置角色
                });
                SuperButton submit=findViewById(R.id.btn_register);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO:提交申请信息到数据库，并弹出弹框提示待审核，管理员要申请
                        finish();
                    }
                });
            }
        });
    }
}
