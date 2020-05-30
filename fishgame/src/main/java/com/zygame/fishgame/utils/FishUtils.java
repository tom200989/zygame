package com.zygame.fishgame.utils;

import android.widget.ImageView;

import com.zygame.fishgame.R;
import com.zygame.fishgame.widget.FishWidget;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * Created by Administrator on 2020/5/29.
 */
public class FishUtils {


    /**
     * 获取小鱼图元数组
     *
     * @return 小鱼图元数组
     */
    public static int[] getFishRes() {
        // 收集·
        List<Integer> fish_ls = new ArrayList<>();
        fish_ls.add(R.drawable.fish1);
        fish_ls.add(R.drawable.fish2);
        fish_ls.add(R.drawable.fish3);
        fish_ls.add(R.drawable.fish4);
        fish_ls.add(R.drawable.fish5);
        fish_ls.add(R.drawable.fish6);
        fish_ls.add(R.drawable.fish7);
        fish_ls.add(R.drawable.fish8);
        fish_ls.add(R.drawable.fish9);
        fish_ls.add(R.drawable.fish10);
        fish_ls.add(R.drawable.fish11);
        fish_ls.add(R.drawable.fish12);
        fish_ls.add(R.drawable.fish13);
        fish_ls.add(R.drawable.fish14);
        fish_ls.add(R.drawable.fish15);
        // 转换
        int[] fish_arr = new int[fish_ls.size()];
        for (int i = 0; i < fish_arr.length; i++) {
            fish_arr[i] = fish_ls.get(i);
        }
        // 返回
        return fish_arr;
    }

    /**
     * 随机获取小鱼图元
     *
     * @param fishArr 小鱼图元数组
     * @return 小鱼图元
     */
    public static int getRandomFishRes(int[] fishArr) {
        Random random = new Random();
        int ids = random.nextInt(fishArr.length);
        return fishArr[ids];
    }

    /**
     * @return 随机获取鱼头朝向 (用于决定鱼头朝向和进入的边侧)
     */
    public static FishWidget.DirectEnum getRandomHeadDirection() {
        Random random = new Random();
        int ids = random.nextInt(2);
        return ids == FishWidget.DirectEnum.HEAD_RIGHT.type ? FishWidget.DirectEnum.HEAD_RIGHT : FishWidget.DirectEnum.HEAD_LEFT;
    }

    /**
     * 获取随机Y值
     *
     * @param totalY     总Y值
     * @param piece_self 外部可以设置分层数(默认为6层)
     * @return 随机的Y值
     */
    public static int getRandomY(int totalY, Integer... piece_self) {
        // 把鱼塘分为6等分
        int piece = 6;
        if (piece_self.length > 0) {
            if (piece_self[0] < 0) {// 最小0块 - 则默认Y为100
                piece = 0;
            } else if (piece_self[0] >= 7) {// 最大7块
                piece = 7;
            }
        }
        int avenger = totalY / piece;
        Random random = new Random();
        int ids = random.nextInt(piece);
        // 得到当前Y坐标
        int nowY = avenger * ids;
        // 设置最小值
        if (nowY <= 0) {
            nowY = 100;
        }
        return nowY;
    }

    /**
     * @return 获取小鱼的随机步长
     */
    public static int getRandomStep() {
        Random random = new Random();
        int ids = random.nextInt(12);
        if (ids < 5) {
            ids = 5;
        }
        return ids;
    }

    /**
     * 判断鱼钩和小鱼是否碰撞上钩
     *
     * @param yugou 鱼钩
     * @param fish  小鱼
     * @return T:上钩
     */
    public static boolean isFishAndYuGouOverLap(ImageView yugou, FishWidget fish) {
        // 鱼钩位置
        float x1 = yugou.getX();
        float y1 = yugou.getY();
        int w1 = yugou.getWidth();
        int h1 = yugou.getHeight();
        // 小鱼位置
        float x2 = fish.getX();
        float y2 = fish.getY();
        int w2 = fish.getWidth();
        int h2 = fish.getHeight();
        // 判断是否碰撞上钩
        return isOverLap(x1, y1, w1, h1, x2, y2, w2, h2);
    }

    /**
     * 判断鱼钩和小鱼是否重叠 (由小鱼的线程分别判断)
     *
     * @param x1      view A x 坐标
     * @param y1      view A y 坐标
     * @param width1  view A 宽度
     * @param height1 view A 高度
     * @param x2      view B x 坐标
     * @param y2      view B y 坐标
     * @param width2  view B 宽度
     * @param height2 view B 高度
     * @return T:重叠 F: 没有重叠
     */
    private static boolean isOverLap(float x1, float y1, int width1, int height1,// view A 位置信息
                                     float x2, float y2, int width2, int height2) {// view B 位置信息
        boolean state = true;
        // 以下几种状态都是没有重叠
        if (x1 <= x2 & x1 + width1 <= x2) {
            state = false;

        } else if (x1 >= x2 & x2 + width2 <= x1) {
            state = false;

        } else if (y1 <= y2 & y1 + height1 <= y2) {
            state = false;

        } else if (y1 >= y2 & y2 + height2 <= y1) {
            state = false;
        }

        return state;
    }


}
