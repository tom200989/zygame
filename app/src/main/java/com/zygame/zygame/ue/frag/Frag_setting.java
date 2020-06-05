package com.zygame.zygame.ue.frag;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.hiber.hiber.RootFrag;
import com.zygame.common.helper.PreventHelper;
import com.zygame.zygame.R;

import butterknife.BindView;

/*
 * Created by Administrator on 2020/5/30.
 */
public class Frag_setting extends RootFrag {

    @BindView(R.id.iv_setting_exit)
    ImageView ivSettingExit;// 回退
    @BindView(R.id.ed_setting_total_permit_play)
    EditText edSettingTotalPermitPlay;// 一共玩了的时长

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
        edSettingTotalPermitPlay.setText(PreventHelper.getTotalPermitDuration_Min_Str());
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
        // TODO: 2020/6/5  退出时- 设置防沉迷
        long totalPermit = Long.parseLong(edSettingTotalPermitPlay.getText().toString()) * 60 * 1000;
        PreventHelper.setTotalPermitDuration(totalPermit);
        // 退出
        toFrag(getClass(), Frag_main.class, null, true);
        return true;
    }

}
