package com.zygame.icegame.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zygame.icegame.R;
import com.zygame.icegame.widget.BlowWidget;

/*
 * Created by Administrator on 2020/10/020.
 */
public class IceStyleHolder extends RecyclerView.ViewHolder {

    public ImageView ivStylePic;// 样式图片
    public BlowWidget bwStyle;// 气泡
    public ImageView ivAlpha;// 选中蒙版
    public TextView tvStyleTitle;// 样式标题

    public IceStyleHolder(@NonNull View itemView) {
        super(itemView);
        ivStylePic = itemView.findViewById(R.id.iv_ice_style_item_btn_stylePic);
        bwStyle = itemView.findViewById(R.id.bw_ice_style_effect);
        ivAlpha = itemView.findViewById(R.id.iv_ice_style_item_alpha);
        tvStyleTitle = itemView.findViewById(R.id.tv_ice_style_item_styleTitle);
    }
}
