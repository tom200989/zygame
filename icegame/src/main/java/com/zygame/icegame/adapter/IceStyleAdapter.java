package com.zygame.icegame.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zygame.icegame.R;
import com.zygame.icegame.bean.IceStyleBean;
import com.zygame.icegame.widget.BlowWidget;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by Administrator on 2020/10/020.
 */
@SuppressWarnings(value = {"unchecked", "deprecation"})
public class IceStyleAdapter extends RecyclerView.Adapter<IceStyleHolder> {

    private Context context;
    private List<IceStyleBean> styleBeans = new ArrayList<>();

    public IceStyleAdapter(Context context) {
        this.context = context;
        initRes();// 加载资源
    }

    /**
     * 加载资源
     */
    private void initRes() {

        int[] ivs = new int[]{// 样式图片
                R.drawable.ice_decorate_1_icon,// 雪糕花
                R.drawable.ice_decorate_2_icon,// 巧克力
                R.drawable.ice_decorate_3_icon,// 奶油
                R.drawable.ice_decorate_4_icon,// 雪糕筒
                R.drawable.ice_decorate_5_icon,// 雪糕带
                R.drawable.ice_decorate_6_icon,// 雪糕边
                R.drawable.ice_decorate_7_icon,// 雪糕线
        };

        int[] tvs = new int[]{// 样式标题
                R.string.common_ice_style_title1,// 雪糕花 
                R.string.common_ice_style_title2,// 巧克力 
                R.string.common_ice_style_title3,// 奶油  
                R.string.common_ice_style_title4,// 雪糕筒 
                R.string.common_ice_style_title5,// 雪糕带 
                R.string.common_ice_style_title6,// 雪糕边 
                R.string.common_ice_style_title7,// 雪糕线 
        };

        // 封装
        for (int i = 0; i < ivs.length; i++) {
            IceStyleBean iceStyleBean = new IceStyleBean();
            iceStyleBean.setIvStylePic(context.getDrawable(ivs[i]));
            iceStyleBean.setTitle(context.getString(tvs[i]));
            iceStyleBean.setSelected(false);
            styleBeans.add(iceStyleBean);
        }
    }

    private void notifys() {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IceStyleHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new IceStyleHolder(LayoutInflater.from(context).inflate(R.layout.icegame_item_style, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IceStyleHolder holder, int position) {
        IceStyleBean iceStyleBean = styleBeans.get(position);
        ImageView ivStylePic = holder.ivStylePic;
        ImageView ivAlpha = holder.ivAlpha;
        BlowWidget bwEffect = holder.bwStyle;
        TextView tvStyleTitle = holder.tvStyleTitle;
        // 设置属性
        ivStylePic.setBackground(iceStyleBean.getIvStylePic());
        ivAlpha.setVisibility(iceStyleBean.isSelected ? View.VISIBLE : View.GONE);
        tvStyleTitle.setText(iceStyleBean.getTitle());
        // 点击
        ivStylePic.setOnClickListener(v -> {
            bwEffect.setVisibility(View.VISIBLE);
            bwEffect.activite();// 显示气泡
            // 选中并刷新
            for (int i = 0; i < styleBeans.size(); i++) {
                styleBeans.get(i).setSelected(i == position);
            }
            ClickStyelItemNext(position);
            new Handler().postDelayed(() -> {
                bwEffect.setVisibility(View.GONE);
                notifys();
            }, 700);


        });
    }

    @Override
    public int getItemCount() {
        return styleBeans.size();
    }

    // ---------------- 监听器 [ClickStyelItem] ----------------
    private OnClickStyelItemListener onClickStyelItemListener;

    // Interface--> 接口: OnClickStyelItemListener
    public interface OnClickStyelItemListener {
        void ClickStyelItem(int position);
    }

    // 对外方式: setOnClickStyelItemListener
    public void setOnClickStyelItemListener(OnClickStyelItemListener onClickStyelItemListener) {
        this.onClickStyelItemListener = onClickStyelItemListener;
    }

    // 封装方法: ClickStyelItemNext
    private void ClickStyelItemNext(int position) {
        if (onClickStyelItemListener != null) {
            onClickStyelItemListener.ClickStyelItem(position);
        }
    }
}
