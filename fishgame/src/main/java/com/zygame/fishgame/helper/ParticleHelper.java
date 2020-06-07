package com.zygame.fishgame.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.plattysoft.leonids.ParticleSystem;
import com.zygame.fishgame.R;

@SuppressLint("StaticFieldLeak")
public class ParticleHelper {

    public static ParticleSystem leniod;
    private static int delay = 5000;// 延迟N秒再加载下一个效果
    public static int TYPE_DEFAULT = 0;// 默认效果(无效果)
    public static int TYPE_RAIN = 1;// 下雨效果
    public static int TYPE_WINTER = 2;// 冬天效果
    public static int TYPE_AUTUMN = 3;// 秋天效果
    public static int TYPE_NIGHT = 4;// 夜晚效果

    /**
     * 风效果
     *
     * @param activity  域
     * @param showView  释放粒子的控件
     * @param yeziCount 叶子数量
     * @param stayTime  叶子停留时长ms
     */
    public static void wind(Activity activity, View showView, int yeziCount, int stayTime) {
        stopEmit();
        // 加handler的目的是利用延迟来解决stopEmitting时销毁view不及时而导致上一个粒子效果停留在界面上, 也可以使用其他延时方式
        new Handler().postDelayed(() -> {
            leniod = new ParticleSystem(activity, 15, R.drawable.yezi_leniod, stayTime);// 粒子数量+停留时间
            leniod.setSpeedModuleAndAngleRange(0f, 0.1f, 0, 135)//速度和角度 
                    .setRotationSpeed(144)// 选择速度
                    .setAcceleration(0.000017f, 90)// 加速
                    .setFadeOut(200, new AccelerateInterpolator())// 粒子出现时间
                    .emitWithGravity(showView, Gravity.BOTTOM, yeziCount);// 控制叶子数量的因素
        }, delay);

    }

    /**
     * 雨效果
     *
     * @param activity  域
     * @param showView  释放粒子的控件
     * @param rainCount 雨滴数量
     * @param stayTime  雨滴停留时长ms
     */
    public static void rain(Activity activity, View showView, int rainCount, int stayTime) {
        stopEmit();
        // 加handler的目的是利用延迟来解决stopEmitting时销毁view不及时而导致上一个粒子效果停留在界面上, 也可以使用其他延时方式
        new Handler().postDelayed(() -> {
            leniod = new ParticleSystem(activity, 800, R.drawable.yudi_leniod, stayTime);// 粒子数量+停留时间
            leniod.setAcceleration(0.00013f, 90)// 加速
                    .setSpeedByComponentsRange(0f, 0f, 0.05f, 0.1f)// 速度范围
                    .setFadeOut(200, new AccelerateInterpolator())// 粒子出现时间
                    .emitWithGravity(showView, Gravity.BOTTOM, rainCount);// 控制雨滴数量
        }, delay);
    }

    /**
     * 雪效果
     *
     * @param activity  域
     * @param showView  释放粒子的控件
     * @param snowCount 雪数量
     * @param stayTime  雪停留时长ms
     */
    public static void snow(Activity activity, View showView, int snowCount, int stayTime) {
        stopEmit();
        // 加handler的目的是利用延迟来解决stopEmitting时销毁view不及时而导致上一个粒子效果停留在界面上, 也可以使用其他延时方式
        new Handler().postDelayed(() -> {
            leniod = new ParticleSystem(activity, 15, R.drawable.snow_leniod, stayTime);// 粒子数量+停留时间
            leniod.setSpeedModuleAndAngleRange(0f, 0.1f, 0, 135)//速度和角度 
                    .setRotationSpeed(144)// 选择速度
                    .setAcceleration(0.000017f, 90)// 加速
                    .setFadeOut(200, new AccelerateInterpolator())// 粒子出现时间
                    .emitWithGravity(showView, Gravity.BOTTOM, snowCount);// 控制叶子数量的因素
        }, delay);
    }

    /**
     * 夜晚效果
     *
     * @param activity  域
     * @param showView  释放粒子的控件
     * @param starCount 雪数量
     * @param stayTime  雪停留时长ms
     */
    public static void night(Activity activity, View showView, int starCount, int stayTime) {
        stopEmit();
        // 加handler的目的是利用延迟来解决stopEmitting时销毁view不及时而导致上一个粒子效果停留在界面上, 也可以使用其他延时方式
        new Handler().postDelayed(() -> {
            leniod = new ParticleSystem(activity, 15, R.drawable.star, stayTime);// 粒子数量+停留时间
            leniod.setSpeedModuleAndAngleRange(0f, 0.1f, 0, 135)//速度和角度 
                    .setRotationSpeed(144)// 选择速度
                    .setAcceleration(0.000017f, 90)// 加速
                    .setFadeOut(200, new AccelerateInterpolator())// 粒子出现时间
                    .emitWithGravity(showView, Gravity.BOTTOM, starCount);// 控制叶子数量的因素
        }, delay);
    }

    /**
     * 停止效果
     */
    private static void stopEmit() {
        // 先停止之前的效果
        if (leniod != null) {
            leniod.stopEmitting();
        }
    }

}
