package com.zygame.zygame.widget;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

/*
 * Created by Administrator on 2020/6/11.
 */
@SuppressLint("ClickableViewAccessibility")
public class AStartWidget extends View {
    /**
     * 绘制路径
     */
    private Path path;
    /**
     * 绘制笔
     */
    private Paint paint;
    /**
     * 上一个点位的坐标值
     */
    private float preX, preY;
    /**
     * 单波波长【这个是同一x轴上最近两点之间的距离（与物理的波长是一般的关系）】
     */
    private int mItemWaveLength = 200;
    /**
     * 动画刷新单位时间内平移距离
     */
    private int dx;


    /**
     * 波浪起点高度
     */
    private int originY;


    public AStartWidget(Context context) {
        super(context);
        init();
    }

    public AStartWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AStartWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    /**
     * 初始化
     */
    private void init() {
        path = new Path();

        paint = new Paint();
        //        paint.setStyle(Paint.Style.STROKE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.CYAN);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(10);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();

        int halfWaveLen = mItemWaveLength / 2;
        path.moveTo(-mItemWaveLength + dx, originY);
        //左右各多出一个波长
        // TODO: 2020/6/11  调试
        for (int i = -mItemWaveLength; i <= getWidth() + mItemWaveLength; i += mItemWaveLength) {
            // path.rQuadTo((float) halfWaveLen / 2, -50, halfWaveLen, 0);
            // path.rQuadTo((float) halfWaveLen / 2, 50, halfWaveLen, 0);
            path.rQuadTo((float) halfWaveLen / 2, -50, halfWaveLen, 0);
            path.rQuadTo((float) halfWaveLen / 2, 50, halfWaveLen, 0);
        }

        

        //封闭图形
        path.lineTo(getWidth(), getHeight());
        path.lineTo(0, getHeight());
        path.close();

        canvas.drawPath(path, paint);
    }


    public void startAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0, mItemWaveLength);//平移动画，一个波长的距离
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(animation -> {
            dx = (int) animation.getAnimatedValue();
            postInvalidate();
        });
        animator.start();
    }

    /**
     * 设置起始高度
     */
    public void setOriginY(int originY) {
        this.originY = originY;
        invalidate();
    }
}
