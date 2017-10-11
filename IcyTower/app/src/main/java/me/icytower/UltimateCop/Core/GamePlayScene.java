package me.icytower.UltimateCop.Core;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;

import me.icytower.UltimateCop.Activities.Result;
import me.icytower.UltimateCop.Contracts.Scene;
import me.icytower.UltimateCop.Core.Gyroscope.OrientationData;
import me.icytower.UltimateCop.Core.Managers.ObstacleManager;
import me.icytower.UltimateCop.Core.Managers.SceneManager;
import me.icytower.UltimateCop.Core.Sound.SoundManager;
import me.icytower.R;
import me.icytower.UltimateCop.GlobalConstants.Constants;


public class GamePlayScene implements Scene {

    private SceneManager manager;
    private Display display;
    private Rect r = new Rect();

    private SoundManager soundManager;
    private RectPlayer player;
    private BadGuy badGuy;
    private Point playerPoint;
    private ObstacleManager obstacleManager;

    private boolean movingPlayer = false;
    private boolean isGameOver = false;

    private long gameOverTime;

    private OrientationData orientationData;
    private long frameTime;


    public GamePlayScene() {
        player = new RectPlayer(new Rect(100, 100, 300, 200), Color.rgb(255, 0, 0));
        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, 3 * Constants.SCREEN_HEIGHT / 4);
        player.update(playerPoint);
        obstacleManager = new ObstacleManager(
                Constants.LEVEL_WHOLEWIDTH,
                Constants.LEVEL_HEIGHT,
                Constants.LEVEL_OBSTACLETHICKNESS,
                Color.BLACK,
                player);
        badGuy = new BadGuy(50, 50, 150, Color.rgb(255, 0, 0), player, obstacleManager);
        soundManager = new SoundManager(Constants.CONTEXT);

        orientationData = new OrientationData();
        orientationData.register();
        frameTime = System.currentTimeMillis();
    }

    private void checkForOffScreen(Point playerPoint) {
        if (playerPoint.x < 0) {
            playerPoint.x = 0;
        } else if (playerPoint.x > Constants.SCREEN_WIDTH) {
            playerPoint.x = Constants.SCREEN_WIDTH;
        }

        if (playerPoint.y < 0) {
            playerPoint.y = 0;
        } else if (playerPoint.y > Constants.SCREEN_HEIGHT) {
            playerPoint.y = Constants.SCREEN_HEIGHT;
        }
    }

    @Override
    public void update() {
        if (!isGameOver) {
            if (frameTime < Constants.INIT_TIME) {
                frameTime = Constants.INIT_TIME;
            }

            int elapsedTime = (int) (System.currentTimeMillis() - frameTime);
            frameTime = System.currentTimeMillis();

            if (orientationData.getOrientation() != null && orientationData.getOrientation() != null) {
                float pitch = orientationData.getOrientation()[1] - orientationData.getStartOrientation()[1];
                float roll = orientationData.getOrientation()[2] - orientationData.getStartOrientation()[2];

                float xSpeed = 2 * roll * Constants.SCREEN_WIDTH / Constants.SLOW_THE_TIME;
                float ySpeed = pitch * Constants.SCREEN_HEIGHT / Constants.SLOW_THE_TIME;

                playerPoint.x += (int) (Math.abs(xSpeed * elapsedTime) > 5 ? xSpeed * elapsedTime : 0);
                playerPoint.y -= (int) (Math.abs(ySpeed * elapsedTime) > 5 ? ySpeed * elapsedTime : 0);
            }

            checkForOffScreen(playerPoint);
            player.update(playerPoint);
            obstacleManager.update();
        }

        if (obstacleManager.playerCollide(player) && !isGameOver) {
            soundManager.playHitSound();
            isGameOver = true;
            gameOverTime = System.currentTimeMillis();

            Intent intent = new Intent(Constants.CONTEXT.getApplicationContext(), Result.class);
            int score = obstacleManager.getScore();
            intent.putExtra("SCORE", score);
            Constants.CONTEXT.startActivity(intent);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        BitmapFactory bf = new BitmapFactory();
        Bitmap image = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.x2);
        canvas.drawBitmap(image, 0, 0, null);
        //canvas.drawColor(Color.YELLOW);

        player.draw(canvas);
        obstacleManager.draw(canvas);
        badGuy.draw(canvas);

        if (isGameOver) {
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.BLUE);
            int points = obstacleManager.getScore();
            drawGameOver(canvas, paint, Constants.GAME_OVER);
        }
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void touchReceive(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                checkIfPlayerIsAllowedToMove(event);
                checkIfTwoSecondsArePassedBeforeRestart();
                break;
            case MotionEvent.ACTION_MOVE:
                changeCordinates(event);
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;
        }
    }

    private void changeCordinates(MotionEvent event) {
        if (!isGameOver && movingPlayer) {
            playerPoint.set((int) event.getX(), (int) event.getY());
        }
    }

    private void checkIfTwoSecondsArePassedBeforeRestart() {
        if (isGameOver && System.currentTimeMillis() - gameOverTime >= 2000) {
            reset();
            isGameOver = false;
            orientationData.newGame();
        }
    }

    private void checkIfPlayerIsAllowedToMove(MotionEvent event) {
        if (!isGameOver && player.getRectangle().contains((int) event.getX(), (int) event.getY())) {
            movingPlayer = true;
        }
    }

    private void drawGameOver(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.rgb(6, 213, 249));
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f - r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }

    private void reset() {
        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, 3 * Constants.SCREEN_HEIGHT / 4);
        player.update(playerPoint);
        obstacleManager = new ObstacleManager(
                Constants.LEVEL_WHOLEWIDTH,
                Constants.LEVEL_HEIGHT,
                Constants.LEVEL_OBSTACLETHICKNESS,
                Color.BLACK,
                player);
        badGuy = new BadGuy(50, 50, 150, Color.rgb(255, 0, 0), player, obstacleManager);
        movingPlayer = false;
    }
}
