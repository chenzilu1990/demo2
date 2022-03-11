package com.example.demo;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.AdError;
import com.anythink.interstitial.api.ATInterstitial;
import com.anythink.interstitial.api.ATInterstitialListener;
import com.anythink.rewardvideo.api.ATRewardVideoAd;
import com.anythink.rewardvideo.api.ATRewardVideoListener;
import  android.webkit.JavascriptInterface;

public class MTPADMgr {
    private static final String TAG = "MTPADMgr";
    private static ATRewardVideoAd mTopRewardAD;
    private static ATInterstitial mTopInterstitialAd;
    private static Activity mActivety;

    public  static void initRewardAD(Activity activity){
        loadTopRewardAD(activity);
        mActivety = activity;
    }

    public static void initIntertitalAD(Activity activity){
        loadInterstitialAD(activity);
        mActivety = activity;
    }

    @JavascriptInterface
    public static void showMTPRewardAD(){
        Log.e(TAG, "showTopRewardAD===========");
        if (mTopRewardAD.isAdReady()){
            Log.e(TAG, "isAdReady==========");
            mTopRewardAD.show(mActivety);
        } else {
            Log.e(TAG, "showTopRewardAD==========");
            mTopRewardAD.load();
        }
    }

    @JavascriptInterface
    public static void showMTPInterstitialAD(){
        Log.e(TAG, "showMTPInterstitialAD======111");
        if (mTopInterstitialAd.isAdReady()){
            Log.e(TAG, "showMTPInterstitialAD======1112222");
            mTopInterstitialAd.show(mActivety);
        } else {
            Log.e(TAG, "showMTPInterstitialAD======111222233333");
            mTopInterstitialAd.load();
        }

    }


    private static void loadInterstitialAD(Context context){
        ATInterstitial mInterstitialAd = new ATInterstitial(context, "b622b13ae572cc");
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
            }
            @Override
            public void onInterstitialAdClose(ATAdInfo atAdInfo) {
                //建议在此回调中调用load进行广告的加载，方便下一次广告的展示（不需要调用isAdReady()）
                mInterstitialAd.load();
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


    private static void loadTopRewardAD(Context context) {

        ATRewardVideoAd mRewardVideoAd = new ATRewardVideoAd(context, "b622aafc253d77");
        mTopRewardAD = mRewardVideoAd;
        mRewardVideoAd.setAdListener(new ATRewardVideoListener() {

            @Override
            public void onRewardedVideoAdLoaded() {
                Log.e(TAG, "onTopRewardedVideoAdLoaded:" );
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
            }

            @Override
            public void onReward(ATAdInfo atAdInfo) {
                //建议在此回调中下发奖励，一般在onRewardedVideoAdClosed之前回调
            }

            @Override
            public void onRewardedVideoAdPlayClicked(ATAdInfo atAdInfo) {
            }
        });
        mTopRewardAD.load();
    }
}
