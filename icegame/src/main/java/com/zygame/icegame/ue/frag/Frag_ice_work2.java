package com.zygame.icegame.ue.frag;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hiber.hiber.RootFrag;
import com.zygame.common.component.RootComponent;
import com.zygame.icegame.R;
import com.zygame.icegame.R2;
import com.zygame.icegame.adapter.IceWork2Adapter;
import com.zygame.icegame.bean.IceShopBean;
import com.zygame.icegame.utils.ActionVoiceUtils;

import java.util.List;

import butterknife.BindView;

/*
 * Created by Administrator on 2020/10/019.
 */
@SuppressWarnings(value = {"unchecked"})
public class Frag_ice_work2 extends RootFrag {

    @BindView(R2.id.rl_ice_machine)
    RelativeLayout rlIceMachine;// 雪糕机
    @BindView(R2.id.iv_ice_cup_stream)
    ImageView ivIceCupStream;// 雪糕流
    @BindView(R2.id.iv_ice_cup_bottom)
    ImageView ivIceCupBottom;// 雪糕筒
    @BindView(R2.id.iv_ice_cup_top)
    ImageView ivIceCupTop;// 雪糕头
    @BindView(R2.id.rcv_ice_work2_list)
    RecyclerView rcvIceWork2List;// 菜单列表

    private List<IceShopBean> iceShopBeans;// 菜篮物品
    private IceWork2Adapter adapter;

    @Override
    public int onInflateLayout() {
        return R.layout.icegame_frag_ice_work2;
    }

    @Override
    public void onNexts(Object o, View view, String s) {
        if (o != null) {
            iceShopBeans = (List<IceShopBean>) o;
        }

        initRcv();// 初始化菜单
        initEvent();// 初始化事件
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        // 点击雪糕机
        rlIceMachine.setOnClickListener(v -> {
            if (adapter != null) {
                if (adapter.isClickAll()) {
                    // 显示雪糕流
                    ivIceCupStream.setVisibility(View.VISIBLE);
                    // 启动定时器显示雪糕头
                    showIceTop();
                } else {
                    toast(R.string.common_ice_work_tip, 3000);
                }
            }
        });

    }

    /**
     * 启动定时器显示雪糕头
     */
    private void showIceTop() {
        ivIceCupTop.setVisibility(View.VISIBLE);
        AlphaAnimation al = new AlphaAnimation(0, 1);
        al.setFillAfter(true);
        al.setDuration(5000);
        al.setInterpolator(new LinearInterpolator());
        ivIceCupTop.setAnimation(al);
        al.startNow();
        al.start();
        ivIceCupTop.startAnimation(al);
        al.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                toast(R.string.common_ice_work_finish, 3000);
                // 释放音频 - 一定要做, 否则出bug
                ActionVoiceUtils.releaseVoice();
                // 跳转到下一个页面
                toFrag(getClass(), Frag_ice_style.class, null, true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 初始化菜单
     */
    private void initRcv() {
        adapter = new IceWork2Adapter(activity, iceShopBeans);
        adapter.setOnClickOrderItemListener(() -> {
            ActionVoiceUtils.play(activity, R.raw.action_voice);// 播放音频
        });
        LinearLayoutManager lm = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        rcvIceWork2List.setLayoutManager(lm);
        rcvIceWork2List.setAdapter(adapter);
    }

    @Override
    public boolean onBackPresss() {
        // 释放音频 - 一定要做, 否则出bug
        ActionVoiceUtils.releaseVoice();
        // 退回到主页
        toFragModule(getClass(), RootComponent.SPLASH_AC, RootComponent.FRAG_MAIN, null, true);
        return true;
    }

}
