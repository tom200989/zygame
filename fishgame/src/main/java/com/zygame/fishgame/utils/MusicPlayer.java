package com.zygame.fishgame.utils;

import android.content.Context;
import android.media.MediaPlayer;

import static android.media.AudioManager.STREAM_MUSIC;


public class MusicPlayer {

    private MediaPlayer mp;

    /**
     * 播放
     *
     * @param context 域
     * @param rawId   音频资源
     */
    public MediaPlayer play(final Context context, int rawId, boolean isloop) {

        try {
            if (mp != null) {
                mp.start();
            }
            // 指定音源
            mp = MediaPlayer.create(context, rawId);
            // 指定播放形式(MUSIC为外放,PHONE为耳机)
            mp.setAudioStreamType(STREAM_MUSIC);
            // 调节音量
            mp.setVolume(1.0f, 1.0f);
            // 是否循环
            mp.setLooping(isloop);
            // 开始播放
            /* 注意:使用静态create的方式,不需要在prepare() */
            mp.start();

            // 设置播放完毕后的监听
            mp.setOnCompletionListener(mp -> {
                // 预先准备好一个音源--> 待有新的调用时能马上启动
                // 会出现的BUG: 如果不设置该监听,则每次有需求调用时候,音源会产生延迟并且有可能会丢失音源而没有正常播放声音
                this.mp = mp;
                this.mp = MediaPlayer.create(context, rawId);
                completedNext(mp);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mp;
    }

    /**
     * 停止
     */
    public void stop() {
        if (mp != null) {
            mp.stop();
        }
    }

    private OnMusicCompleteListener onMusicCompleteListener;

    // Inteerface--> 接口OnMusicCompleteListener
    public interface OnMusicCompleteListener {
        void completed(MediaPlayer mp);
    }

    // 对外方式setOnMusicCompleteListener
    public void setOnMusicCompleteListener(OnMusicCompleteListener onMusicCompleteListener) {
        this.onMusicCompleteListener = onMusicCompleteListener;
    }

    // 封装方法completedNext
    private void completedNext(MediaPlayer mp) {
        if (onMusicCompleteListener != null) {
            onMusicCompleteListener.completed(mp);
        }
    }
}
