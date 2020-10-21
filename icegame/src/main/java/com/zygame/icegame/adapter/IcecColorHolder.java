package com.zygame.icegame.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.zygame.icegame.R;
import com.zygame.icegame.widget.BlowWidget;

/*
 * Created by Administrator on 2020/10/020.
 */
public class IcecColorHolder extends RecyclerView.ViewHolder {

    public ImageView ivStylePic;// 颜色图片
    public BlowWidget bwStyle;// 气泡

    public IcecColorHolder(@NonNull View itemView) {
        super(itemView);
        ivStylePic = itemView.findViewById(R.id.iv_ice_color_item_btn_stylePic);
        bwStyle = itemView.findViewById(R.id.bw_ice_color_effect);
    }
}
