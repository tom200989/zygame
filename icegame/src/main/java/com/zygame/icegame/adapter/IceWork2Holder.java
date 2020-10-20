package com.zygame.icegame.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zygame.icegame.R;
import com.zygame.icegame.widget.BlowWidget;

/*
 * Created by Administrator on 2020/10/019.
 */
public class IceWork2Holder extends RecyclerView.ViewHolder {

    public ImageView ivFoodContainer;// 菜容器
    public TextView tvFoodTitle;// 菜名
    public BlowWidget bwEffect;// 气泡特效

    public IceWork2Holder(@NonNull View itemView) {
        super(itemView);
        ivFoodContainer = itemView.findViewById(R.id.iv_ice_item_btn_foodPic);
        tvFoodTitle = itemView.findViewById(R.id.tv_ice_item_foodTitle);
        bwEffect = itemView.findViewById(R.id.bw_ice_effect);
    }
}
