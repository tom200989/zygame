package com.zygame.icegame.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zygame.icegame.R;

/*
 * Created by Administrator on 2020/6/9.
 */
public class IceShopHolder extends RecyclerView.ViewHolder {


    public ImageView ivShopItem;// 图标
    public TextView tvShopItem;// 标题

    public IceShopHolder(@NonNull View itemView) {
        super(itemView);
        ivShopItem = itemView.findViewById(R.id.iv_ice_shop_item);
        tvShopItem = itemView.findViewById(R.id.tv_ice_shop_item);
    }
}
