package com.zygame.icegame.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.RawRes;

@SuppressWarnings(value = {"unchecked", "deprecation"})
public class ActionVoiceUtils {

    public static MediaPlayer mp;

    public static void play(final Context context, @RawRes int rawVoice) {

        try {
            // 如果正在播放 - 则不处理
            // if (mp.isPlaying()) {
            //     return;
            // }
            // 如果之前有播放过 - 则先释放再重新创建
            if (mp != null) {
                releaseVoice();
            }
            // 指定音源
            mp = MediaPlayer.create(context, rawVoice);
            // 指定播放形式(MUSIC为外放,PHONE为耳机)
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            // 调节音量
            mp.setVolume(1.0f, 1.0f);
            // 开始播放
            /* 注意:使用静态create的方式,不需要在prepare() */
            mp.start();

            // 设置播放完毕后的监听
            mp.setOnCompletionListener(mps -> {
                // 预先准备好一个音源--> 待有新的调用时能马上启动
                // 会出现的BUG:如果不设置该监听,则每次有需求调用时候,音源会产生延迟并且有可能会丢失音源而没有正常播放声音
                ActionVoiceUtils.mp = mps;
                ActionVoiceUtils.mp = MediaPlayer.create(context, rawVoice);
            });

            mp.setOnErrorListener((mp1, what, extra) -> {
                play(context, rawVoice);
                return true;
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放音频
     */
    public static void releaseVoice() {
        if (mp != null) {
            mp.release();
            mp = null;
        }
    }
}
