package com.zygame.icegame.bean;

import android.graphics.drawable.Drawable;

/*
 * Created by Administrator on 2020/10/020.
 */
public class IceStyleBean {

    public Drawable ivStylePic;// 样式图片
    public String title;// 样式标题
    public boolean isSelected;// 是否选中

    public Drawable getIvStylePic() {
        return ivStylePic;
    }

    public IceStyleBean setIvStylePic(Drawable ivStylePic) {
        this.ivStylePic = ivStylePic;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public IceStyleBean setTitle(String title) {
        this.title = title;
        return this;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public IceStyleBean setSelected(boolean selected) {
        isSelected = selected;
        return this;
    }
}
