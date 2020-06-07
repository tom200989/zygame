package com.zygame.common.helper;

import com.alibaba.fastjson.JSONObject;
import com.hiber.tools.ShareUtils;
import com.zygame.common.bean.PreventBean;
import com.zygame.common.component.RootComponent;

import static com.zygame.common.component.RootComponent.PREVENT_INIT_TOTAL_PERMIT;

/*
 * Created by Administrator on 2020/6/5.
 *
 * 防沉迷辅助
 */
public class PreventHelper {

    /**
     * 初始化防沉迷数据缓存
     */
    public static void initPrevent() {
        // 创建防沉迷对象
        PreventBean preventBean = new PreventBean();
        preventBean.setTotalPermitDuration(PREVENT_INIT_TOTAL_PERMIT);
        preventBean.setLastRecordTime(0);
        preventBean.setTotalDuration(0);
        // 缓存
        setPrevent(preventBean);
    }

    /**
     * @return 获取防沉迷数据
     */
    public static PreventBean getPrevent() {
        String aNull = "null";
        String timeJson = ShareUtils.get(RootComponent.PREVENTION_TIME, aNull);
        if (!timeJson.equalsIgnoreCase(aNull)) {
            return JSONObject.parseObject(timeJson, PreventBean.class);
        } else {
            return null;
        }
    }

    /**
     * 设置防沉迷数据
     *
     * @param preventBean 防沉迷数据
     */
    private static void setPrevent(PreventBean preventBean) {
        ShareUtils.set(RootComponent.PREVENTION_TIME, JSONObject.toJSONString(preventBean));
    }

    /* -------------------------------------------- impl 0 current time -------------------------------------------- */

    /**
     * @return 获取当前时间
     */
    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    /* -------------------------------------------- impl 3 total -------------------------------------------- */

    /**
     * 设置一共玩了的时长
     *
     * @param totalDuration 一共玩了的时长
     */
    public static void setTotalDuration(long totalDuration) {
        // 先获取原先的对象
        PreventBean prevent = getPrevent();
        if (prevent != null) {
            // 再修改对应的值
            prevent.setTotalDuration(totalDuration);
            setPrevent(prevent);
        } else {
            System.err.println("防沉迷［一共玩了的时长］设置失败");
        }
    }

    /**
     * @return 获取一共玩了的时长(毫秒)
     */
    public static long getTotalDuration() {
        String aNull = "null";
        String json = ShareUtils.get(RootComponent.PREVENTION_TIME, aNull);
        if (!json.equalsIgnoreCase(aNull)) {
            return JSONObject.parseObject(json, PreventBean.class).getTotalDuration();
        }
        return -1;
    }

    /**
     * @return 获取一共玩了的时长(分钟)
     */
    public static long getTotalDuration_Min() {
        String aNull = "null";
        String json = ShareUtils.get(RootComponent.PREVENTION_TIME, aNull);
        if (!json.equalsIgnoreCase(aNull)) {
            return JSONObject.parseObject(json, PreventBean.class).getTotalDuration_Min();
        }
        return -1;
    }


    /**
     * @return 获取一共玩了的时长(分钟字符)
     */
    public static String getTotalDuration_Min_Str() {
        String aNull = "null";
        String json = ShareUtils.get(RootComponent.PREVENTION_TIME, aNull);
        if (!json.equalsIgnoreCase(aNull)) {
            return String.valueOf(JSONObject.parseObject(json, PreventBean.class).getTotalDuration_Min());
        }
        return "-1";
    }

    /* -------------------------------------------- impl 3 last record time -------------------------------------------- */

    /**
     * 设置最后一次记录［总玩］时长的时间
     *
     * @param lastRecordTime 最后一次记录［总玩］时长的时间
     */
    public static void setLastRecordTime(long lastRecordTime) {
        // 先获取原先的对象
        PreventBean prevent = getPrevent();
        if (prevent != null) {
            // 再修改对应的值
            prevent.setLastRecordTime(lastRecordTime);
            setPrevent(prevent);
        } else {
            System.err.println("防沉迷［最后一次记录［总玩］时长的时间］设置失败");
        }
    }

    /**
     * @return 获取最后一次记录［总玩］时长的时间(ms)
     */
    public static long getLastRecordTime() {
        String aNull = "null";
        String json = ShareUtils.get(RootComponent.PREVENTION_TIME, aNull);
        if (!json.equalsIgnoreCase(aNull)) {
            return JSONObject.parseObject(json, PreventBean.class).getLastRecordTime();
        }
        return -1;
    }

    /* -------------------------------------------- impl 4 total permit play -------------------------------------------- */

    /**
     * 设置一共玩了的时长
     *
     * @param totalPermitDuration 一共玩了的时长
     */
    public static void setTotalPermitDuration(long totalPermitDuration) {
        // 先获取原先的对象
        PreventBean prevent = getPrevent();
        if (prevent != null) {
            // 再修改对应的值
            prevent.setTotalPermitDuration(totalPermitDuration);
            setPrevent(prevent);
        } else {
            System.err.println("防沉迷［一共玩了的时长］设置失败");
        }
    }

    /**
     * @return 获取可允许玩耍时长(毫秒)
     */
    public static long getTotalPermitDuration() {
        String aNull = "null";
        String json = ShareUtils.get(RootComponent.PREVENTION_TIME, aNull);
        if (!json.equalsIgnoreCase(aNull)) {
            return JSONObject.parseObject(json, PreventBean.class).getTotalPermitDuration();
        }
        return -1;
    }

    /**
     * @return 获取可允许玩耍时长(分钟)
     */
    public static long getTotalPermitDuration_Min() {
        String aNull = "null";
        String json = ShareUtils.get(RootComponent.PREVENTION_TIME, aNull);
        if (!json.equalsIgnoreCase(aNull)) {
            return JSONObject.parseObject(json, PreventBean.class).getTotalPermitDuration_Min();
        }
        return -1;
    }

    /**
     * @return 获取可允许玩耍时长(分钟字符)
     */
    public static String getTotalPermitDuration_Min_Str() {
        String aNull = "null";
        String json = ShareUtils.get(RootComponent.PREVENTION_TIME, aNull);
        if (!json.equalsIgnoreCase(aNull)) {
            return String.valueOf(JSONObject.parseObject(json, PreventBean.class).getTotalPermitDuration_Min());
        }
        return "-1";
    }

}
