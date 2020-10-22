package com.zygame.icegame.ue.frag;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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

    @BindView(R2.id.rl_ice_container)
    RelativeLayout rlIceContainer;// 雪糕容器
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
    @BindView(R2.id.iv_ice_screenshot)
    ImageView ivIceScreenShot;// 截图后展示控件

    private IceStyleAdapter iceStyleAdapter;
    private List<ImageView> iceIvs = new ArrayList<>();
    private ImageView ivStyelTemp;// 临时样式控件
    private IceColorAdapter iceColorAdapter;
    private int touchPx = 50;// 吃雪糕时手指像素范围(px)
    private Bitmap bitmapScreenshot;// 要吃的雪糕截图
    private boolean processFlag = false;// 手指操作限定, 防止暴击

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

    @SuppressLint("ClickableViewAccessibility")
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
            // 隐藏布局
            rlIceContainer.setVisibility(View.GONE);
            rcvIceStyle.setVisibility(View.GONE);
            rcvIceColor.setVisibility(View.GONE);
            btIceStyle.setVisibility(View.GONE);
            // 截图
            bitmapScreenshot = loadBitmapFromView(rlIceContainer);
            ivIceScreenShot.setVisibility(View.VISIBLE);
            ivIceScreenShot.setImageBitmap(bitmapScreenshot);
        });

        // * 点击截图控件
        ivIceScreenShot.setOnTouchListener((View.OnTouchListener) (v, event) -> {
            // 如果此时雪糕容器隐藏了 - 说明点击了［完成］- 则可以进行以下操作了
            if (rlIceContainer.getVisibility() == View.GONE) {
                // 手指抬起时进行判断
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // 锁定
                    if (!processFlag) {
                        processFlag = true;
                        int x = (int) event.getX();
                        int y = (int) event.getY();
                        // 两个for循环确定下压点的矩形大小
                        for (int i = -touchPx; i < touchPx + 1; i++) {
                            for (int j = -touchPx; j < touchPx + 1; j++) {
                                // 限定作用范围 (此处一定要做, 否则靠下面的try...catch...会影响性能)
                                int px = x + i;
                                int py = y + j;
                                if (px >= 0 & px < bitmapScreenshot.getWidth() & py >= 0 & py < bitmapScreenshot.getHeight()) {
                                    try {
                                        // 平方根计算圆范围
                                        if (Math.sqrt(i * i + j * j) <= touchPx) {
                                            bitmapScreenshot.setPixel(px, py, Color.TRANSPARENT);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        // 重新设置图元
                        ivIceScreenShot.setImageBitmap(bitmapScreenshot);
                        // 释放
                        processFlag = false;
                    }

                }

            }
            return true;
        });
    }

    /**
     * 截图(getDrawableCache已经过时无效)
     */
    private Bitmap loadBitmapFromView(View v) {
        if (v == null) {
            return null;
        }
        Bitmap screenshot;
        // 这里设置8888的目的是使用透明度 - 否则565使用的是32位, 透明背景会出现黑色
        screenshot = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(screenshot);
        c.translate(-v.getScrollX(), -v.getScrollY());
        v.draw(c);
        return screenshot;
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
