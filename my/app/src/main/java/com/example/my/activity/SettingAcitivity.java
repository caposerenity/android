package com.example.my.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.example.chapter3.demo.R;

public class SettingAcitivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.preference_setting);
        addPreferencesFromResource(R.xml.preference_setting);
    }


}
