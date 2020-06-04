package com.zygame.fishgame.ue.frag;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.de.wave.core.WaveView;
import com.hiber.cons.TimerState;
import com.hiber.hiber.RootFrag;
import com.hiber.tools.ScreenSize;
import com.hiber.tools.ShareUtils;
import com.hiber.tools.TimerHelper;
import com.nineoldandroids.view.ViewHelper;
import com.zygame.common.component.RootComponent;
import com.zygame.fishgame.R;
import com.zygame.fishgame.R2;
import com.zygame.fishgame.helper.BearMoveHelper;
import com.zygame.fishgame.helper.BearShootHelper;
import com.zygame.fishgame.utils.FishUtils;
import com.zygame.fishgame.utils.ThreadPoolFactory;
import com.zygame.fishgame.utils.ThreadPoolProxy;
import com.zygame.fishgame.widget.BlowWidget;
import com.zygame.fishgame.widget.FishWidget;
import com.zygame.fishgame.widget.GoalFinishWidget;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/*
 * Created by qianli.ma on 2020/5/6 0006.
 */
@SuppressLint("ClickableViewAccessibility")
@SuppressWarnings(value = {"unchecked", "deprecation"})
public class Frag_fish extends RootFrag {

    @BindView(R2.id.rl_sprite_bear)
    RelativeLayout rlSpriteBear;// 熊二
    @BindView(R2.id.iv_main_yugou)
    ImageView ivMainYugou;// 鱼钩
    @BindView(R2.id.bw_main_blow)
    BlowWidget bwMainBlow;// 气泡
    @BindView(R2.id.wv_main_waveborder)
    WaveView waveView;// 波浪
    @BindView(R2.id.iv_main_yuxian)
    ImageView ivMainYuXian;// 鱼线
    @BindView(R2.id.iv_main_exit)
    ImageView ivMainExit;// 退出按钮
    @BindView(R2.id.iv_main_shoot)
    ImageView ivMainShoot;// 钓鱼按钮
    @BindView(R2.id.tv_main_goal)
    TextView tvMainGoal;// 分数
    @BindView(R2.id.rl_main_fishArea)
    RelativeLayout rlMainFishArea;// 捕鱼区
    @BindView(R2.id.gw_main_finish)
    GoalFinishWidget gwMainFinish;// 退出面板

    private BearMoveHelper bearMoveHelper;// 手势移动器
    private BearShootHelper bearShootHelper;// 钓鱼按压器
    private shootRunable shootRunable;// 钓鱼线程
    private ThreadPoolProxy threadPoolProxy;// 线程池代理

    private float yugouY = 0;// 当前鱼钩Y坐标
    private float yugouInitY = -1;// 鱼钩的初始Y值
    private int screenWidth;// 屏幕宽度
    private int screenHeight;// 屏幕高度
    private int yugou_step = 10;// 鱼钩下掉步长
    private int yugou_duration = 15;// 鱼钩下掉间隔时长
    private int fish_duration = 15;// 小鱼滑动间隔时长
    private int[] fishRes;// 小鱼资源
    private long topTime;// 鱼钩回到顶部的时间
    private List<Integer> goal_ls = new ArrayList<>();// 分数集合
    private boolean goalFlag = true;// 控制分数线程退出循环的标记
    private int curGoal = 0;// 当前分数, 默认从0开始
    private DecimalFormat decimalFormat;// 分数格式化对象
    private TimerHelper goalTimer;
    private MediaPlayer bgVoice;
    private MediaPlayer fishVoice;

    @Override
    public int onInflateLayout() {
        return R.layout.fishgame_frag_fish;
    }

    @Override
    public void initViewFinish(View inflateView) {
        initRes();// 初始化资源
        initAttr();// 初始化属性
        initView();// 初始化视图
        initEvent();// 初始化事件
    }

    /**
     * 初始化资源
     */
    private void initRes() {
        // 初始化小鱼资源
        fishRes = FishUtils.getFishRes();
    }

    /**
     * 初始化属性
     */
    private void initAttr() {
        // 把当前时间存入lasttime
        ShareUtils.set(RootComponent.SETTING_LAST_TIME, System.currentTimeMillis());
        // 播放背景音乐
        bgVoice = getBgVoice(true);
        // 设置定时器
        timer_period = 2000;
        // 启动线程池
        threadPoolProxy = ThreadPoolFactory.getPoolProxy(20, 30);
        // 获取屏幕宽高
        screenWidth = ScreenSize.getSize(activity).width;
        screenHeight = ScreenSize.getSize(activity).height;
    }

    /**
     * 初始化视图
     */
    private void initView() {
        // 初始化分数
        setTvGoal(curGoal);
    }

    /**
     * 初始化事件
     */
    private void initEvent() {

        // 设置手势移动监听器
        bearMoveHelper = new BearMoveHelper(activity);
        bearMoveHelper.bind(rlMainFishArea, rlSpriteBear, ivMainYugou, bwMainBlow, ivMainYuXian);

        // 设置钓鱼监听
        bearShootHelper = new BearShootHelper(activity);
        bearShootHelper.setOnBearMouseDownListener(() -> handleThreadState(true));// 鱼钩下掉
        bearShootHelper.setOnBearMouseUpListener(() -> handleThreadState(false));// 鱼钩上拉
        bearShootHelper.bind(ivMainShoot);

        // 退出按钮
        ivMainExit.setOnClickListener(v -> onBackPresss());

        // 退出面板
        gwMainFinish.setOnCLickGoalFinishListener(this::onBackPresss);

    }

    @Override
    public void onNexts(Object o, View view, String s) {
        // 开启定时器
        timerState = TimerState.ON_BUT_OFF_WHEN_HIDE_AND_PAUSE;
        // 启动防沉迷线程 - todo 暂时取消
        // threadPoolProxy.executeTask(new RestRunable());
        // 开启分数观察线程
        threadPoolProxy.executeTask(new GoalRunnable());
    }

    @Override
    public boolean onBackPresss() {
        // 停止音乐
        if (bgVoice != null) {
            bgVoice.pause();
            bgVoice.stop();
        }
        if (fishVoice != null) {
            fishVoice.pause();
            fishVoice.stop();
        }
        // 结束分数循环
        goalFlag = false;
        // 停止全部线程
        threadPoolProxy.shutAll();
        // 并跳转到主页
        toFragModule(getClass(), RootComponent.SPLASH_AC, RootComponent.FRAG_MAIN, null, false, getClass());
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onBackPressed();
    }

    /* -------------------------------------------- private -------------------------------------------- */

    /**
     * 处理鱼钩线程
     *
     * @param isActionDown T: 手指按下 F:手指弹起
     */
    private void handleThreadState(boolean isActionDown) {
        if (isActionDown) {/* 手指按下 */
            // 检测上一次鱼钩回到顶部的时间 > 500ms
            if (System.currentTimeMillis() - topTime < 500) {
                return;
            }
            // 获取鱼钩的初始Y值
            if (yugouInitY == -1) {
                yugouY = yugouInitY = ivMainYugou.getY();
            }
            // 限制熊二在鱼钩下掉时不能移动
            bearMoveHelper.setlock(true);

            // 停止当前线程, 再创建线程 - 该目的是为了在反复点击时避免线程溢出
            shootRunable = new shootRunable();
            // 设置线程进入轮询启动预备
            shootRunable.canThreadRun = true;
            // 设置鱼钩模式为［下掉］
            shootRunable.needDown = true;
            // 启动线程(加入最大数量判断 - 防止溢出)
            threadPoolProxy.executeTask(shootRunable);

        } else {/* 手指弹起 */
            // 设置鱼钩模式为［上滑］
            shootRunable.needDown = false;
            // 当鱼钩的位置 > 初始值位置时 (即鱼钩在上滑过程中) - 才允许上锁, 否则会导致鱼钩回到了顶端, 用户手指还没离开按钮, 导致鱼钩无法再次下掉
            if (ivMainYugou.getY() > yugouInitY) {
                // 鱼钩上锁 - 此时不允许鱼钩下掉的操作, 否则线程溢出
                bearShootHelper.setlock(true);
            }
        }
    }

    /**
     * 设置分数 (用于初始化和分数变化时)
     *
     * @param goal 分数
     */
    private void setTvGoal(int goal) {
        decimalFormat = new DecimalFormat("0000000");
        tvMainGoal.setText(decimalFormat.format(goal));
    }


    @Override
    public void setTimerTask() {
        // 定时每隔2秒生成一条鱼
        randomCreatFish();
    }

    /**
     * 随机生成小鱼
     */
    @SuppressLint("ResourceType")
    private void randomCreatFish() {
        // TOAT: 先判断波浪是否加载完毕 (此步骤为必要步骤, 否则, 当小鱼线程启动时, 小鱼相对屏幕的坐标可能还获取不到)
        float waveViewPosY = waveView.getY() + waveView.getHeight();
        if (waveViewPosY == 0) {
            return;
        }
        // 随机获取小鱼图元
        int fish_res = FishUtils.getRandomFishRes(fishRes);
        // 随机获取小鱼进入方向
        FishWidget.DirectEnum directEnum = FishUtils.getRandomHeadDirection();
        // 随机获取小鱼的Y方向值
        int randomY = (int) (FishUtils.getRandomY((int) (screenHeight * 0.55f), 5) + waveViewPosY);
        // 随机获取小鱼速度步长
        int step = FishUtils.getRandomStep();
        // 随机创建鱼对象
        FishWidget fish = new FishWidget(activity,// 域
                rlMainFishArea,// 绑定的父布局
                fish_res,// 图元
                directEnum,// 鱼头朝向
                randomY// 起始Y值
        );

        // 任务绑定线程池
        threadPoolProxy.executeTask(new FishRunable(fish, step));
    }

    /**
     * 启动背景音频
     *
     * @param isDefaultStart 是否默认启动
     */
    private MediaPlayer getBgVoice(boolean isDefaultStart) {
        bgVoice = MediaPlayer.create(activity, R.raw.bg_music);
        bgVoice.setAudioStreamType(AudioManager.STREAM_MUSIC);
        bgVoice.setVolume(1, 1);
        bgVoice.setLooping(true);
        bgVoice.setOnCompletionListener(mp -> bgVoice = getBgVoice(isDefaultStart));
        if (isDefaultStart) {
            bgVoice.start();
        }
        return bgVoice;
    }

    /**
     * 启动小鱼音频
     */
    private MediaPlayer getFishVoice(boolean isDefaultStart) {
        if (fishVoice == null) {
            fishVoice = MediaPlayer.create(activity, R.raw.fish_get);
        }

        fishVoice.setAudioStreamType(AudioManager.STREAM_MUSIC);
        fishVoice.setVolume(1, 1);
        fishVoice.setLooping(false);
        fishVoice.setOnCompletionListener(mp -> fishVoice = getFishVoice(isDefaultStart));
        if (isDefaultStart) {
            fishVoice.start();
        }
        return fishVoice;
    }

    /* -------------------------------------------- thread -------------------------------------------- */

    /**
     * 小鱼游动线程
     */
    class FishRunable implements Runnable {

        public boolean canLoop = true;// 每一条线程的循环标记
        private FishWidget fishWidget;// 小鱼对象
        private int fishStep = 10;// 小鱼步长
        private boolean isLockYuGou;// 是否锁定鱼钩 - 默认没有上钩 (用于当鱼钩触碰到小鱼时, 不再重复判定)
        private boolean isLockTop;// 到达顶点时的抖动

        public FishRunable(FishWidget fishWidget, int fishStep) {
            this.fishWidget = fishWidget;
            this.fishStep = fishStep;
        }

        @Override
        public void run() {
            while (canLoop) {

                if (!isLockYuGou) {/* 小鱼没有上钩前 - 进行游动 */
                    // 指定游动方向
                    activity.runOnUiThread(() -> {
                        if (fishWidget.getDirectEnum() == FishWidget.DirectEnum.HEAD_RIGHT) {// 从左出来
                            fishWidget.swimTo(fishWidget.getX() + fishStep, null);// 递增x

                        } else {// 从右出来
                            fishWidget.swimTo(fishWidget.getX() - fishStep, null);// 递减x
                        }
                    });

                    // 指定边界值
                    boolean isReachScreenRightBorder = fishWidget.getDirectEnum() == FishWidget.DirectEnum.HEAD_RIGHT // 鱼头向右
                                                               & (fishWidget.getX() > screenWidth + 10);// 到达屏幕右侧

                    boolean isReachScreenLeftBorder = fishWidget.getDirectEnum() == FishWidget.DirectEnum.HEAD_LEFT // 鱼头向左
                                                              & (fishWidget.getX() < -fishWidget.getWidth() - 10);// 到达屏幕左侧
                    if (isReachScreenRightBorder || isReachScreenLeftBorder) {
                        activity.runOnUiThread(() -> {
                            fishWidget.setVisibility(View.GONE);
                            rlMainFishArea.removeView(fishWidget);
                        });
                        canLoop = false;
                    }

                } else {/* 上钩后的移动轨迹 */

                    activity.runOnUiThread(() -> {// 开始跟随鱼钩移动
                        // 计算鱼钩中心点
                        int yugou_center_x = (int) (ivMainYugou.getX() + ivMainYugou.getWidth() / 2);
                        int yugou_center_y = (int) (ivMainYugou.getY() + ivMainYugou.getHeight() / 2);
                        // 设定小鱼中心点与鱼钩中心点重合 - 并计算重合后的小鱼(x,y)
                        float fish_point_x = yugou_center_x - fishWidget.getWidth() * 1f / 2;
                        float fish_point_y = yugou_center_y - fishWidget.getHeight() * 1f / 2;
                        // 小鱼游动
                        fishWidget.swimTo(fish_point_x, fish_point_y);
                        // 如果鱼钩已经到达顶点 - 停止线程并移除小鱼
                        if (ivMainYugou.getY() <= yugouInitY) {
                            // 进入防抖
                            if (!isLockTop) {
                                isLockTop = true;
                                // 释放小鱼线程并清除小鱼图层
                                fishWidget.setVisibility(View.GONE);
                                rlMainFishArea.removeView(fishWidget);
                                // 显示泡泡效果
                                bwMainBlow.activite();
                                // 播放小鱼上钩音乐
                                fishVoice = getFishVoice(false);
                                fishVoice.start();
                                // 计算并动态设置分数 - 由分数线程进行计算
                                goal_ls.add(fishWidget.getGoal());
                                // 退出小鱼线程
                                canLoop = false;
                            }
                        }
                    });

                }

                /* 在鱼塘的范围内 (非屏幕外) 解决上钩的稳定性问题 - 判断鱼钩和小鱼是否重叠 (鱼儿是否上钩) */
                boolean isInRightArea = fishWidget.getDirectEnum() == FishWidget.DirectEnum.HEAD_RIGHT & (fishWidget.getX() <= screenWidth);
                boolean isInLeftArea = fishWidget.getDirectEnum() == FishWidget.DirectEnum.HEAD_LEFT & (fishWidget.getX() >= -fishWidget.getWidth());
                // 鱼塘范围内
                if (isInRightArea || isInLeftArea) {
                    boolean fishAndYuGouOverLap = FishUtils.isFishAndYuGouOverLap(ivMainYugou, fishWidget);
                    // 如果上钩 - 改变运动轨迹
                    if (fishAndYuGouOverLap) {
                        // 小鱼首次碰到鱼钩
                        if (!isLockYuGou) {
                            // 锁定鱼钩
                            isLockYuGou = true;
                            // 跟随鱼钩运动 (中心点)
                            int yugou_center_x = (int) (ivMainYugou.getX() + ivMainYugou.getWidth() / 2);
                            int yugou_center_y = (int) (ivMainYugou.getY() + ivMainYugou.getHeight() / 2);
                            // 计算小鱼在鱼钩的中心点
                            float fish_point_x = yugou_center_x - fishWidget.getWidth() * 1f / 2;
                            float fish_point_y = yugou_center_y - fishWidget.getHeight() * 1f / 2;
                            // 让小鱼定格在鱼钩上
                            fishWidget.setX(fish_point_x);
                            fishWidget.setY(fish_point_y);
                        }

                    }
                }


                try {
                    Thread.sleep(fish_duration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 鱼钩线程
     */
    class shootRunable implements Runnable {

        public boolean canThreadRun;// 是否允许自动线程
        public boolean needDown;// 是否需要鱼钩下掉

        @Override
        public void run() {
            while (canThreadRun) {
                try {
                    activity.runOnUiThread(() -> {
                        if (needDown) {/* 鱼钩下滑 */
                            // 鱼钩超过底线 - 则截止, 没有则正常显示
                            if (ivMainYugou.getY() < screenHeight * 0.95 - ivMainYugou.getHeight()) {
                                ViewHelper.setTranslationY(ivMainYugou, yugouY += yugou_step);
                                // 鱼钩下掉(通过缩放Y方向实现)
                                scaleYuxian();
                            }

                        } else if (ivMainYugou.getY() <= yugouInitY) {/* 鱼钩回到顶端 */
                            // 退出本次线程轮询
                            canThreadRun = false;
                            // 鱼钩回到顶部时, 500ms后才允许下一次点击
                            topTime = System.currentTimeMillis();
                            // 顶端坐标限制
                            ivMainYugou.setY(yugouInitY);
                            // 恢复熊二可以移动
                            bearMoveHelper.setlock(false);
                            // 恢复鱼钩的下掉操作
                            bearShootHelper.setlock(false);

                        } else {/* 鱼钩上滑 */
                            ViewHelper.setTranslationY(ivMainYugou, yugouY -= yugou_step);
                            // 鱼钩上滑(通过缩放Y方向实现)
                            scaleYuxian();
                        }
                    });

                    // 休眠
                    Thread.sleep(yugou_duration);
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        /**
         * 缩放鱼线操作
         */
        private void scaleYuxian() {
            // 设定鱼线的缩放基点
            ViewHelper.setPivotY(ivMainYuXian, 0);
            // 再设定缩放比例
            float yuXianScale = (ivMainYugou.getY() - yugouInitY) / ivMainYuXian.getHeight();
            ViewHelper.setScaleY(ivMainYuXian, yuXianScale);
        }
    }

    /**
     * 分数线程
     */
    class GoalRunnable implements Runnable {
        // 分数锁 - 在进行显示时不允许外部循环计算
        private boolean isGoalLock = false;
        // 计数器
        private int temp = 0;


        @Override
        public void run() {
            while (goalFlag) {
                // 如果集合里有元素 - 则说明有分数
                if (goal_ls.size() > 0) {
                    // 取出集合的第一个元素
                    int goal = goal_ls.get(0);
                    if (!isGoalLock) {
                        // 上锁
                        isGoalLock = true;
                        // 使用定时器进行动态展示
                        goalTimer = new TimerHelper(activity) {
                            @Override
                            public void doSomething() {
                                // 非空判断 - 避免在初始化时试图还没加载完毕
                                if (tvMainGoal != null) {
                                    if (temp < goal) {// 还没动态计算完毕
                                        activity.runOnUiThread(() -> {
                                            if (curGoal >= 9999999) {
                                                // 到达最高分 - 弹出面板退出
                                                gwMainFinish.setVisibility(View.VISIBLE);
                                                // 停止定时器并重置计数器
                                                stopAndReset();

                                            } else {
                                                // 正常设置分数
                                                setTvGoal(curGoal += 5);
                                                temp += 5;
                                            }
                                        });
                                    } else {
                                        // 停止定时器并重置计数器
                                        stopAndReset();
                                    }
                                }
                            }

                            /**
                             * 停止定时器并重置计数器
                             */
                            private void stopAndReset() {
                                goalTimer.stop();// 停止定时器
                                temp = 0;// 恢复计数器
                                if (goal_ls.size() > 0) {// 增加非空判断 - 提高稳定性
                                    goal_ls.remove(0);// 删除分数集合第一个元素
                                }
                                isGoalLock = false;// 恢复 - 解锁 - 允许下一个观察轮询进入
                            }
                        };
                        goalTimer.start(5);
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 防沉迷监控线程
     */
    class RestRunable implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    long lasttime = ShareUtils.get(RootComponent.SETTING_LAST_TIME, 0L);
                    long playDuration = ShareUtils.get(RootComponent.SETTING_PLAY_TIME, RootComponent.SETTING_DEFAULT_PLAY_DURATION);
                    long deltime = System.currentTimeMillis() - lasttime;// 当前时间 - 进入时间 > 设置的玩耍时间
                    if (deltime >= playDuration) {
                        activity.runOnUiThread(() -> {
                            // 弹出框 并把时间记录
                            if (goalTimer != null) {
                                goalTimer.stop();
                            }
                            if (gwMainFinish != null) {
                                gwMainFinish.setVisibility(View.VISIBLE);
                            }
                            ShareUtils.set(RootComponent.SETTING_LAST_TIME, System.currentTimeMillis());
                        });

                        break;
                    }
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
