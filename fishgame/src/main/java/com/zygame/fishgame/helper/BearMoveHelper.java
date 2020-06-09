package com.zygame.fishgame.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hiber.tools.ScreenSize;
import com.nineoldandroids.view.ViewHelper;
import com.zygame.fishgame.widget.BlowWidget;

/*
 * Created by Administrator on 2020/5/26.
 */
public class BearMoveHelper {

    private final int screenWidth;// 屏幕宽度
    private float down_x = 0;// 点下时的坐标
    private float bear_x = 0;// rlSpriteBear的X坐标
    private float yugou_x = 0;// ivYugou的X坐标
    private float blow_x = 0;// blowPop的X坐标
    private float yuxian_x = 0;// ivYuxian的X坐标
    private boolean islock;// 是否上锁(用于按钮按下时不允许滑动操作)
    private float yuxian_margin_init;// 手指按下时鱼线的初始偏移start值

    public BearMoveHelper(Context context) {
        // 获取屏幕宽度
        screenWidth = ScreenSize.getSize(context).width;
    }

    /**
     * 设置上锁状态(用于按钮按下时不允许滑动操作)
     *
     * @param islock T:上锁 F:解锁
     */
    public void setlock(boolean islock) {
        this.islock = islock;
    }

    /**
     * 绑定要移动的空间
     *
     * @param rlSpriteBear 熊二
     * @param ivYugou      鱼钩
     */
    @SuppressLint("ClickableViewAccessibility")
    public void bind(RelativeLayout rlMainFishArea,// 捕鱼区 (用于触发手势控制)
                     RelativeLayout rlSpriteBear,// 熊二 (移动)
                     ImageView ivYugou,// 鱼钩 (移动)
                     BlowWidget blowPop,// 气泡 (动画)
                     ImageView ivYuxian) {// 鱼线 (缩放)
        // 0.以鱼塘作为滑动面板
        rlMainFishArea.setOnTouchListener((v, event) -> {

            // 0.1.上锁状态不做任何处理 - 一般有外部决定, 比如钓鱼按钮生效时
            if (islock) {
                return false;
            }

            // 1.获取手势事件
            int action = event.getAction();
            // 2.分析事件
            if (action == MotionEvent.ACTION_DOWN) {

                // 2.1.获取点下时X坐标
                down_x = event.getRawX();
                bear_x = rlSpriteBear.getX();
                yugou_x = ivYugou.getX();
                blow_x = blowPop.getX();
                yuxian_x = ivYuxian.getX();
                yuxian_margin_init = ((RelativeLayout.LayoutParams) ivYuxian.getLayoutParams()).getMarginStart();

            } else if (action == MotionEvent.ACTION_MOVE) {
                // 2.1.获取移动时X坐标
                float move_x = event.getRawX();
                // 2.2.计算变化值
                float dex = move_x - down_x;
                // 2.3.计算最新的目标值
                float translationBearX = bear_x += dex;
                float translationYugouX = yugou_x += dex;
                float translationBlowX = blow_x += dex;
                float translationYuxianX = yuxian_x += dex;

                // 2.4.如果靠近最左侧
                if (translationBearX <= 0) {// 熊二
                    translationBearX = 0;
                }
                if (translationYugouX <= rlSpriteBear.getWidth() - ivYugou.getWidth()) {// 鱼钩
                    translationYugouX = rlSpriteBear.getWidth() - ivYugou.getWidth();
                }
                if (translationBlowX <= rlSpriteBear.getWidth() - blowPop.getWidth()) {// 气泡
                    translationBlowX = rlSpriteBear.getWidth() - blowPop.getWidth();
                }
                if (translationYuxianX <= rlSpriteBear.getWidth() - ivYugou.getWidth() + yuxian_margin_init) {// 鱼线
                    translationYuxianX = rlSpriteBear.getWidth() - ivYugou.getWidth() + yuxian_margin_init;
                }

                // 2.4.如果靠近最右侧 - 则强制为［屏幕宽度 - rlSpriteBear控件宽度］
                if (translationBearX >= screenWidth - rlSpriteBear.getWidth()) {// 熊二
                    translationBearX = screenWidth - rlSpriteBear.getWidth();
                }
                if (translationYugouX >= screenWidth - ivYugou.getWidth()) {// 鱼钩
                    translationYugouX = screenWidth - ivYugou.getWidth();
                }
                if (translationBlowX >= screenWidth - ivYugou.getWidth()) {// 气泡
                    translationBlowX = screenWidth - ivYugou.getWidth();
                }
                if (translationYuxianX >= screenWidth - ivYugou.getWidth() + yuxian_margin_init) {// 鱼线
                    translationYuxianX = screenWidth - ivYugou.getWidth() + yuxian_margin_init;
                }

                // 2.5.调用ViewHelper进行移动
                ViewHelper.setTranslationX(rlSpriteBear, translationBearX);
                ViewHelper.setTranslationX(ivYugou, translationYugouX);
                ViewHelper.setTranslationX(blowPop, translationBlowX);
                ViewHelper.setTranslationX(ivYuxian, translationYuxianX);
                // 2.6.更新x坐标(该步骤必须要做, 否则view会偏移)
                rlSpriteBear.setX(translationBearX);
                ivYugou.setX(translationYugouX);
                blowPop.setX(translationBlowX);
                ivYuxian.setX(translationYuxianX);
                // 2.7.更新当前起始X值
                down_x = move_x;

            } else if (action == MotionEvent.ACTION_UP) {
                // 2.1.手指抬起时复位
                down_x = 0;
                bear_x = 0;
                yugou_x = 0;
                blow_x = 0;
                yuxian_x = 0;
            }
            return true;
        });
    }
}
