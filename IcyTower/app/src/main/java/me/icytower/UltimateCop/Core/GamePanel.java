package me.icytower.UltimateCop.Core;


import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import me.icytower.UltimateCop.Db.DbManager;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private SceneManager manager;
    private DbManager dbManager;

    public GamePanel(Context context) {
        //don`t touch the parameters or it will explode
        super(context);
        getHolder().addCallback(this);
        Constants.CONTEXT = context;
        thread = new MainThread(getHolder(), this);
        manager = new SceneManager();
        this.dbManager = DbManager.getInstance(context);

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);
        Constants.INIT_TIME = System.currentTimeMillis();
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        manager.receiveTouch(event);
        return true;
    }

    public void update() {
        manager.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        manager.draw(canvas);
    }
}