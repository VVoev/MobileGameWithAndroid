package me.icytower.UltimateCop.Core.Managers;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

import me.icytower.UltimateCop.Contracts.Scene;
import me.icytower.UltimateCop.Core.GamePlayScene;

public class SceneManager {

    private ArrayList<Scene> scenes;
    public static int ACTIVE_SCENE;

    public SceneManager() {
        scenes = new ArrayList<>();
        ACTIVE_SCENE = 0;
        scenes.add(new GamePlayScene());
    }

    public void update(){
        scenes.get(ACTIVE_SCENE).update();
    }

    public void draw(Canvas canvas){
        scenes.get(ACTIVE_SCENE).draw(canvas);
    }

    public void receiveTouch(MotionEvent event){
        scenes.get(ACTIVE_SCENE).touchReceive(event);
    }

}