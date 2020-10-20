package com.zygame.icegame.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zygame.icegame.R;
import com.zygame.icegame.bean.IceShopBean;
import com.zygame.icegame.widget.BlowWidget;

import java.util.List;

/*
 * Created by Administrator on 2020/10/019.
 */
public class IceWork2Adapter extends RecyclerView.Adapter<IceWork2Holder> {

    private Context context;
    private List<IceShopBean> iceShopBeans;
    private int count;// 临时计数器 - 用于记录点击菜单的次数(如果次数等于菜单个数, 允许外部点击雪糕机)

    public IceWork2Adapter(Context context, List<IceShopBean> iceShopBeans) {
        this.context = context;
        this.iceShopBeans = iceShopBeans;
    }

    public void notifys(List<IceShopBean> iceShopBeans) {
        this.iceShopBeans = iceShopBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IceWork2Holder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new IceWork2Holder(LayoutInflater.from(context).inflate(R.layout.icegame_item_work_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IceWork2Holder holder, int position) {
        // 获取菜单数据
        IceShopBean iceShopBean = iceShopBeans.get(position);
        String foodTitle = iceShopBean.getFoodTitle();
        Drawable drawPic = iceShopBean.getIvFood().getDrawable();
        // 获取视图
        ImageView ivFoodPic = holder.ivFoodContainer;
        TextView tvOrderName = holder.tvFoodTitle;
        BlowWidget bwEffect = holder.bwEffect;
        // 绑定
        tvOrderName.setText(foodTitle);// 菜名
        ivFoodPic.setImageDrawable(drawPic);
        // 点击
        ivFoodPic.setOnClickListener(v -> {
            bwEffect.setVisibility(View.VISIBLE);// 显示特效
            bwEffect.activite();// 播放
            ivFoodPic.postDelayed(() -> ivFoodPic.setVisibility(View.GONE), 700);// 隐藏当前图片
            count++;
        });
    }

    @Override
    public int getItemCount() {
        return iceShopBeans != null ? iceShopBeans.size() : 0;
    }

    /**
     * 是否全部菜单被点击
     */
    public boolean isClickAll() {
        // 大于等于菜单数量
        if (count >= iceShopBeans.size()) {
            count = 0;// 重置
            return true;
        }
        return false;
    }
}
