package com.zygame.zygame.ue.activity;

import com.hiber.bean.RootProperty;
import com.hiber.hiber.RootMAActivity;
import com.zygame.zygame.BuildConfig;
import com.zygame.zygame.R;
import com.zygame.zygame.ue.frag.Frag_main;
import com.zygame.zygame.ue.frag.Frag_setting;
import com.zygame.zygame.ue.frag.Frag_splash;

public class SplashActivity extends RootMAActivity {

    private Class[] frags = {// 
            // Frag_astart.class, //调试页
            Frag_main.class, // 主页
            Frag_splash.class,// 启动页
            Frag_setting.class,// 设置页
    };

    @Override
    public RootProperty initProperty() {
        RootProperty rootProperty = new RootProperty();
        rootProperty.setColorStatusBar(R.color.common_colorTheme);
        rootProperty.setContainId(R.id.fl_app_container);
        rootProperty.setFragmentClazzs(frags);
        rootProperty.setFullScreen(true);
        rootProperty.setLayoutId(R.layout.activity_splash);
        rootProperty.setSaveInstanceState(false);
        rootProperty.setPackageName(BuildConfig.APPLICATION_ID);
        return rootProperty;
    }

    @Override
    public void onNexts() {

    }

    @Override
    public boolean onBackClick() {
        return false;
    }
}
