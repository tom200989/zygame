package com.zygame.icegame.ue.frag;

import android.content.res.ColorStateList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.hiber.hiber.RootFrag;
import com.zygame.common.component.RootComponent;
import com.zygame.icegame.R;
import com.zygame.icegame.R2;
import com.zygame.icegame.adapter.IceColorAdapter;
import com.zygame.icegame.adapter.IceStyleAdapter;
import com.zygame.icegame.utils.ActionVoiceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/*
 * Created by Administrator on 2020/10/020.
 */
@SuppressWarnings(value = {"unchecked", "deprecation"})
public class Frag_ice_style extends RootFrag {

    @BindView(R2.id.iv_ice_1)
    ImageView ivIce1;// 雪糕花
    @BindView(R2.id.iv_ice_2)
    ImageView ivIce2;// 巧克力
    @BindView(R2.id.iv_ice_3)
    ImageView ivIce3;// 奶油
    @BindView(R2.id.iv_ice_4)
    ImageView ivIce4;// 雪糕筒
    @BindView(R2.id.iv_ice_5)
    ImageView ivIce5;// 雪糕带
    @BindView(R2.id.iv_ice_6)
    ImageView ivIce6;// 雪糕边
    @BindView(R2.id.iv_ice_7)
    ImageView ivIce7;// 雪糕线

    @BindView(R2.id.bt_ice_style)
    Button btIceStyle;// 完成按钮
    @BindView(R2.id.rcv_ice_style)
    RecyclerView rcvIceStyle;// 样式列表
    @BindView(R2.id.rcv_ice_color)
    RecyclerView rcvIceColor;// 颜色列表

    private IceStyleAdapter iceStyleAdapter;
    private List<ImageView> iceIvs = new ArrayList<>();
    private ImageView ivStyelTemp;// 临时样式控件
    private IceColorAdapter iceColorAdapter;

    @Override
    public int onInflateLayout() {
        return R.layout.icegame_frag_style;
    }

    @Override
    public void initViewFinish(View inflateView) {
        iceIvs.add(ivIce1);
        iceIvs.add(ivIce2);
        iceIvs.add(ivIce3);
        iceIvs.add(ivIce4);
        iceIvs.add(ivIce5);
        iceIvs.add(ivIce6);
        iceIvs.add(ivIce7);

    }

    @Override
    public void onNexts(Object o, View view, String s) {

        // * 样式适配器
        iceStyleAdapter = new IceStyleAdapter(activity);
        iceStyleAdapter.setOnClickStyelItemListener(position -> {
            // 播放音频
            ActionVoiceUtils.play(activity, R.raw.action_voice);
            // 雪糕样式临时存储
            ivStyelTemp = iceIvs.get(position);
        });// 临时样式存储
        LinearLayoutManager styleLm = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        rcvIceStyle.setLayoutManager(styleLm);
        rcvIceStyle.setAdapter(iceStyleAdapter);

        // * 颜色适配器
        iceColorAdapter = new IceColorAdapter(activity);
        iceColorAdapter.setOnClickColorItemListener(colorRes -> {
            // 播放音频
            ActionVoiceUtils.play(activity, R.raw.action_voice);
            // 修改雪糕样式渲染
            if (ivStyelTemp != null) {
                ivStyelTemp.setImageTintList(ColorStateList.valueOf(colorRes));
                ivStyelTemp.setBackgroundTintList(ColorStateList.valueOf(colorRes));
            } else {
                toast(R.string.common_ice_style_tip, 3000);
            }
        });
        LinearLayoutManager colorLm = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        rcvIceColor.setLayoutManager(colorLm);
        rcvIceColor.setAdapter(iceColorAdapter);

        // * 完成按钮点击
        btIceStyle.setOnClickListener(v -> {
            rcvIceStyle.setVisibility(View.GONE);
            rcvIceColor.setVisibility(View.GONE);
            btIceStyle.setVisibility(View.GONE);
        });
    }

    @Override
    public boolean onBackPresss() {
        // 释放音频
        ActionVoiceUtils.releaseVoice();
        // 退回到主页
        toFragModule(getClass(), RootComponent.SPLASH_AC, RootComponent.FRAG_MAIN, null, true);
        return true;
    }

}
