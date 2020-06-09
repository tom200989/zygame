package com.zygame.icegame.helper;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/*
 * Created by Administrator on 2020/6/8.
 */
@SuppressLint("ClickableViewAccessibility")
public class FoodMoveHelper {

    private float down_x = 0;// 点下x
    private float down_y = 0;// 点下y
    private float move_x = 0;// 移动x
    private float move_y = 0;// 移动y
    private float view_x = 0;// view当前x
    private float view_y = 0;// view当前y

    /**
     * 绑定移动对象
     *
     * @param view 移动对象
     */
    public void bind(View view) {
        view.setOnTouchListener((v, event) -> {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    down_x = event.getRawX();
                    down_y = event.getRawY();
                    view_x = view.getX();
                    view_y = view.getY();

                    break;
                case MotionEvent.ACTION_MOVE:
                    move_x = event.getRawX();
                    move_y = event.getRawY();

                    float dex = move_x - down_x;
                    float dey = move_y - down_y;

                    view_x += dex;
                    view_y += dey;

                    ViewHelper.setTranslationX(view, view_x);
                    ViewHelper.setTranslationY(view, view_y);
                    // (该步骤必须要做, 否则view会偏移)
                    view.setX(view_x);
                    view.setY(view_y);

                    down_x = move_x;
                    down_y = move_y;
                    break;

                case MotionEvent.ACTION_UP:
                    touchUpNext();// 回调
                    down_x = 0;
                    down_y = 0;
                    move_x = 0;
                    move_y = 0;
                    break;
            }
            return true;
        });
    }

    private OnTouchUpListener onTouchUpListener;

    // Inteerface--> 接口OnTouchUpListener
    public interface OnTouchUpListener {
        void touchUp();
    }

    // 对外方式setOnTouchUpListener
    public void setOnTouchUpListener(OnTouchUpListener onTouchUpListener) {
        this.onTouchUpListener = onTouchUpListener;
    }

    // 封装方法touchUpNext
    private void touchUpNext() {
        if (onTouchUpListener != null) {
            onTouchUpListener.touchUp();
        }
    }
}
