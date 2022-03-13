package com.example.demo;
import android.util.Log;
import  android.webkit.JavascriptInterface;
//import com.example.demo.WebViewActivity;
public class anjs extends Object {
    private static String TAG = "anjs";
    @JavascriptInterface
    public void hello(String msg) {
        System.out.println(msg);

    }

    @JavascriptInterface
    public void showMTPRewardAD(){
        Log.e(TAG, "showMTPRewardAD==========");

    }


}
