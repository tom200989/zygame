package com.zygame.fishgame.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hiber.tools.ScreenSize;
import com.nineoldandroids.view.ViewHelper;

import java.util.Random;

/*
 * Created by Administrator on 2020/5/28.
 */
@SuppressLint("AppCompatCustomView")
public class FishWidget extends ImageView {

    private Context context;
    private ViewGroup parent;// 控件所在的父布局
    private int drawId;// 图元
    private DirectEnum directEnum;// 鱼头方向
    private int startY;// 垂直偏移距离
    private int fish_width_px;// 图元宽度
    private int fish_height_px;// 图元高度
    private int goal;// 不同资源所代表的分数

    public FishWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 小鱼对象
     *
     * @param context    域
     * @param parent     需要关联的父布局
     * @param drawId     图元
     * @param directEnum 鱼头朝向
     * @param startY     垂直方向值
     */
    @SuppressLint("ResourceType")
    public FishWidget(Context context, ViewGroup parent, @IdRes int drawId, DirectEnum directEnum, int startY) {
        this(context, null, 0);
        this.context = context;
        this.parent = parent;
        this.drawId = drawId;
        this.directEnum = directEnum;
        this.startY = startY;
        this.goal = getRandomGoal();
        // 设置图元
        Drawable fishDrawable = ContextCompat.getDrawable(context, drawId);
        setImageDrawable(fishDrawable);
        // 获取图元宽高
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), drawId, options);
        fish_width_px = options.outWidth;
        fish_height_px = options.outHeight;
        options.inJustDecodeBounds = false;
        // 是否翻转
        setScaleX(directEnum == DirectEnum.HEAD_RIGHT ? -1f : 1f);
        // 起始位置
        if (directEnum == DirectEnum.HEAD_RIGHT) {// 左侧进入 ->
            setX(-fish_width_px - 10);
        } else {// 右侧进入 <-
            setX(ScreenSize.getSize(context).width + 10);
        }
        // 设置垂直方向起始值
        setY(startY);
        // 设置大小
        RelativeLayout.LayoutParams ivp = new RelativeLayout.LayoutParams((int) (fish_width_px * 0.8f), (int) (fish_height_px * 0.8f));
        setLayoutParams(ivp);
        // 添加到父布局
        parent.addView(this);
    }

    /**
     * 游泳
     *
     * @param x 游到哪个x
     * @param y 游到哪个y
     */
    public void swimTo(Float x, Float y) {
        if (x != null) {
            ViewHelper.setTranslationX(this, x);
        }
        if (y != null) {
            ViewHelper.setTranslationY(this, y);
        }
    }

    /**
     * @return 提供分数给外部
     */
    public int getGoal() {
        return goal;
    }

    /**
     * 随机生成分数
     */
    private int getRandomGoal() {
        // 随机生成分数
        return new Random().nextInt(20) + 20;
    }

    public enum DirectEnum {
        HEAD_RIGHT(0), // 鱼头朝右
        HEAD_LEFT(1);// 鱼头朝左

        public int type;

        DirectEnum(int type) {
            this.type = type;
        }
    }

    public ViewGroup getViewParent() {
        return parent;
    }

    public void setParent(ViewGroup parent) {
        this.parent = parent;
    }

    public int getDrawId() {
        return drawId;
    }

    public void setDrawId(int drawId) {
        this.drawId = drawId;
    }

    public DirectEnum getDirectEnum() {
        return directEnum;
    }

    public void setDirectEnum(DirectEnum directEnum) {
        this.directEnum = directEnum;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getFish_width_px() {
        return fish_width_px;
    }

    public void setFish_width_px(int fish_width_px) {
        this.fish_width_px = fish_width_px;
    }

    public int getFish_height_px() {
        return fish_height_px;
    }

    public void setFish_height_px(int fish_height_px) {
        this.fish_height_px = fish_height_px;
    }
}
