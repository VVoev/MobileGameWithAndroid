package me.icytower.Game.Core;


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

import me.icytower.Game.Activities.Result;
import me.icytower.Game.Contracts.Scene;
import me.icytower.R;


public class GamePlayScene implements Scene {

    private SceneManager manager;
    private Rect r = new Rect();


    private RectPlayer player;
    private Point playerPoint;
    private ObstacleManager obstacleManager;

    private Display display;

    private boolean movingPlayer = false;
    private boolean isGameOver = false;

    private long gameOverTime;

    private OrientationData orientationData;
    private long frameTime;

    private Coin coin;

    /*best params
     100 100 200 200        debelina na kvadrata 3ti i 4ti parametur
     */

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
        coin = new Coin(50,50,150,Color.rgb(255,0,0),player,obstacleManager);


        orientationData = new OrientationData();
        orientationData.register();
        frameTime = System.currentTimeMillis();
    }

    private void checkForOffScreen(Point playerPoint) {
        if(playerPoint.x < 0){
            playerPoint.x = 0;
        }
        else  if(playerPoint.x > Constants.SCREEN_WIDTH){
            playerPoint.x = Constants.SCREEN_WIDTH;
        }

        if(playerPoint.y < 0){
            playerPoint.y = 0;
        }
        else if(playerPoint.y > Constants.SCREEN_HEIGHT){
            playerPoint.y = Constants.SCREEN_HEIGHT;
        }
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
        coin = new Coin(50,50,150,Color.rgb(255,0,0),player,obstacleManager);
        movingPlayer = false;
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

                //check for possible bottlenecks (int)
                playerPoint.x += (int)(Math.abs(xSpeed * elapsedTime) > 5 ? xSpeed * elapsedTime : 0);
                playerPoint.y -= (int)(Math.abs(ySpeed * elapsedTime) > 5 ? ySpeed * elapsedTime : 0);
            }

            checkForOffScreen(playerPoint);

            player.update(playerPoint);
            obstacleManager.update();
        }

        if (obstacleManager.playerCollide(player) && !isGameOver) {
            isGameOver = true;
            gameOverTime = System.currentTimeMillis();

            Intent intent = new Intent(Constants.CONTEXT.getApplicationContext(),Result.class);
            int score = obstacleManager.getScore();
            intent.putExtra("SCORE",score);
            Constants.CONTEXT.startActivity(intent);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        //TODO REPLACE THE CANVAS WITH PICTURE
        BitmapFactory bf = new BitmapFactory();
        Bitmap image = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.x2);
        canvas.drawBitmap(image,0,0,null);
        //canvas.drawColor(Color.YELLOW);

        player.draw(canvas);
        obstacleManager.draw(canvas);
        coin.draw(canvas);

        if (isGameOver) {
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.BLUE);
            //TODO implement fucking intent getApplicationContext() which for some fuckin reason is not working
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
                if (!isGameOver && player.getRectangle().contains((int) event.getX(), (int) event.getY())) {
                    movingPlayer = true;
                }
                if (isGameOver && System.currentTimeMillis() - gameOverTime >= 2000) {
                    reset();
                    isGameOver = false;
                    orientationData.newGame();
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
}
