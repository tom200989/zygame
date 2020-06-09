package com.zygame.icegame.utils;

/*
 * Created by Administrator on 2020/6/9.
 */
public class IceUtils {

    /**
     * 判断食物和售货员是否重叠
     *
     * @param x1      view A x 坐标
     * @param y1      view A y 坐标
     * @param w1  view A 宽度
     * @param h1 view A 高度
     * @param x2      view B x 坐标
     * @param y2      view B y 坐标
     * @param w2  view B 宽度
     * @param h2 view B 高度
     * @return T:重叠 F: 没有重叠
     */
    public static boolean isOverLap(float x1, float y1, int w1, int h1,// view A 位置信息
                                     float x2, float y2, int w2, int h2) {// view B 位置信息
        boolean state = true;
        // 以下几种状态都是没有重叠
        if (x1 <= x2 & x1 + w1 <= x2) {
            state = false;

        } else if (x1 >= x2 & x2 + w2 <= x1) {
            state = false;

        } else if (y1 <= y2 & y1 + h1 <= y2) {
            state = false;

        } else if (y1 >= y2 & y2 + h2 <= y1) {
            state = false;
        }

        return state;
    }
}
