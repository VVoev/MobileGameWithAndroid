package me.icytower.UltimateCop.Core;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

    private static final int ONE_MILLION = 1_000_000;
    private static final int MAX_FPS = 30;
    private double averageFPS;

    private SurfaceHolder surfaceHolder;
    private GamePanel gamePannel;

    private boolean running;
    public static Canvas canvas;

    public void setRunning(boolean running) {
        this.running = running;
    }

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePannel = gamePanel;
    }

    //TODO LEAVE IT FOR NOW
    //Override default sleep since its deprecated in thread
    //    public void wait(int time){
    //        try {
    //            Thread.sleep(time);
    //        } catch (InterruptedException e) {
    //            e.printStackTrace();
    //        }
    //    }

    @Override
    public void run() {
        long startTime;
        long timeMiliseconds = 1000 / MAX_FPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000 / MAX_FPS;

        while (running) {
            startTime = System.nanoTime();
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gamePannel.update();
                    this.gamePannel.draw(canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            timeMiliseconds = (System.nanoTime() - startTime) / ONE_MILLION;
            waitTime = targetTime - timeMiliseconds;
            try {
                if (waitTime > 0) {
                    this.sleep(waitTime);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == MAX_FPS) {
                double operation = (totalTime / frameCount) / ONE_MILLION;
                averageFPS = 1000 / operation;
                frameCount = 0;
                totalTime = 0;
                //debugging
                //System.out.println(averageFPS);
            }
        }
    }


}
