package com.example.demo;

import android.app.Application;
import android.util.Log;

import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.example.demo.TTAdManagerHolder;

public class DemoApplication extends Application {
    public static String PROCESS_NAME_XXXX = "process_name_xxxx";
    public static String TAG = "DemoApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        //强烈建议在应用对应的Application#onCreate()方法中调用，避免出现content为null的异常

        TTAdManagerHolder.init(this );

        //如果明确某个进程不会使用到广告SDK，可以只针对特定进程初始化广告SDK的content
        //if (PROCESS_NAME_XXXX.equals(processName)) {
        //   TTAdSdk.init(context, config);
        //}

        Log.e(TAG, "erewrerewr=======");
    }
}