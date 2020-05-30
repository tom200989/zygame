package com.zygame.icegame.ue.frag;

import android.view.View;

import com.hiber.hiber.RootFrag;
import com.zygame.icegame.R;

/*
 * Created by qianli.ma on 2020/5/6 0006.
 */
public class Frag_ice extends RootFrag {
    @Override
    public int onInflateLayout() {
        return R.layout.icegame_frag_ice;
    }

    @Override
    public void onNexts(Object o, View view, String s) {

    }

    @Override
    public boolean onBackPresss() {
        return false;
    }
}
