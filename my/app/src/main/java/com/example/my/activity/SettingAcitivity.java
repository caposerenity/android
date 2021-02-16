package com.example.my.activity;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import com.example.chapter3.demo.R;
import com.example.my.utils.XToastUtils;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

public class SettingAcitivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.preference_setting);
        addPreferencesFromResource(R.xml.preference_setting);

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
                .show();

    }


}
