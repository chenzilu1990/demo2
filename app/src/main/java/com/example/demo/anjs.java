package com.example.demo;
import  android.webkit.JavascriptInterface;
import com.example.demo.WebViewActivity;
public class anjs extends Object {
    @JavascriptInterface
    public void hello(String msg) {
        System.out.println(msg);
        WebViewActivity.showAD();
    }
}
