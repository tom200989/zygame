package com.zygame.zygame.app;

import android.text.TextUtils;

import com.hiber.hiber.language.LangHelper;
import com.hiber.hiber.language.RootApp;
import com.hiber.tools.Lgg;
import com.hiber.tools.ShareUtils;
import com.zygame.common.component.RootComponent;
import com.zygame.zygame.utils.FontsOverride;
import com.zygame.zygame.utils.HostnameUtils;
import com.zygame.zygame.utils.RootCons;

import org.xutils.x;

import java.util.Locale;

/*
 * Created by qianli.ma on 2020/5/6 0006.
 */
public class ZYApp extends RootApp {
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化网络
        x.Ext.init(this);
        // 初始化缓存器
        ShareUtils.spName = RootComponent.SP_NAME;
        ShareUtils.init(this);
        // 初始化语言
        cacheLanguage();
        // 设置google请求认证
        HostnameUtils.setVerifyHostName(this);
        // 初始化日志开关 - 默认开
        Lgg.openOrClose(false);
        // 设置字体
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/lovely.ttc");
    }

    /**
     * 把需要展示的语言保存到缓存
     * 在[语言选择页]再取出来显示
     */
    private void cacheLanguage() {
        Locale locale = LangHelper.getLocale(getApplicationContext());
        String language = locale.getLanguage();
        String country = locale.getCountry();
        // 如果没有指定地区, 则格式为: es-default, 否则es-MX (一般情况下即便是从系统取, 系统会自动带地区, 这里仅仅为防止其他情况)
        ShareUtils.set(RootCons.LOCALE_LANGUAGE_COUNTRY, language + "-" + (TextUtils.isEmpty(country) ? "default" : country));
    }
}
