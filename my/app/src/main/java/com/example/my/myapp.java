package com.example.my;

import android.app.Application;
import com.xuexiang.xui.XUI;

public class myapp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        XUI.init(this);
        XUI.debug(true);
    }
}
