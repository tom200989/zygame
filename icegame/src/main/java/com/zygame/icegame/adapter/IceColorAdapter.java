package com.zygame.icegame.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zygame.icegame.R;
import com.zygame.icegame.bean.IceColorBean;
import com.zygame.icegame.widget.BlowWidget;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by Administrator on 2020/10/021.
 */
@SuppressWarnings(value = {"unchecked", "deprecation"})
public class IceColorAdapter extends RecyclerView.Adapter<IcecColorHolder> {

    private Context context;
    private List<IceColorBean> colorBeans = new ArrayList<>();

    public IceColorAdapter(Context context) {
        this.context = context;
        initRes();
    }

    private void initRes() {

        int[] ivRes = new int[]{// 颜色图片
                R.drawable.ice_color_00c6ff,// 00c6ff
                R.drawable.ice_color_00ffe4,// 00ffe4
                R.drawable.ice_color_06ff00,// 06ff00
                R.drawable.ice_color_0084ff,// 0084ff
                R.drawable.ice_color_7800ff,// 7800ff
                R.drawable.ice_color_a8ff00,// a8ff00
                R.drawable.ice_color_a65757,// a65757
                R.drawable.ice_color_c600ff,// c600ff
                R.drawable.ice_color_feff80,// feff80
                R.drawable.ice_color_ff9c9c,// ff9c9c
                R.drawable.ice_color_ff6600,// ff6600
                R.drawable.ice_color_ffa86e,// ffa86e
        };

        int[] colorStrs = new int[]{// 颜色资源
                R.color.common_colorIce_00C6FF,// 00c6ff      
                R.color.common_colorIce_00FFE4,// 00ffe4      
                R.color.common_colorIce_06FF00,// 06ff00      
                R.color.common_colorIce_0084FF,// 0084ff      
                R.color.common_colorIce_7800FF,// 7800ff      
                R.color.common_colorIce_A8FF00,// a8ff00      
                R.color.common_colorIce_A65757,// a65757      
                R.color.common_colorIce_C600FF,// c600ff      
                R.color.common_colorIce_FEFF80,// feff80      
                R.color.common_colorIce_FF9C9C,// ff9c9c      
                R.color.common_colorIce_FF6600,// ff6600      
                R.color.common_colorIce_FFA86E,// ffa86e      
        };

        // 封装
        for (int i = 0; i < ivRes.length; i++) {
            IceColorBean iceColorBean = new IceColorBean();
            iceColorBean.setIvStylePic(context.getDrawable(ivRes[i]));
            iceColorBean.setColorRes(colorStrs[i]);
            colorBeans.add(iceColorBean);
        }
    }

    @NonNull
    @Override
    public IcecColorHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new IcecColorHolder(LayoutInflater.from(context).inflate(R.layout.icegame_item_color, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IcecColorHolder holder, int i) {
        // 控件属性
        ImageView ivStylePic = holder.ivStylePic;
        BlowWidget bwColor = holder.bwStyle;
        // 设置
        IceColorBean iceColorBean = colorBeans.get(i);
        ivStylePic.setBackground(iceColorBean.getIvStylePic());
        ivStylePic.setOnClickListener(v -> {
            bwColor.setVisibility(View.VISIBLE);
            bwColor.activite();// 显示气泡
            ClickColorItemNext(context.getResources().getColor(iceColorBean.getColorRes()));// 回调
            new Handler().postDelayed(() -> bwColor.setVisibility(View.GONE), 700);
        });
    }

    @Override
    public int getItemCount() {
        return colorBeans.size();
    }

    // ---------------- 监听器 [ClickColorItem] ----------------
    private OnClickColorItemListener onClickColorItemListener;

    // Interface--> 接口: OnClickColorItemListener
    public interface OnClickColorItemListener {
        void ClickColorItem(int colorRes);
    }

    // 对外方式: setOnClickColorItemListener
    public void setOnClickColorItemListener(OnClickColorItemListener onClickColorItemListener) {
        this.onClickColorItemListener = onClickColorItemListener;
    }

    // 封装方法: ClickColorItemNext
    private void ClickColorItemNext(int colorRes) {
        if (onClickColorItemListener != null) {
            onClickColorItemListener.ClickColorItem(colorRes);
        }
    }
}
