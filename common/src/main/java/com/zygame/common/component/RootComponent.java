package com.zygame.common.component;

/*
 * Created by qianli.ma on 2019/3/13 0013.
 * 用于存放module间跳转的组件类
 *
 * update by xiaoke.luo@tcl.com 2019/5/22 18:43
 * 修改固件升级页面的路径
 *
 * update by xiaoke.luo@tcl.com 2019/7/16 13:14
 * 添加40的联系人列表的路径
 */
public class RootComponent {

    public static String SPLASH_AC = "com.zygame.zygame.ue.activity.SplashActivity";
    public static String FRAG_MAIN = "com.zygame.zygame.ue.frag.Frag_main";

    public static String FISH_AC = "com.zygame.fishgame.ue.activity.FishActivity";
    public static String FRAG_FISH = "com.zygame.fishgame.ue.frag.Frag_fish";

    public static String ICE_AC = "com.zygame.icegame.ue.activity.IceActivity";
    public static String FRAG_ICE = "com.zygame.icegame.ue.frag.Frag_ice";
    public static String FRAG_ICE_SHOP = "com.zygame.icegame.ue.frag.Frag_ice_shop";
    public static String FRAG_ICE_WORK = "com.zygame.icegame.ue.frag.Frag_ice_work";

    public static String SP_NAME = "ZYApp";// sp文件名
    public static String PREVENTION_TIME = "PREVENTION_TIME";// 防沉迷标记 (Value为json)
    public static long PREVENT_CLEAR_TIME = 6 * 60 * 60 * 1000;// 防沉迷每日清零时长 (6小时)
    public static long PREVENT_INIT_TOTAL_PERMIT = 2 * 60 * 60 * 1000;// 防沉迷每日可以玩时长 (2小时)

}
