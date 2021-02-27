package com.example.my.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.example.chapter3.demo.R;
import com.xuexiang.xui.widget.button.ButtonView;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import org.json.JSONObject;
import rxhttp.RxHttp;
import com.example.my.utils.roleConvert;

import static com.xuexiang.xutil.XUtil.getContext;

public class LoginActivity extends AppCompatActivity{
    private String createNotificationChannel(String channelID, String channelNAME, int level) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) getContext().getSystemService(getContext().NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(channelID, channelNAME, level);
            manager.createNotificationChannel(channel);
            return channelID;
        } else {
            return null;
        }
    }

    private void myNotify(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        String channelId = createNotificationChannel("my_channel_ID", "my_channel_NAME", NotificationManager.IMPORTANCE_HIGH);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, channelId)
                .setContentTitle("通知")
                .setContentText("请联系管理员")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(100, notification.build());
    }
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_login);

        ButtonView forget=findViewById(R.id.tv_forget_password);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myNotify();
            }
        });

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

                                login(roleConvert.roleStrToNum(position),user_id+"");
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
    private void login(int role,String id){
        Intent i=new Intent(this,MainActivity.class);
        i.putExtra("role",role);
        i.putExtra("id",id);

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
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

}
