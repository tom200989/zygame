package com.zygame.icegame.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by fanrunqi on 2016/7/6.
 */
@SuppressLint({"AppCompatCustomView", "HandlerLeak"})
public class WaterWidget extends ImageView {

    /*
     * 使用方式:
     *
     * xml设置background作为背景容器
     * xml设置src作为波浪绘制容器
     *
     * 注意: src的大小需要与background的大小一致
     * 并且需要绘制波浪的部分为［非透明］, 其他部分不需要绘制的部分必须为［透明］
     */

    // View的宽高
    private int view_w;
    private int view_h;

    // 图片背景
    private Bitmap srcBitmap;

    // 控件视图对角线长度
    private int obliquelen;
    // 偏差值(用于修正边线的余量)
    private int deviation = 50 * 50;

    // 绘制波纹
    private Path wavePath;
    private Paint wavePaint;

    // 背景画笔与画布
    private Paint bgPaint;
    private Canvas canvas;

    // 波浪参数
    private int waveHeight = 12;
    private int waveHalf_w = 100;
    private String waveColor = "#945be4ef";
    private int speed = 15;

    // 默认进度值
    private int maxProgress = 100;
    private float currentProgress = 70;
    private float currentY;

    // 动画位移距离
    private float distance = 0;
    private float degreeX;

    // 控件旋转角度
    private float degreeY = 30;

    // 图元矩阵
    private Matrix matrix;
    private Bitmap finalBitmap;

    // handler无限循环绘制
    private static final int INVALIDATE = 0X777;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == INVALIDATE) {
                invalidate();
                sendEmptyMessageDelayed(INVALIDATE, 3);
            }
        }
    };


    public WaterWidget(Context context) {
        this(context, null, 0);
    }

    public WaterWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();// 初始化
        start();// 启动
    }

    /* -------------------------------------------- private -------------------------------------------- */

    /**
     * 初始化
     */
    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        // 获得src
        if (null == getDrawable()) {
            throw new IllegalArgumentException("请至少要设置一个src图片源或者drawable");
        } else {
            degreeY = (int) getRotation();
        }
        // 波浪路径
        wavePath = new Path();
        // 波浪画笔
        wavePaint = new Paint();
        wavePaint.setAntiAlias(true);
        wavePaint.setStyle(Paint.Style.FILL);
    }

    /**
     * 启动
     */
    private void start() {
        // 开始轮询刷新
        handler.sendEmptyMessageDelayed(INVALIDATE, 10);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获得控件的宽高，默认MeasureSpec.EXACTLY
        view_w = MeasureSpec.getSize(widthMeasureSpec);
        currentY = view_h = MeasureSpec.getSize(heightMeasureSpec);
        // 测量后加载图片并获取大小
        srcBitmap = getBitmapFromDrawable(getDrawable());
        // 获取控件对角线长度
        obliquelen = (int) Math.sqrt(Math.pow(view_w, 2) + Math.pow(view_h, 2));
        // 修正偏差取值 (布满父布局情况下 - 保持2500偏差; 否则不做偏差)
        if (getContext() != null) {
            int screentWidth = ScreenSize.getSize(getContext()).width;
            if (view_w < screentWidth) {
                deviation = 0;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (srcBitmap != null) {
            // 把最后的bitmap画上去
            canvas.drawBitmap(refreshBitmap(), 0, 0, null);
        }
    }

    /**
     * 开始绘制并返回图元
     *
     * @return 绘制后的图元
     */
    private Bitmap refreshBitmap() {

        // 设置波浪画笔颜色
        wavePaint.setColor(Color.parseColor(waveColor));
        // 设置背景画笔并创建图元
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        finalBitmap = Bitmap.createBitmap(view_w, view_h, Bitmap.Config.ARGB_8888);
        // 产生一个同样大小的画布
        canvas = new Canvas(finalBitmap);

        /* -------------------------------------------- 绘制波浪 START -------------------------------------------- */

        // 根据设置的进度计算起始高度
        currentY = view_h * (maxProgress - currentProgress) / maxProgress;
        currentYNext(view_h - currentY);

        // 重置画笔
        wavePath.reset();

        // 考虑到倾斜时的最大对角线长度 - 此处起始点需要偏移
        float offsetX = ((obliquelen - view_w) >> 1) + deviation;

        // 起始点
        float startX = 0 - distance - offsetX * 100;
        float startY = currentY;

        // 移动画笔
        wavePath.moveTo(startX, startY);

        // 根据输入的wavewidth进行波浪数设定
        int waveNum = view_w / (waveHalf_w * 4) + 1;

        // 设置乘数对每个波浪的x位移进行递增
        int waveIdx = 0;

        // 波浪数增益
        int waveAdd = (obliquelen / waveHalf_w) + 1;

        // 绘制波浪右上角点
        for (int i = 0; i < waveNum * waveAdd * 30; i++) {
            // 曲线1
            float x11 = waveHalf_w * (waveIdx + 1) - distance - offsetX * 100;// 控制点1
            float y11 = currentY - waveHeight;
            float x12 = waveHalf_w * (waveIdx + 2) - distance - offsetX * 100;// 结束点1
            float y12 = currentY;
            wavePath.quadTo(x11, y11, x12, y12);

            // 曲线2
            float x21 = waveHalf_w * (waveIdx + 3) - distance - offsetX * 100;// 控制点2
            float y21 = currentY + waveHeight;
            float x22 = waveHalf_w * (waveIdx + 4) - distance - offsetX * 100;// 结束点2
            float y22 = currentY;

            wavePath.quadTo(x21, y21, x22, y22);
            waveIdx += 4;
        }

        // 设置移动值
        distance += (float) waveHalf_w / speed;
        distance = distance % (waveHalf_w * 4);

        // 绘制波浪区域［右下角点］
        wavePath.lineTo(view_w + offsetX, obliquelen + deviation);
        // 绘制波浪区域［左下角点］
        wavePath.lineTo(startX, obliquelen + deviation);
        // 闭合绘制区域
        wavePath.close();

        // 旋转画笔
        matrix = new Matrix();

        // 设定X翻转的边界 - 当尾部有一定角度时才去翻转
        if (degreeX > 15) {
            setScaleY(-1);
        }

        // 设定X翻转的边界 - 当头部有一定角度时才去翻转
        if (degreeX < -15) {
            setScaleY(1);
        }

        // 旋转画笔 - 绘制水平线角度
        matrix.setRotate(-degreeY, degreeY >= 0 ? view_w : 0, view_h);
        canvas.setMatrix(matrix);
        canvas.drawPath(wavePath, wavePaint);
        /* -------------------------------------------- 绘制波浪 END -------------------------------------------- */

        // 对图片给进行缩放
        srcBitmap = Bitmap.createScaledBitmap(srcBitmap, view_w, view_h, false);
        // 使用DST_ATOP - 取上层非交集部分与下层交集部分
        bgPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
        // 绘制背景图片
        matrix.setRotate(0, (float) view_w / 2, (float) view_h / 2);
        canvas.setMatrix(matrix);
        canvas.drawBitmap(srcBitmap, 0, 0, bgPaint);
        return finalBitmap;
    }

    /**
     * Drawable -> Bitmap
     */
    private Bitmap getBitmapFromDrawable(Drawable drawable) {

        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        try {
            // 获取图片的实际大小
            Bitmap bitmap;
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();

            // 如果控件没有指定你大小 - 则直接让控件大小等于图片大小
            if (intrinsicWidth == -1) {
                intrinsicWidth = view_w;
            }

            if (intrinsicHeight == -1) {
                intrinsicHeight = view_h;
            }

            // 再生成新尺寸的bitmap
            bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }

    /**
     * 获取屏幕尺寸
     */
    public static class ScreenSize {
        public static SizeBean getSize(Context context) {
            Activity activity = (Activity) context;
            SizeBean sizeBean = new SizeBean();
            sizeBean.width = activity.getWindowManager().getDefaultDisplay().getWidth();
            sizeBean.height = activity.getWindowManager().getDefaultDisplay().getHeight();
            return sizeBean;
        }

        public static class SizeBean {
            public int width;
            public int height;
        }

    }

    /* -------------------------------------------- public -------------------------------------------- */

    /**
     * 设置偏转角度
     *
     * @param degreeX X偏转角度
     * @param degreeY Y偏转角度
     */
    public void setDegree(float degreeX, float degreeY) {
        this.degreeX = degreeX;
        this.degreeY = degreeY;
    }

    /**
     * 设置当前进度
     *
     * @param currentProgress 当前进度
     */
    public void setCurrent(float currentProgress) {
        this.currentProgress = currentProgress;
    }

    /**
     * 获取当前进度
     *
     * @return 当前进度
     */
    public float getCurrent() {
        return currentProgress;
    }

    /**
     * 获取最大进度值
     *
     * @return 最大进度值
     */
    public int getMaxProgress() {
        return maxProgress;
    }

    /**
     * 设置最大进度
     *
     * @param maxProgress 设置进度条的最大值，默认100
     */
    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    /**
     * 设置波峰高度
     *
     * @param mWaveHeight 波峰的高度
     * @param mWaveWidth  一个波峰的宽度
     */
    public void setWave(int mWaveHeight, int mWaveWidth) {
        this.waveHeight = mWaveHeight;
        this.waveHalf_w = mWaveWidth / 2;
    }

    /**
     * 设置谁的颜色
     *
     * @param mWaveColor 水的颜色
     */
    public void setWaveColor(String mWaveColor) {
        this.waveColor = mWaveColor;
    }

    /**
     * 值越大震荡的越慢
     *
     * @param mWaveSpeed 震荡速度
     */
    public void setWaveSpeed(int mWaveSpeed) {
        this.speed = mWaveSpeed;
    }


    /* -------------------------------------------- impl -------------------------------------------- */
    private OnCurrentYListener onCurrentYListener;

    // Inteerface--> 接口OnCurrentYListener
    public interface OnCurrentYListener {
        void currentY(float currentY);
    }

    // 对外方式setOnCurrentYListener
    public void setOnCurrentYListener(OnCurrentYListener onCurrentYListener) {
        this.onCurrentYListener = onCurrentYListener;
    }

    // 封装方法currentYNext
    private void currentYNext(float currentY) {
        if (onCurrentYListener != null) {
            onCurrentYListener.currentY(currentY);
        }
    }
}
