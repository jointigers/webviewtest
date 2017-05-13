package com.example.administrator.webviewtest;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
//import okhttp3.internal.*;


/**
 * Created by Administrator on 2017/5/13.
 */

public class UpdateForce {
    public static int getVerCode(Context context) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(SelfConfig.PNAME, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verCode;
    }
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(SelfConfig.PNAME, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
        }
        return verName;
    }
    public static String getServerVer () {
        try {
            OKhttpself httpRequest = new OKhttpself();

            String httpRes = httpRequest.run(SelfConfig.UPURL);
            if (httpRes != null) {
                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                Matcher m = p.matcher(httpRes);
                httpRes = m.replaceAll("");
                return httpRes;
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
