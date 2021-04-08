package com.example.shorttest.app;

import android.app.Application;

import com.example.shorttest.MainActivity;
import com.example.shorttest.utlis.ShortCutUtil;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 创建快捷方式
         */
        ShortCutUtil.getInstance()
                .init(this)
                .setShortCuts(this, MainActivity.class);
    }
}
