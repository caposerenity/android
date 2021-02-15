package com.example.my.fragment;



import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.ContextCompat;
import com.example.chapter3.demo.R;



public class SettingFragment extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.preference_setting);
        addPreferencesFromResource(R.xml.preference_setting);
    }


}

