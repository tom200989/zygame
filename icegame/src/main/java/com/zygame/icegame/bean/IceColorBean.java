package com.zygame.icegame.bean;

import android.graphics.drawable.Drawable;

/*
 * Created by Administrator on 2020/10/020.
 */
public class IceColorBean {

    public Drawable ivStylePic;// 颜色图片
    public int colorRes;// 颜色资源

    public Drawable getIvStylePic() {
        return ivStylePic;
    }

    public IceColorBean setIvStylePic(Drawable ivStylePic) {
        this.ivStylePic = ivStylePic;
        return this;
    }

    public int getColorRes() {
        return colorRes;
    }

    public IceColorBean setColorRes(int colorRes) {
        this.colorRes = colorRes;
        return this;
    }
}
