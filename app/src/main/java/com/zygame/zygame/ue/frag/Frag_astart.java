package com.zygame.zygame.ue.frag;

import android.view.View;

import com.hiber.hiber.RootFrag;
import com.zygame.zygame.R;
import com.zygame.zygame.widget.AStartWidget;

import butterknife.BindView;

/*
 * Created by Administrator on 2020/6/11.
 */
public class Frag_astart extends RootFrag {
    @BindView(R.id.wd_start)
    AStartWidget wdStart;

    @Override
    public int onInflateLayout() {
        return R.layout.frag_astart;
    }

    @Override
    public void initViewFinish(View inflateView) {
        wdStart.setOriginY(60);
        wdStart.startAnim();
    }

    @Override
    public void onNexts(Object o, View view, String s) {

    }

    @Override
    public boolean onBackPresss() {
        return false;
    }
}
