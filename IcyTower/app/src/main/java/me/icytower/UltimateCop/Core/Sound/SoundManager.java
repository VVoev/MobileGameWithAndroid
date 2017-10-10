package me.icytower.UltimateCop.Core.Sound;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import me.icytower.R;

public class SoundManager {

    private int hitSound;
    private int bonusSound;
    private SoundPool soundpool;

    public SoundManager(Context context) {
        this.soundpool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        hitSound = soundpool.load(context, R.raw.carcrash, 1);
        bonusSound = soundpool.load(context, R.raw.coin, 1);
    }

    public void playHitSound(){
        soundpool.play(hitSound,1.0f,1.0f,1,0,1.0f);
    }

    public void playBonusSound(){
        soundpool.play(bonusSound,1.0f,1.0f,1,0,1.0f);
    }
}
