package com.example.my.activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import com.example.chapter3.demo.R;
import com.example.my.utils.XToastUtils;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;

import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import org.json.JSONObject;
import rxhttp.RxHttp;

public class SettingAcitivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_setting);
        SharedPreferences sharedPre=getSharedPreferences("config",MODE_PRIVATE);

        EditTextPreference name_pref=(EditTextPreference)findPreference("example_text");
        name_pref.setSummary(sharedPre.getString("name",""));
        name_pref.setText(sharedPre.getString("name",""));
        name_pref.setPositiveButtonText("确定");name_pref.setNegativeButtonText("取消");
        name_pref.setOnPreferenceClickListener(preference -> {

            return false;
        });

        Preference pref = findPreference("modify_password");
        pref.setOnPreferenceClickListener(preference -> {
            showCustomDialog();
            return false;
        });
    }

    private void showCustomDialog() {
        new MaterialDialog.Builder(this)
                .customView(R.layout.dialog_custom, true)
                .title("修改密码")
                .positiveText("确定")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        SharedPreferences sharedPre=getSharedPreferences("config",MODE_PRIVATE);

                        MaterialEditText oldPwdText=dialog.getCustomView().findViewById(R.id.et_now_code);
                        String nowPwd=oldPwdText.getText().toString();
                        MaterialEditText pwdText=dialog.getCustomView().findViewById(R.id.et_new_code_modify);
                        String pwd=pwdText.getText().toString();
                        MaterialEditText confirmPwdText=dialog.getCustomView().findViewById(R.id.et_confirm_code_modify);
                        String confirmPwd=confirmPwdText.getText().toString();

                        int user_id = sharedPre.getInt("user_id",-1);

                        if(!pwd.equals(confirmPwd)){
                            showSimpleWarningDialog("两次密码不一致");
                        }else{
                        RxHttp.postJson("http://10.0.2.2:8000/api/user/changePassword")
                                .add("user_id",user_id)
                                .add("password",nowPwd)
                                .add("newPassword",pwd)
                                .asString()
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(res -> {
                                    JSONObject j= new JSONObject(res);
                                    String message =j.getString("message");
                                    if(!message.equals("null") &&!message.equals("修改成功")){
                                        Log.d("TAG", message);
                                        showSimpleWarningDialog(message);
                                    }else{
                                        showSimpleTipDialog("修改成功");
                                    }
                                }, throwable -> {
                                    showSimpleWarningDialog("请重试");
                                });
                        }
                    }
                })
                .show();

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
