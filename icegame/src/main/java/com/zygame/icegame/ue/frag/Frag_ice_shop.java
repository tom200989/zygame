package com.zygame.icegame.ue.frag;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hiber.hiber.RootFrag;
import com.zygame.common.component.RootComponent;
import com.zygame.icegame.R;
import com.zygame.icegame.R2;
import com.zygame.icegame.adapter.IceShopAdapter;
import com.zygame.icegame.bean.IceShopBean;
import com.zygame.icegame.helper.FoodMoveHelper;
import com.zygame.icegame.utils.ActionVoiceUtils;
import com.zygame.icegame.utils.IceUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/*
 * Created by Administrator on 2020/6/7.
 */
@SuppressLint("ValidFragment")
@SuppressWarnings(value = {"unchecked", "deprecation"})
public class Frag_ice_shop extends RootFrag {

    @BindView(R2.id.ll_ice_merchant)
    LinearLayout llIceMerchant;// 货架布局
    @BindView(R2.id.rcv_ice_order)
    RecyclerView rcvIceOrder;// 清单列表
    @BindView(R2.id.bt_ice_shop_ok)
    Button btOk;// OK按钮
    @BindView(R2.id.rl_ice_food_seller)
    RelativeLayout rlFoodSeller;// 食物与售货员
    @BindView(R2.id.iv_ice_seller)
    ImageView ivIceSeller;// 售货员
    @BindView(R2.id.iv_ice_food_egg1)
    ImageView ivIceFoodEgg1;// 鸡蛋1
    @BindView(R2.id.iv_ice_food_egg2)
    ImageView ivIceFoodEgg2;// 鸡蛋2
    @BindView(R2.id.iv_ice_food_egg3)
    ImageView ivIceFoodEgg3;// 鸡蛋3
    @BindView(R2.id.iv_ice_food_mianfen1)
    ImageView ivIceFoodMianfen1;// 面粉1
    @BindView(R2.id.iv_ice_food_mianfen2)
    ImageView ivIceFoodMianfen2;// 面粉2
    @BindView(R2.id.iv_ice_food_mianfen3)
    ImageView ivIceFoodMianfen3;// 面粉3
    @BindView(R2.id.iv_ice_food_suannai1)
    ImageView ivIceFoodSuannai1;// 酸奶1
    @BindView(R2.id.iv_ice_food_suannai2)
    ImageView ivIceFoodSuannai2;// 酸奶2
    @BindView(R2.id.iv_ice_food_suannai3)
    ImageView ivIceFoodSuannai3;// 酸奶3
    @BindView(R2.id.iv_ice_nailao1)
    ImageView ivIceNailao1;// 奶酪1
    @BindView(R2.id.iv_ice_nailao2)
    ImageView ivIceNailao2;// 奶酪2
    @BindView(R2.id.iv_ice_nailao3)
    ImageView ivIceNailao3;// 奶酪3
    @BindView(R2.id.iv_ice_milk2_1)
    ImageView ivIceMilk21;// 牛奶2-1
    @BindView(R2.id.iv_ice_milk2_2)
    ImageView ivIceMilk22;// 牛奶2-2
    @BindView(R2.id.iv_ice_milk2_3)
    ImageView ivIceMilk23;// 牛奶2-3
    @BindView(R2.id.iv_ice_milk3)
    ImageView ivIceMilk3;// 牛奶3
    @BindView(R2.id.iv_ice_milk4)
    ImageView ivIceMilk4;// 牛奶4
    @BindView(R2.id.iv_ice_milk5)
    ImageView ivIceMilk5;// 牛奶5
    @BindView(R2.id.iv_ice_milk6)
    ImageView ivIceMilk6;// 牛奶6
    @BindView(R2.id.iv_ice_milk7)
    ImageView ivIceMilk7;// 牛奶7
    @BindView(R2.id.iv_ice_milk8)
    ImageView ivIceMilk8;// 牛奶8

    private ImageView[] foodArr = {};// 存放imageview数组
    private List<IceShopBean> foodls = new ArrayList<>();// 食物集合1
    private HashMap<String, IceShopBean> foodms = new HashMap<>();// 食物集合2 - 用于传递给其他界面 (tag : imageview)
    private IceShopAdapter shopadapter;// 清单适配器

    @Override
    public int onInflateLayout() {
        return R.layout.icegame_frag_ice_shop;
    }

    /**
     * 填充食物iv
     */
    private void setImageview() {
        foodArr = new ImageView[]{// 可移动的食物
                ivIceFoodEgg1,      // 鸡蛋1
                ivIceFoodEgg2,      // 鸡蛋2
                ivIceFoodEgg3,      // 鸡蛋3
                ivIceFoodMianfen1,  // 面粉1
                ivIceFoodMianfen2,  // 面粉2
                ivIceFoodMianfen3,  // 面粉3
                ivIceFoodSuannai1,  // 酸奶1
                ivIceFoodSuannai2,  // 酸奶2
                ivIceFoodSuannai3,  // 酸奶3
                ivIceNailao1,       // 奶酪1
                ivIceNailao2,       // 奶酪2
                ivIceNailao3,       // 奶酪3
                ivIceMilk21,        // 牛奶2-1
                ivIceMilk22,        // 牛奶2-2
                ivIceMilk23,        // 牛奶2-3
                ivIceMilk3,         // 牛奶3
                ivIceMilk4,         // 牛奶4
                ivIceMilk5,         // 牛奶5
                ivIceMilk6,         // 牛奶6
                ivIceMilk7,         // 牛奶7
                ivIceMilk8          // 牛奶8
        };

        // 封装成功映射便于其他页面使用
        for (ImageView ivFood : foodArr) {
            foodms.put((String) ivFood.getTag(), new IceShopBean(ivFood, (String) ivFood.getTag()));
        }
    }

    @Override
    public void initViewFinish(View inflateView) {
        // 填充食物iv
        setImageview();
        // 设置移动监听
        inflateView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // 对食物设置可移动监听器
                setMoveListener();
                // 移除监听
                inflateView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        // 设置清单适配器
        LinearLayoutManager lm = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        rcvIceOrder.setLayoutManager(lm);
        shopadapter = new IceShopAdapter(activity, foodls);
        rcvIceOrder.setAdapter(shopadapter);
        // 设置点击OK监听
        btOk.setOnClickListener(v -> {
            if (foodls.size() <= 0) {
                toast(R.string.common_ice_shop_tip, 6000);
            } else {
                // 释放音频 - 一定要做, 否则出bug
                ActionVoiceUtils.releaseVoice();
                // 跳转到下一个页面 - 并把选中的材料发送过去
                toFrag(getClass(), Frag_ice_work2.class, foodls, true);
            }
        });
    }

    /**
     * 对食物设置可移动监听器
     */
    private void setMoveListener() {
        // 获取售货员的范围 (上下左右各取值一半)
        float seller_x1 = ivIceSeller.getX();
        float seller_y1 = ivIceSeller.getY();
        int seller_w1 = ivIceSeller.getWidth();
        int seller_h1 = ivIceSeller.getHeight();

        for (ImageView iv : foodArr) {
            // 先获取当前控件的位置(x,y)和宽高(w,h)
            float old_x2 = iv.getX();
            float old_y2 = iv.getY();
            int w2 = iv.getWidth();
            int h2 = iv.getHeight();
            // 绑定移动器
            FoodMoveHelper foodMoveHelper = new FoodMoveHelper();
            // 手指抬起时的监听
            foodMoveHelper.setOnTouchUpListener(() -> {
                // 抬起时判断食物是否在售货员手里
                float new_x2 = iv.getX();
                float new_y2 = iv.getY();
                boolean overLap = IceUtils.isOverLap(seller_x1, seller_y1, seller_w1, seller_h1, new_x2, new_y2, w2, h2);

                if (overLap) {// 控件有重叠 - 添加控件到列表
                    // 添加到集合
                    foodls.add(new IceShopBean(iv, (String) iv.getTag()));
                    // 更新适配器
                    shopadapter.notifys(foodls);
                    // 父布局隐藏视图 (并且设置Enable为False以防止再次拖动)
                    iv.setVisibility(View.INVISIBLE);
                    iv.setEnabled(false);
                    // 播放音效
                    ActionVoiceUtils.play(activity, R.raw.action_voice);
                } else {
                    // 恢复到原位
                    iv.setX(old_x2);
                    iv.setY(old_y2);
                }

            });
            foodMoveHelper.bind(iv);
        }
    }

    @Override
    public void onNexts(Object o, View view, String s) {

    }

    @Override
    public boolean isReloadData() {
        return false;
    }

    @Override
    public boolean onBackPresss() {
        // 跳转回原来页面
        ActionVoiceUtils.releaseVoice();
        toFragModule(getClass(), RootComponent.SPLASH_AC, RootComponent.FRAG_MAIN, null, false, getClass());
        return true;
    }

}
