package com.zygame.zygame.ue.frag;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hiber.hiber.RootFrag;
import com.zygame.common.PreventBean;
import com.zygame.common.component.RootComponent;
import com.zygame.common.helper.PreventHelper;
import com.zygame.zygame.R;

import butterknife.BindView;

import static com.zygame.common.component.RootComponent.PREVENT_CLEAR_TIME;

/*
 * Created by Administrator on 2020/5/25.
 */
public class Frag_main extends RootFrag {

    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;// 标题
    @BindView(R.id.iv_main_fish)
    ImageView ivMainFish;// 钓鱼
    @BindView(R.id.iv_main_ice)
    ImageView ivMainIce;// 雪糕
    @BindView(R.id.iv_main_setting)
    ImageView ivMainSetting;// 设置
    @BindView(R.id.iv_main_close)
    ImageView ivMainClose;// 关闭

    @Override
    public int onInflateLayout() {
        return R.layout.frag_main;
    }

    /**
     * 初始化防沉迷数据
     */
    private void creatSpRestData() {

        // 防沉迷: 当时长大于6小时: 每日清零
        if (PreventHelper.getCurrentTime() - PreventHelper.getLastRecordTime() > PREVENT_CLEAR_TIME) {
            PreventHelper.setTotalDuration(0);
        }

        // 防沉迷: 初始化防沉迷数据
        PreventBean preventBean = PreventHelper.getPrevent();
        if (preventBean == null) {
            PreventHelper.initPrevent();
        }

        long totalDuration = PreventHelper.getTotalDuration();// 已经玩的时间
        long totalPermitPlayDuration = PreventHelper.getTotalPermitDuration();// 总共可玩时间
        long showDuration = 0;
        if (totalDuration < totalPermitPlayDuration) {
            showDuration = totalPermitPlayDuration - totalDuration;
        }

        // 显示防沉迷可玩时间
        int playtime_min = (int) (showDuration / 60 / 1000);
        String playtime_show = String.format(getString(R.string.common_app_main_today_play_time), playtime_min);
        tvMainTitle.setText(playtime_show);
    }

    @Override
    public void initViewFinish(View inflateView) {
        // 初始化防沉迷数据
        creatSpRestData();
        // 退出APP
        ivMainClose.setOnClickListener(v -> onBackPresss());
        // 进入设置页
        ivMainSetting.setOnClickListener(v -> toFrag(getClass(), Frag_setting.class, null, true));
        // 跳转到FISH
        ivMainFish.setOnClickListener(v -> {
            // 防沉迷: 当时长大于12小时: 每日清零
            if (PreventHelper.getCurrentTime() - PreventHelper.getLastRecordTime() > PREVENT_CLEAR_TIME) {
                PreventHelper.setTotalDuration(0);
            }

            // 防沉迷: 防沉迷进入判断
            long totalDuration = PreventHelper.getTotalDuration();// 获取一共玩了多久
            long totalPermitPlayDuration = PreventHelper.getTotalPermitDuration();// 获取一共允许玩多久
            if (totalDuration >= totalPermitPlayDuration) {// 如果当天超时了 - 提示
                toast(R.string.common_app_setting_tip2, 6000);

            } else {// 当天没有超时 - 则再判断单次是否超时
                toFragModule(getClass(), RootComponent.FISH_AC, RootComponent.FRAG_FISH, null, true);
            }

            // toFragModule(getClass(), RootComponent.FISH_AC, RootComponent.FRAG_FISH, null, true);


        });
        // 跳转到ICE
        // ivMainIce.setOnClickListener(v -> toFragModule(getClass(), RootComponent.ICE_AC, RootComponent.FRAG_ICE, null, true));
        ivMainIce.setOnClickListener(v -> toast("阳阳雪糕店还没建好噢~", 5000));
    }

    @Override
    public void onNexts(Object o, View view, String s) {

    }

    @Override
    public boolean onBackPresss() {
        killAllActivitys();
        kill();
        return true;
    }
}
