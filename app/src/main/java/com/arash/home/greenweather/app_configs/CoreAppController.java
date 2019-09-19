package com.arash.home.greenweather.app_configs;

import androidx.multidex.MultiDexApplication;

public class CoreAppController extends MultiDexApplication {

    private static CoreAppController instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }

    public static synchronized CoreAppController getInstance() {
        return instance;
    }
}
