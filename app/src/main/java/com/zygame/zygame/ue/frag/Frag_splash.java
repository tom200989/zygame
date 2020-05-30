package com.zygame.zygame.ue.frag;

import android.view.View;

import com.hiber.hiber.RootFrag;
import com.zygame.zygame.R;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/*
 * Created by qianli.ma on 2020/5/6 0006.
 */
public class Frag_splash extends RootFrag {

    private GifImageView gitLogo;// 动图

    @Override
    public int onInflateLayout() {
        return R.layout.frag_splash;
    }

    @Override
    public void initViewFinish(View inflateView) {
        // 显示gif动画
        gitLogo = inflateView.findViewById(R.id.gif_logo);
    }

    @Override
    public void onNexts(Object o, View view, String s) {
        gitLogo.postDelayed(() -> {
            // 停止动画
            GifDrawable gifDrawable = (GifDrawable) gitLogo.getDrawable();
            gifDrawable.stop();
            // 跳转到下一页
            toFrag(getClass(), Frag_main.class, null, true);
        }, 3000);
    }

    @Override
    public boolean onBackPresss() {
        return false;
    }
}
