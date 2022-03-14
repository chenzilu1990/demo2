package com.example.demo;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.AdError;
import com.anythink.rewardvideo.api.ATRewardVideoAd;
import com.anythink.rewardvideo.api.ATRewardVideoAutoAd;
import com.anythink.rewardvideo.api.ATRewardVideoAutoLoadListener;
import com.anythink.rewardvideo.api.ATRewardVideoListener;
//import com.anythink.re
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdLoadType;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;

public class WebViewActivity extends AppCompatActivity {
    // 声明
    private WebView wb;
    public static String TAG = "WebViewActivity";
    private boolean mIsLoaded = false;
    private static WebViewActivity app;
    private static TTRewardVideoAd ttRewardVideoAd1;
    private TTAdNative mTTAdNative;
    private static ATRewardVideoAd mTopRewardAD;

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = this;
        setContentView(R.layout.activity_web_view);
        // 获取
        wb = findViewById(R.id.wb);
        // 加载本地 html 文件
//  wb.loadUrl("file:///android_asset/test.html");
        // 加载网页 html 文件
        // 支持 JS 和 DOM
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setDomStorageEnabled(true);
        // 防止所有后打开默认浏览器
        wb.setWebViewClient(new MyWebViewClient());
        wb.setWebChromeClient(new MyWebChromeClient());

        wb.addJavascriptInterface(new MTPADMgr(), "MTPADMgr");

        wb.loadUrl("file:///android_asset/demo.html");

//        wb.loadUrl("https://www.baidu.com/");
        TTAdSdk.getAdManager().requestPermissionIfNecessary(getApplicationContext());
        mTTAdNative = TTAdSdk.getAdManager().createAdNative(this);

        loadRewardAD();
//        MTPADMgr.initIntertitalAD(this);
        MTPADMgr.mRewardActivety = this;
        MTPADMgr.mIntertitalActivety = this;
        MTPADMgr.wb = wb;

//        MTPADMgr.initAutoRewardAD(this);

    }


    private void loadRewardAD() {
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId("948124558")
                .setExpressViewAcceptedSize(500, 500)
                .setOrientation(TTAdConstant.VERTICAL) //必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                .setAdLoadType(TTAdLoadType.LOAD)//推荐使用，用于标注此次的广告请求用途为预加载（当做缓存）还是实时加载，方便后续为开发者优化相关策略
                .build();
        mTTAdNative.loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {
            @Override
            public void onError(int code, String message) {
                Log.e(TAG, "Callback --> onError: " + code + ", " + String.valueOf(message));
            }

            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ttRewardVideoAd) {

            }

            @Override
            public void onRewardVideoCached() {
                Log.e(TAG, "Callback --> onRewardVideoCached");
                mIsLoaded = true;
            }

            @Override
            public void onRewardVideoCached(TTRewardVideoAd ttRewardVideoAd) {
                Log.e(TAG, "Callback --> onRewardVideoCached");
                mIsLoaded = true;
                ttRewardVideoAd1 = ttRewardVideoAd;
//                ttRewardVideoAd.showRewardVideoAd(WebViewActivity.this, TTAdConstant.RitScenes.CUSTOMIZE_SCENES, "scenes_test");

            }
        });
    }


    public static void showAD() {

        app.runOnUiThread(new Runnable() {
            public void run() {
                Log.e(TAG, "showAD====");
//                app.loadRewardAD();
                ttRewardVideoAd1.showRewardVideoAd(WebViewActivity.app, TTAdConstant.RitScenes.CUSTOMIZE_SCENES, "scenes_test");
            }
        });

    }




    /**
     * 防止返回到之前的 Activity
     *
     * @param keyCode 按键
     * @param event   事件
     * @return true
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && wb.canGoBack()) {
            wb.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    static class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.d("WebViewClient", "Page started...");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.d("WebViewClient", "Page Finished...");
        }
    }

    /**
     * 添加所有记录和 Title
     */
    class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            setTitle(title);
        }
    }
}
