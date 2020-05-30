package com.zygame.zygame.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;


/**
 * Created by qianli.ma on 2017/8/5.
 */

public class HostnameUtils {

    @SuppressLint("StaticFieldLeak")
    private static HostNameImpl hostName;// hostname接口

    /* 设置SSL安全性验证 */
    public static void setVerifyHostName(Context context) {
        HttpsURLConnection.setDefaultHostnameVerifier(getVerify(context));
    }

    /* 获取SSL验证签名 */
    public static HostnameVerifier getVerify(Context context) {
        if (hostName == null) {
            hostName = new HostNameImpl(context);
        }
        return hostName;
    }

    /* 验证SSL类 */
    private static class HostNameImpl implements HostnameVerifier {

        private Context context;

        HostNameImpl(Context context) {
            this.context = context;
        }

        @Override
        public boolean verify(String hostname, SSLSession session) {
            String gateWay = TextUtils.isEmpty(getWIFIGateWay(context)) ? "192.168.1.1" : getWIFIGateWay(context);
            return hostname.contains(gateWay);
        }
    }

    /**
     * 获取WIFI网管
     *
     * @param context 域
     * @return 网关, 如192.168.1.1
     */
    public static String getWIFIGateWay(Context context) {
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcp = wifi.getDhcpInfo();
        StringBuilder builder = new StringBuilder();
        builder.append(String.valueOf((dhcp.gateway & 0xff))).append(".");
        builder.append(String.valueOf(((dhcp.gateway >> 8) & 0xff))).append(".");
        builder.append(String.valueOf(((dhcp.gateway >> 16) & 0xff))).append(".");
        builder.append(String.valueOf(((dhcp.gateway >> 24) & 0xff)));
        return builder.toString();
    }
}
