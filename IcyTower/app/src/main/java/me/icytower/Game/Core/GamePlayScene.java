package me.icytower.Game.Core;

import android.graphics.Canvas;
import android.view.MotionEvent;

import me.icytower.Game.Contracts.Scene;

public class GamePlayScene implements Scene {

    private SceneManager manager;


    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void touchReceive(MotionEvent event) {

    }
}
