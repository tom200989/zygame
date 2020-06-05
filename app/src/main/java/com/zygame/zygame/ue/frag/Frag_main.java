package com.zygame.zygame.ue.frag;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hiber.hiber.RootFrag;
import com.hiber.tools.ShareUtils;
import com.zygame.common.component.RootComponent;
import com.zygame.zygame.R;

import butterknife.BindView;

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
        // 初始化玩耍时间
        if (ShareUtils.get(RootComponent.SETTING_PLAY_TIME, -1L) == -1L) {
            // 如果是首次安装 - 默认为40分钟
            ShareUtils.set(RootComponent.SETTING_PLAY_TIME, RootComponent.SETTING_DEFAULT_PLAY_DURATION);
        }

        // 初始化休息时间
        if (ShareUtils.get(RootComponent.SETTING_REST_TIME, -1L) == -1L) {
            // 如果是首次安装 - 默认为60分钟
            ShareUtils.set(RootComponent.SETTING_REST_TIME, RootComponent.SETTING_DEFAULT_REST_DURATION);
        }

        // 显示防沉迷可玩时间
        long playtime_ms = ShareUtils.get(RootComponent.SETTING_PLAY_TIME, RootComponent.SETTING_DEFAULT_PLAY_DURATION);
        int playtime_min = (int) (playtime_ms / 60 / 1000);
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
            // 当前时间 - 上次时间 >= 休息时长 -- 可以进入
            // 获取: 当前时间
            long currenttime = System.currentTimeMillis();
            // 获取: 上次时间 (首次不会记录=0)
            long lasttime = ShareUtils.get(RootComponent.SETTING_LAST_TIME, 0L);
            // 获取: 休息时长
            long restDuration = ShareUtils.get(RootComponent.SETTING_REST_TIME, RootComponent.SETTING_DEFAULT_REST_DURATION);
            // 当前时间 - 上次时间 >= 休息时长 -- 允许再次进入
            if (currenttime - lasttime >= restDuration) {
                toFragModule(getClass(), RootComponent.FISH_AC, RootComponent.FRAG_FISH, null, true);
            } else {
                // 否则提示
                toast("休息时间还没过\n先休息一下噢", 6000);
            }
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
