package com.zygame.fishgame.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.zygame.fishgame.R;

/*
 * Created by Administrator on 2020/5/30.
 */
public class GoalFinishWidget extends RelativeLayout {

    private View inflate;

    public GoalFinishWidget(Context context) {
        this(context, null, 0);
    }

    public GoalFinishWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GoalFinishWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate = inflate(context, R.layout.fishgame_goal_finish, this);
        inflate.findViewById(R.id.rl_goal_finish).setOnClickListener(null);
        inflate.findViewById(R.id.iv_goal_bg).setOnClickListener(null);
        inflate.findViewById(R.id.bt_goal_finish).setOnClickListener(v -> CLickGoalFinishNext());
    }

    private OnCLickGoalFinishListener onCLickGoalFinishListener;

    // Inteerface--> 接口OnCLickGoalFinishListener
    public interface OnCLickGoalFinishListener {
        void CLickGoalFinish();
    }

    // 对外方式setOnCLickGoalFinishListener
    public void setOnCLickGoalFinishListener(OnCLickGoalFinishListener onCLickGoalFinishListener) {
        this.onCLickGoalFinishListener = onCLickGoalFinishListener;
    }

    // 封装方法CLickGoalFinishNext
    private void CLickGoalFinishNext() {
        if (onCLickGoalFinishListener != null) {
            onCLickGoalFinishListener.CLickGoalFinish();
        }
    }
}
