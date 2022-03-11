package com.example.demo;

import android.app.Application;
import android.os.Build;
import android.util.Log;
import android.webkit.WebView;

import com.anythink.core.api.ATSDK;
import com.anythink.core.api.DeviceInfoCallback;
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            String processName = getProcessName();
            if (!getPackageName().equals(processName)) {
                WebView.setDataDirectorySuffix(processName);
            }
        }

        TTAdManagerHolder.init(this );

        ATSDK.setNetworkLogDebug(true);//SDK日志功能，集成测试阶段建议开启，上线前必须关闭

        Log.i(TAG, "TopOn SDK version: " + ATSDK.getSDKVersionName());//SDK版本

        ATSDK.integrationChecking(getApplicationContext());//检查广告平台的集成状态
//(v5.7.77新增) 打印当前设备的设备信息(IMEI、OAID、GAID、AndroidID等)
        ATSDK.init(getApplicationContext(), "a622aaf6ed9273", "864147faa02fced424564fc2f3b7a915");//初始化SDK
        ATSDK.testModeDeviceInfo(this, new DeviceInfoCallback() {
            @Override
            public void deviceInfo(String deviceInfo) {
                Log.i(TAG, "deviceInfo: " + deviceInfo);
            }
        });

        //如果明确某个进程不会使用到广告SDK，可以只针对特定进程初始化广告SDK的content
        //if (PROCESS_NAME_XXXX.equals(processName)) {
        //   TTAdSdk.init(context, config);
        //}

        Log.e(TAG, "erewrerewr=======");
    }
}