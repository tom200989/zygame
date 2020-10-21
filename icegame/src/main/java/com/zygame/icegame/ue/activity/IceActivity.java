package com.zygame.icegame.ue.activity;

import android.media.AudioManager;
import android.media.MediaPlayer;

import com.hiber.bean.RootProperty;
import com.hiber.hiber.RootMAActivity;
import com.zygame.icegame.BuildConfig;
import com.zygame.icegame.R;
import com.zygame.icegame.ue.frag.Frag_ice;
import com.zygame.icegame.ue.frag.Frag_ice_shop;
import com.zygame.icegame.ue.frag.Frag_ice_style;
import com.zygame.icegame.ue.frag.Frag_ice_work2;

@SuppressWarnings(value = {"unchecked", "deprecation"})
public class IceActivity extends RootMAActivity {

    private Class[] frags = {// 
            Frag_ice.class, // 雪糕起始页
            Frag_ice_shop.class, // 雪糕商店页
            // Frag_ice_work.class, // 雪糕厨房页(以后再做)
            Frag_ice_work2.class, // 雪糕制作页2
            Frag_ice_style.class, // 雪糕样式页
    };

    private MediaPlayer bgVoice;// 背景音乐

    @Override
    public RootProperty initProperty() {
        RootProperty rootProperty = new RootProperty();
        rootProperty.setColorStatusBar(R.color.common_colorTheme);
        rootProperty.setContainId(R.id.fl_ice_container);
        rootProperty.setFragmentClazzs(frags);
        rootProperty.setFullScreen(true);
        rootProperty.setLayoutId(R.layout.icegame_activity_ice);
        rootProperty.setSaveInstanceState(false);
        rootProperty.setPackageName(BuildConfig.APPLICATION_ID);
        return rootProperty;
    }

    @Override
    public void onNexts() {

    }

    /**
     * 启动背景音频
     *
     * @param isDefaultStart 是否默认启动
     */
    private MediaPlayer getBgVoice(boolean isDefaultStart) {
        releaseVoice();
        bgVoice = MediaPlayer.create(this, R.raw.merry_chrismas);
        bgVoice.setAudioStreamType(AudioManager.STREAM_MUSIC);
        bgVoice.setVolume(1, 1);
        bgVoice.setLooping(true);
        bgVoice.setOnCompletionListener(mp -> bgVoice = getBgVoice(isDefaultStart));
        if (isDefaultStart) {
            bgVoice.start();
        }
        return bgVoice;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getBgVoice(true);
        System.out.println("begin start music");
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseVoice();
    }

    public void releaseVoice() {
        if (bgVoice != null) {
            bgVoice.reset();
            bgVoice.release();
            bgVoice = null;
        }
    }

    @Override
    public boolean onBackClick() {
        return false;
    }
}
