package com.zygame.icegame.widget;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zygame.icegame.R;


/*
 * Created by Administrator on 2020/5/30.
 */
public class BlowWidget extends RelativeLayout {

    private View inflate;
    private Context context;
    private ImageView blow1;
    private ImageView blow2;
    private ImageView blow3;
    private ImageView blow4;
    private ImageView blow5;
    private ImageView blow6;
    private ImageView blow7;

    public BlowWidget(Context context) {
        this(context, null, 0);
    }

    public BlowWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlowWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        inflate = View.inflate(context, R.layout.icegame_blow_widget, this);
        blow1 = inflate.findViewById(R.id.blow1);
        blow2 = inflate.findViewById(R.id.blow2);
        blow3 = inflate.findViewById(R.id.blow3);
        blow4 = inflate.findViewById(R.id.blow4);
        blow5 = inflate.findViewById(R.id.blow5);
        blow6 = inflate.findViewById(R.id.blow6);
        blow7 = inflate.findViewById(R.id.blow7);

    }

    /**
     * 触发
     */
    public void activite() {
        // 启动动画 (顺序启动)
        blow1.postDelayed(() -> setViewAnimation(blow1), 0);
        blow2.postDelayed(() -> setViewAnimation(blow2), 100);
        blow3.postDelayed(() -> setViewAnimation(blow3), 200);
        blow4.postDelayed(() -> setViewAnimation(blow4), 300);
        blow5.postDelayed(() -> setViewAnimation(blow5), 400);
        blow6.postDelayed(() -> setViewAnimation(blow6), 500);
        blow7.postDelayed(() -> setViewAnimation(blow7), 600);
    }

    private void setViewAnimation(ImageView view) {
        // 显示图元
        view.setVisibility(VISIBLE);
        // 放大动画
        Animation scale = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setInterpolator(new LinearInterpolator());
        scale.setDuration(100);
        scale.setFillAfter(true);
        view.setAnimation(scale);
        view.startAnimation(scale);
        scale.startNow();

        // 渐显动画
        Animation alpha_show = new AlphaAnimation(0, 1);
        alpha_show.setInterpolator(new LinearInterpolator());
        alpha_show.setDuration(100);
        alpha_show.setFillAfter(true);
        alpha_show.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                // 改变动画图元
                view.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.blow_bloom));
                // x毫秒后渐隐
                view.postDelayed(() -> {
                    // 渐隐动画
                    Animation alpha_hide = new AlphaAnimation(1, 0);
                    alpha_hide.setInterpolator(new LinearInterpolator());
                    alpha_hide.setDuration(300);
                    alpha_hide.setFillAfter(true);
                    alpha_hide.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            // 恢复原来的图元
                            view.setVisibility(GONE);
                            view.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.blow_default));
                            // 如果最后一个气泡显示 - 恢复初始设置
                            if (view == blow7) {
                                Log.i("blow", "最后一个显示啦");
                            }
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    view.setAnimation(alpha_hide);
                    view.startAnimation(alpha_hide);
                    alpha_hide.startNow();

                }, 200);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.setAnimation(alpha_show);
        view.startAnimation(alpha_show);
        alpha_show.startNow();
    }
}
