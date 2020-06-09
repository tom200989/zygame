package com.zygame.icegame.bean;

import android.widget.ImageView;

/*
 * Created by Administrator on 2020/6/9.
 */
public class IceShopBean {

    private ImageView ivFood;// 食物图标
    private String foodTitle;// 食物标题

    public IceShopBean(ImageView ivFood, String foodTitle) {
        this.ivFood = ivFood;
        this.foodTitle = foodTitle;
    }

    public String getFoodTitle() {
        return foodTitle;
    }

    public void setFoodTitle(String foodTitle) {
        this.foodTitle = foodTitle;
    }

    public ImageView getIvFood() {
        return ivFood;
    }

    public void setIvFood(ImageView ivFood) {
        this.ivFood = ivFood;
    }

}
