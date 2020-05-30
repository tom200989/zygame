package com.zygame.zygame.ue.frag;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.hiber.hiber.RootFrag;
import com.hiber.tools.ShareUtils;
import com.zygame.common.component.RootComponent;
import com.zygame.zygame.R;

import butterknife.BindView;

/*
 * Created by Administrator on 2020/5/30.
 */
public class Frag_setting extends RootFrag {

    @BindView(R.id.iv_setting_exit)
    ImageView ivSettingExit;// 回退
    @BindView(R.id.ed_setting_play)
    EditText edSettingPlay;// 玩耍时长
    @BindView(R.id.ed_setting_rest)
    EditText edSettingRest;// 休息时长

    @Override
    public int onInflateLayout() {
        return R.layout.frag_setting;
    }

    @Override
    public void initViewFinish(View inflateView) {
        initView();// 初始化视图
        initEvent();// 初始化事件
    }

    /**
     * 初始化视图
     */
    private void initView() {
        long playDuration_default = Long.parseLong(ShareUtils.get(RootComponent.SETTING_PLAY_TIME, String.valueOf(RootComponent.SETTING_DEFAULT_PLAY_DURATION)));
        long restDuration_default = Long.parseLong(ShareUtils.get(RootComponent.SETTING_REST_TIME, String.valueOf(RootComponent.SETTING_DEFAULT_REST_DURATION)));
        edSettingPlay.setText(String.valueOf(playDuration_default / 60 / 1000));
        edSettingRest.setText(String.valueOf(restDuration_default / 60 / 1000));
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        ivSettingExit.setOnClickListener(v -> onBackPresss());
    }

    @Override
    public void onNexts(Object o, View view, String s) {

    }

    @Override
    public boolean onBackPresss() {// 记录设置的时间并退出
        int playDuration = Integer.parseInt(edSettingPlay.getText().toString());// 获取玩耍时长
        int restDuration = Integer.parseInt(edSettingRest.getText().toString());// 获取休息时长

        if (playDuration < RootComponent.SETTING_DEFAULT_PLAY_MIN) {// 最小玩耍时长10分钟(ms)
            playDuration = RootComponent.SETTING_DEFAULT_PLAY_MIN;
            edSettingPlay.setText(String.valueOf(playDuration));
        }
        if (restDuration < RootComponent.SETTING_DEFAULT_REST_MIN) {// 最小休息时长30分钟(ms)
            restDuration = RootComponent.SETTING_DEFAULT_REST_MIN;
            edSettingRest.setText(String.valueOf(restDuration));
        }
        // 设置(ms)
        ShareUtils.set(RootComponent.SETTING_PLAY_TIME, String.valueOf(playDuration * 60 * 1000));
        ShareUtils.set(RootComponent.SETTING_REST_TIME, String.valueOf(restDuration * 60 * 1000));
        // 退出
        toFrag(getClass(), Frag_main.class, null, true);
        return true;
    }

}
