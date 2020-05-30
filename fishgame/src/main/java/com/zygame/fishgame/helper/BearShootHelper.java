package com.zygame.fishgame.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.zygame.fishgame.R;

/*
 * Created by Administrator on 2020/5/27.
 */
@SuppressLint("ClickableViewAccessibility")
public class BearShootHelper {

    private Context context;
    private boolean islock;// 是否锁止 T:锁止 F:解锁 (用于鱼钩下掉时反复点击导致线程溢出)

    public BearShootHelper(Context context) {
        this.context = context;
    }

    public void setlock(boolean islock) {
        this.islock = islock;
    }

    /**
     * 绑定要钓鱼的按钮
     *
     * @param ivMainShoot 按钮对象
     */
    public void bind(ImageView ivMainShoot) {

        ivMainShoot.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {/* 按下时  */
                ivMainShoot.setImageDrawable(context.getDrawable(R.drawable.btn_shoot_pressed));
                if (!islock) {// 处于解锁状态才回调
                    BearMouseDownNext();// 按下时回调
                }

            } else if (event.getAction() == MotionEvent.ACTION_UP) {/* 抬起时 */
                ivMainShoot.setImageDrawable(context.getDrawable(R.drawable.btn_shoot_unpressed));
                BearMouseUpNext();// 抬起时回调
            }
            return true;
        });
    }

    /* -------------------------------------------- impl -------------------------------------------- */
    private OnBearMouseDownListener onBearMouseDownListener;

    // Inteerface--> 接口OnBearMouseDownListener
    public interface OnBearMouseDownListener {
        void BearMouseDown();
    }

    // 对外方式setOnBearMouseDownListener
    public void setOnBearMouseDownListener(OnBearMouseDownListener onBearMouseDownListener) {
        this.onBearMouseDownListener = onBearMouseDownListener;
    }

    // 封装方法BearMouseDownNext
    private void BearMouseDownNext() {
        if (onBearMouseDownListener != null) {
            onBearMouseDownListener.BearMouseDown();
        }
    }

    private OnBearMouseUpListener onBearMouseUpListener;

    // Inteerface--> 接口OnBearMouseUpListener
    public interface OnBearMouseUpListener {
        void BearMouseUp();
    }

    // 对外方式setOnBearMouseUpListener
    public void setOnBearMouseUpListener(OnBearMouseUpListener onBearMouseUpListener) {
        this.onBearMouseUpListener = onBearMouseUpListener;
    }

    // 封装方法BearMouseUpNext
    private void BearMouseUpNext() {
        if (onBearMouseUpListener != null) {
            onBearMouseUpListener.BearMouseUp();
        }
    }
}
