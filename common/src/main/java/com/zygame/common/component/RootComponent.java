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

    public static String SP_NAME = "ZYApp";// sp文件名
    public static String SETTING_PLAY_TIME = "SETTING_PLAY_TIME";// 玩耍时长缓存标记
    public static String SETTING_REST_TIME = "SETTING_REST_TIME";// 休息时长缓存标记
    public static String SETTING_LAST_TIME = "SETTING_LAST_TIME";// 上次时长缓存标记

    public static long SETTING_DEFAULT_PLAY_MIN = 1;// 默认玩耍时长:min
    public static long SETTING_DEFAULT_REST_MIN = 1;// 默认休息时长:min
    public static long SETTING_DEFAULT_PLAY_DURATION = SETTING_DEFAULT_PLAY_MIN * 60L * 1000L;// 默认玩耍时长:ms
    public static long SETTING_DEFAULT_REST_DURATION = SETTING_DEFAULT_REST_MIN * 60L * 1000L;// 默认休息时长:ms
}
