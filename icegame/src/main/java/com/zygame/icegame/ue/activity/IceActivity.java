package com.zygame.icegame.ue.activity;

import com.hiber.bean.RootProperty;
import com.hiber.hiber.RootMAActivity;
import com.zygame.icegame.BuildConfig;
import com.zygame.icegame.R;
import com.zygame.icegame.ue.frag.Frag_ice;

public class IceActivity extends RootMAActivity {

    private Class[] frags = {Frag_ice.class};

    @Override
    public RootProperty initProperty() {
        RootProperty rootProperty = new RootProperty();
        rootProperty.setColorStatusBar(R.color.common_colorTheme);
        rootProperty.setContainId(R.id.fl_ice_container);
        rootProperty.setFragmentClazzs(frags);
        rootProperty.setFullScreen(true);
        rootProperty.setLayoutId(R.layout.icegame_activity_ice);
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
