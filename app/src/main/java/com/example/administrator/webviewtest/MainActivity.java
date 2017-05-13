package com.example.administrator.webviewtest;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
//sdfsdf
public class MainActivity extends Activity {
    public static final String LOAD_URL = "http://www.baidu.com/";
    private WebView webView = null;
    private String serverVersion;
    private Selfhandler handler = new Selfhandler();
//    /**
//     * 配制发生变化，这里处理屏幕旋转的事件
//     * @param newConfig
//     */
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        String message=newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE ? "屏幕设置为：横屏" : "屏幕设置为：竖屏";
//        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
//    }
    @JavascriptInterface
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FlurryAgent.Builder().withLogEnabled(true).build(this, SelfConfig.FlurryKEY);
        webView = new WebView(this);
        this.setContentView(webView);
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {

            }
        });
        new Thread() {
            public void run() {
                Looper.prepare();
                Message msg = handler.obtainMessage();
                serverVersion = UpdateForce.getServerVer();
                String localVersion = UpdateForce.getVerName(getApplicationContext());
                if (serverVersion != null && !"".equals(serverVersion) &&!serverVersion.equals(localVersion) ) {
                        msg.what = SelfConfig.UPDATEINT;
                        handler.sendEmptyMessage(msg.what);
                }
                Looper.loop();
            }
        }.start();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(this, "native");
        webView.loadUrl(LOAD_URL);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class Selfhandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Context activityNotif = getApplicationContext();
            if (activityNotif != null) {
                switch (msg.what) {
                    case SelfConfig.UPDATEINT:
                        finish();
                        break;
                }
            }
        }
    }
}