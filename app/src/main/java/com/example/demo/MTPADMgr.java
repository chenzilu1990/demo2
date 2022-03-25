package com.example.demo;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.AdError;
import com.anythink.interstitial.api.ATInterstitial;
import com.anythink.interstitial.api.ATInterstitialListener;
import com.anythink.rewardvideo.api.ATRewardVideoAd;
import com.anythink.rewardvideo.api.ATRewardVideoAutoAd;
import com.anythink.rewardvideo.api.ATRewardVideoAutoEventListener;
import com.anythink.rewardvideo.api.ATRewardVideoAutoLoadListener;
import com.anythink.rewardvideo.api.ATRewardVideoListener;
import com.kwad.sdk.core.imageloader.utils.L;

import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import java.util.function.Function;

public class MTPADMgr {
    private static final String TAG = "MTPADMgr";
    private static ATRewardVideoAd mTopRewardAD;
    private static ATInterstitial mTopInterstitialAd;
    public static Activity mRewardActivety;
    public static Activity mIntertitalActivety;
    //    private static String mRewardID = "b622aafc253d77";
    private static String mRewardID = "";
    //    private static String mInterstitalID = "b622b13ae572cc";
    private static String mInterstitalID = "";
    //    private static  mOnReward;
    public static WebView wb;

    private static Boolean isReward = false;

    @JavascriptInterface
    public static void initMTPRewardAD(String adCode) {
        mRewardID = adCode;
        loadRewardAD();
    }

    @JavascriptInterface
    public static void initMTPIntertitalAD(String adCode) {
        mInterstitalID = adCode;
        loadInterstitialAD();
    }

    @JavascriptInterface
    public static void initMTPAutoRewardAD(String adCode) {
        mRewardID = adCode;
        loadAutoRewardAD();
    }


    @JavascriptInterface
    public static void showMTPRewardAD() {
        Log.e(TAG, "showTopRewardAD===========");
        if (mTopRewardAD.isAdReady()) {
            Log.e(TAG, "isAdReady==========");
            mTopRewardAD.show(mRewardActivety);
        } else {
            Log.e(TAG, "showTopRewardAD==========");
            mTopRewardAD.load();
        }
    }


//    @JavascriptInterface
    public static void onMTPRewardADClose(Boolean isReward) {
        Log.e(TAG, "onMTPRewardADClose===" + isReward);
        if (isReward){
            wb.evaluateJavascript("demo:onRewardADClose(true)", new ValueCallback(){
                @Override
                public void onReceiveValue(Object o) {
                    Log.e(TAG, "onRewardADRewardEnd====");
                }
            });
        } else {
            wb.evaluateJavascript("demo:onRewardADClose(false)", new ValueCallback(){
                @Override
                public void onReceiveValue(Object o) {
                    Log.e(TAG, "onRewardADRewardEnd====");
                }
            });

        }
    }




    @JavascriptInterface
    public static void showMTPInterstitialAD() {
        Log.e(TAG, "showMTPInterstitialAD======111");
        if (mTopInterstitialAd.isAdReady()) {
            Log.e(TAG, "showMTPInterstitialAD======1112222");
            mTopInterstitialAd.show(mIntertitalActivety);
        } else {
            Log.e(TAG, "showMTPInterstitialAD======111222233333");
            mTopInterstitialAd.load();
        }

    }


    @JavascriptInterface
    public static void showMTPAutoRewardAD() {
        //需判断广告位是否准备好
        Log.e(TAG, "showMTPAutoRewardAD======");
        if (ATRewardVideoAutoAd.isAdReady(mRewardID)) {
            Log.e(TAG, "showMTPAutoRewardAD isAdReady======");
            ATRewardVideoAutoAd.show(mRewardActivety, mRewardID, new ATRewardVideoAutoEventListener() {
                @Override
                public void onRewardedVideoAdPlayStart(ATAdInfo atAdInfo) {
                    Log.e(TAG, "onRewardedVideoAdPlayStart====");
                }

                @Override
                public void onRewardedVideoAdPlayEnd(ATAdInfo atAdInfo) {
                    Log.e(TAG, "onRewardedVideoAdPlayEnd=========");
                }

                @Override
                public void onRewardedVideoAdPlayFailed(AdError adError, ATAdInfo atAdInfo) {
                    Log.e(TAG, "onRewardedVideoAdPlayFailed=======");
                }

                @Override
                public void onRewardedVideoAdClosed(ATAdInfo atAdInfo) {
                    Log.e(TAG, "onRewardedVideoAdClose==========");
                }

                @Override
                public void onRewardedVideoAdPlayClicked(ATAdInfo atAdInfo) {
                    Log.e(TAG, "onRewardedVideoAdPlayClicked======");
                }

                @Override
                public void onReward(ATAdInfo atAdInfo) {
                    Log.e(TAG, "onReward=======");
                }
            });
        }
    }

    private static void loadInterstitialAD() {
        Log.e(TAG, "loadInterstitialAD===========ing");
        ATInterstitial mInterstitialAd = new ATInterstitial(mIntertitalActivety, mInterstitalID);
        mTopInterstitialAd = mInterstitialAd;
        mInterstitialAd.setAdListener(new ATInterstitialListener() {
            @Override
            public void onInterstitialAdLoaded() {
                Log.e(TAG, "onInterstitialAdLoaded");
            }

            @Override
            public void onInterstitialAdLoadFail(AdError adError) {
                //注意：禁止在此回调中执行广告的加载方法进行重试，否则会引起很多无用请求且可能会导致应用卡顿
                //AdError，请参考 https://docs.toponad.com/#/zh-cn/android/android_doc/android_test?id=aderror
                Log.e(TAG, "onInterstitialAdLoadFail:" + adError.getFullErrorInfo());
            }

            @Override
            public void onInterstitialAdClicked(ATAdInfo atAdInfo) {
            }

            @Override
            public void onInterstitialAdShow(ATAdInfo atAdInfo) {
                //ATAdInfo可区分广告平台以及获取广告平台的广告位ID等
                //请参考 https://docs.toponad.com/#/zh-cn/android/android_doc/android_sdk_callback_access?id=callback_info
                mInterstitialAd.load();
            }

            @Override
            public void onInterstitialAdClose(ATAdInfo atAdInfo) {
                //建议在此回调中调用load进行广告的加载，方便下一次广告的展示（不需要调用isAdReady()）
//                mInterstitialAd.load();
            }

            @Override
            public void onInterstitialAdVideoStart(ATAdInfo atAdInfo) {
            }

            @Override
            public void onInterstitialAdVideoEnd(ATAdInfo atAdInfo) {
            }

            @Override
            public void onInterstitialAdVideoError(AdError adError) {
                //AdError，请参考 https://docs.toponad.com/#/zh-cn/android/android_doc/android_test?id=aderror
                Log.e(TAG, "onInterstitialAdVideoError:" + adError.getFullErrorInfo());
            }
        });
        mInterstitialAd.load();
    }


    private static void loadRewardAD() {
        Log.e(TAG, "loadRewardAD=========ing");
        ATRewardVideoAd mRewardVideoAd = new ATRewardVideoAd(mRewardActivety, mRewardID);
        mTopRewardAD = mRewardVideoAd;
        mRewardVideoAd.setAdListener(new ATRewardVideoListener() {

            @Override
            public void onRewardedVideoAdLoaded() {
                Log.e(TAG, "onTopRewardedVideoAdLoaded:");
            }

            @Override
            public void onRewardedVideoAdFailed(AdError adError) {
                //AdError，请参考 https://docs.toponad.com/#/zh-cn/android/android_doc/android_test?id=aderror
                Log.e(TAG, "onRewardedVideoAdFailed:" + adError.getFullErrorInfo());
            }

            @Override
            public void onRewardedVideoAdPlayStart(ATAdInfo atAdInfo) {
                //ATAdInfo可区分广告平台以及获取广告平台的广告位ID等
                //请参考 https://docs.toponad.com/#/zh-cn/android/android_doc/android_sdk_callback_access?id=callback_info
                isReward = false;
                mRewardVideoAd.load();
            }

            @Override
            public void onRewardedVideoAdPlayEnd(ATAdInfo atAdInfo) {
            }

            @Override
            public void onRewardedVideoAdPlayFailed(AdError adError, ATAdInfo atAdInfo) {
                //注意：禁止在此回调中执行广告的加载方法进行重试，否则会引起很多无用请求且可能会导致应用卡顿
                //AdError，请参考 https://docs.toponad.com/#/zh-cn/android/android_doc/android_test?id=aderror
                Log.e(TAG, "onRewardedVideoAdPlayFailed:" + adError.getFullErrorInfo());
            }

            @Override
            public void onRewardedVideoAdClosed(ATAdInfo atAdInfo) {
                //建议在此回调中调用load进行广告的加载，方便下一次广告的展示（不需要调用isAdReady()）
//                mRewardVideoAd.load();
                onMTPRewardADClose(isReward);

            }

            @Override
            public void onReward(ATAdInfo atAdInfo) {
                //建议在此回调中下发奖励，一般在onRewardedVideoAdClosed之前回调
                isReward = true;
            }

            @Override
            public void onRewardedVideoAdPlayClicked(ATAdInfo atAdInfo) {
            }
        });
        mTopRewardAD.load();
    }

    private static void loadAutoRewardAD() {
        Log.e(TAG, "loadAutoRewardAD======ing");
        ATRewardVideoAutoAd.init(mRewardActivety, new String[]{mRewardID}, new ATRewardVideoAutoLoadListener() {

            @Override
            public void onRewardVideoAutoLoaded(String s) {
                Log.e(TAG, "onRewardVideoAutoLoaded====");
            }

            @Override
            public void onRewardVideoAutoLoadFail(String s, AdError adError) {
                Log.e(TAG, "onRewardVideoAutoLoadFail=====");
            }
        });
    }

}
