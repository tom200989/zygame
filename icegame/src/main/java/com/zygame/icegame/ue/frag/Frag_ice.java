package com.zygame.icegame.ue.frag;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.hiber.hiber.RootFrag;
import com.hiber.tools.ScreenSize;
import com.hiber.tools.TimerHelper;
import com.zygame.icegame.R;
import com.zygame.icegame.R2;

import butterknife.BindView;

/*
 * Created by qianli.ma on 2020/5/6 0006.
 */
public class Frag_ice extends RootFrag {

    @BindView(R2.id.sv_ice)
    HorizontalScrollView svIce;
    @BindView(R2.id.iv_ice_car)
    ImageView ivIceCar;
    @BindView(R2.id.wh_ice_car1)
    ImageView whIceCar1;
    @BindView(R2.id.wh_ice_car2)
    ImageView whIceCar2;

    private TimerHelper carTimer;// 滚动定时器
    private int scrollview_width;// sv总长
    private int screen_width;// 屏幕长度
    private int currentSV_x = 0;// 当前滚动条位置
    private int sv_step = 20;// 滚动步长
    private int wheel_duration = 2000;// 轮子滚动周期

    @Override
    public int onInflateLayout() {
        return R.layout.icegame_frag_ice;
    }


    @Override
    public void initViewFinish(View inflateView) {
        initEvent();
    }

    @Override
    public boolean isReloadData() {
        return false;
    }

    @SuppressLint({"NewApi", "ClickableViewAccessibility"})
    private void initEvent() {

        /* 设置scrollview滚动监听 */
        svIce.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            // 改变时赋值
            currentSV_x = scrollX;
        });

        /* 屏蔽手势滑动 */
        svIce.setOnTouchListener((v, event) -> true);

        /* 视图加载完毕监听 */
        svIce.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {

                // 1.获取scrollview总长和屏幕长度
                scrollview_width = svIce.getChildAt(0).getWidth();
                screen_width = ScreenSize.getSize(activity).width;
                // 2.启动滚动定时器
                carTimer = new TimerHelper(activity) {
                    @Override
                    public void doSomething() {
                        // 2.1.如果到了底边 - 停止
                        if (currentSV_x >= scrollview_width - screen_width) {
                            carTimer.stop();// sv停止滑动
                            whIceCar1.clearAnimation();// 停止动画
                            whIceCar2.clearAnimation();// 停止动画
                            return;
                        }
                        // 2.2.使scrollview滚动
                        svIce.smoothScrollTo(currentSV_x + sv_step, 0);
                    }
                };
                carTimer.start(50);
                // 3.轮子开始滚动动画
                setWheelAnim();

                // 3.移除监听
                svIce.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }

            /**
             * 轮子滚动动画
             */
            private void setWheelAnim() {
                RotateAnimation rotate = new RotateAnimation(0, 360, 1, 0.5f, 1, 0.5f);
                rotate.setFillAfter(true);
                rotate.setDuration(wheel_duration);
                rotate.setInterpolator(new LinearInterpolator());
                rotate.setRepeatCount(Animation.INFINITE);
                rotate.setRepeatMode(Animation.INFINITE);
                whIceCar1.setAnimation(rotate);
                whIceCar2.setAnimation(rotate);
                rotate.startNow();
                whIceCar1.startAnimation(rotate);
                whIceCar2.startAnimation(rotate);
            }
        });
    }

    @Override
    public void onNexts(Object o, View view, String s) {

    }

    @Override
    public boolean onBackPresss() {
        return false;
    }

}
