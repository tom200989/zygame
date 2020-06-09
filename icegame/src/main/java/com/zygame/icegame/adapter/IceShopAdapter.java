package com.zygame.icegame.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zygame.icegame.R;
import com.zygame.icegame.bean.IceShopBean;

import java.util.List;

/*
 * Created by Administrator on 2020/6/9.
 */
public class IceShopAdapter extends RecyclerView.Adapter<IceShopHolder> {

    private Context context;
    private List<IceShopBean> iceShopBeans;

    public IceShopAdapter(Context context,List<IceShopBean> iceShopBeans) {
        this.context = context;
        this.iceShopBeans = iceShopBeans;
    }

    public void notifys(List<IceShopBean> iceShopBeans) {
        this.iceShopBeans = iceShopBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IceShopHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new IceShopHolder(LayoutInflater.from(context).inflate(R.layout.icegame_ice_shop_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IceShopHolder holder, int position) {
        IceShopBean iceShopBean = iceShopBeans.get(position);
        holder.ivShopItem.setImageDrawable(iceShopBean.getIvFood().getDrawable());
        holder.tvShopItem.setText(iceShopBean.getFoodTitle());
    }

    @Override
    public int getItemCount() {
        return iceShopBeans.size();
    }
}
