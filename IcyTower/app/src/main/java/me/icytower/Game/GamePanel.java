package me.icytower.Game;


import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.graphics.Point;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private Rect r = new Rect();


    private RectPlayer player;
    private Point playerPoint;
    private ObstacleManager obstacleManager;

    private Display display;

    private boolean movingPlayer = false;
    private boolean isGameOver = false;

    private long gameOverTime;

    public GamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        player = new RectPlayer(new Rect(100, 100, 150, 150), Color.rgb(255, 0, 0));
        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, 3 * Constants.SCREEN_HEIGHT / 4);
        player.update(playerPoint);
        obstacleManager = new ObstacleManager(
                Constants.LEVEL_WHOLEWIDTH,
                Constants.LEVEL_HEIGHT,
                Constants.LEVEL_OBSTACLETHICKNESS,
                Color.BLACK);

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (true) {
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

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!isGameOver && player.getRectangle().contains((int) event.getX(), (int) event.getY())) {
                    movingPlayer = true;
                }
                System.out.println("Current Time:"+System.currentTimeMillis());
                System.out.println("IsGameOver"+isGameOver);
                System.out.println("gameOverTime"+gameOverTime);
                System.out.println("Difference" + (System.currentTimeMillis()-gameOverTime));
                if (isGameOver && System.currentTimeMillis() - gameOverTime >= 2000) {
                    reset();
                    isGameOver = false;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (!isGameOver && movingPlayer) {
                    playerPoint.set((int) event.getX(), (int) event.getY());
                    break;
                }
            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;
        }

        return true;
        //return super.onTouchEvent(event);
    }

    private void reset() {
        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, 3 * Constants.SCREEN_HEIGHT / 4);
        player.update(playerPoint);
        obstacleManager = new ObstacleManager(
                Constants.LEVEL_WHOLEWIDTH,
                Constants.LEVEL_HEIGHT,
                Constants.LEVEL_OBSTACLETHICKNESS,
                Color.BLACK);
        movingPlayer = false;
    }

    public void update() {
        if (!isGameOver) {
            player.update(playerPoint);
            obstacleManager.update();
        }

        if (obstacleManager.playerCollide(player) && !isGameOver) {
            isGameOver = true;
            gameOverTime = System.currentTimeMillis();
        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(Color.WHITE);

        player.draw(canvas);
        obstacleManager.draw(canvas);

        if (isGameOver) {
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.BLUE);
            drawGameOver(canvas,paint,Constants.GAME_OVER);
        }
    }

    private void drawGameOver(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.rgb(6,213,249));
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f - r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }

}
