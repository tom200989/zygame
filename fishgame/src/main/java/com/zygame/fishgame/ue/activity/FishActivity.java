package com.zygame.fishgame.ue.activity;

import com.hiber.bean.RootProperty;
import com.hiber.hiber.RootMAActivity;
import com.zygame.fishgame.BuildConfig;
import com.zygame.fishgame.R;
import com.zygame.fishgame.ue.frag.Frag_fish;
public class FishActivity extends RootMAActivity {

    private Class[] frags = {Frag_fish.class};

    @Override
    public RootProperty initProperty() {
        RootProperty rootProperty = new RootProperty();
        rootProperty.setColorStatusBar(R.color.common_colorTheme);
        rootProperty.setContainId(R.id.fl_fish_container);
        rootProperty.setFragmentClazzs(frags);
        rootProperty.setFullScreen(true);
        rootProperty.setLayoutId(R.layout.fishgame_activity_fish);
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
