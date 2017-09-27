package me.icytower.Game.Core;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

import me.icytower.Game.Contracts.GameObject;

public class ObstacleManager implements GameObject {

    private ArrayList<Obstacle> obstacles;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;
    private RectPlayer player;

    //speed of dropping the obstacles
    private int score = 0;
    private float superSpeed = 1;

    private long startTime;

    public ObstacleManager(int playerGap, int obstacleGap, int obstacleHeight, int color, RectPlayer player) {
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;
        this.player = player;

        startTime = System.currentTimeMillis();
        obstacles = new ArrayList<>();
        populateObstaces();
        System.out.println("Ot obstacle managera");
    }
    public int getScore(){
        return this.score;
    }
    public void setScore(int score){
        this.score = score;
    }

    public void update() {
        if (startTime < Constants.INIT_TIME) {
            startTime = Constants.INIT_TIME;
        }

        int elapsedTime = (int) (System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        float speed = (Constants.SCREEN_HEIGHT / 10000.0f);
        for (Obstacle ob : obstacles) {
            if (player.getRectangle().top > ob.getRectangle().top) {
                score++;
                if (score % Constants.GAME_SPEED == 0) {
                    superSpeed += 0.1f;
                }
            }

            ob.incrementY((speed * elapsedTime) + superSpeed);
        }

        //TODO refactor logic
        if (obstacles.get(obstacles.size() - 1).getRectangle().top >= Constants.SCREEN_HEIGHT) {
            int xStart = (int) (Math.random() * Constants.SCREEN_WIDTH - playerGap);
            obstacles.add(0, new Obstacle(
                    obstacleHeight, color, xStart, obstacles.get(0).getRectangle().top -
                    obstacleHeight - obstacleGap, playerGap));
            obstacles.remove(obstacles.size() - 1);
            //score++ can also be achieved here
        }
    }

    public void draw(Canvas canvas) {
        for (Obstacle ob : obstacles) {
            ob.draw(canvas);
        }
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setTextSize(100);
        canvas.drawText("" + score, 50, 50 + paint.descent() - paint.ascent(), paint);
    }

    public boolean playerCollide(RectPlayer player) {
        for (Obstacle ob : obstacles) {
            if (ob.isPlayerColiding(player)) {
                return true;
            }
        }
        return false;
    }

    private void populateObstaces() {
        int currY = -5 * Constants.SCREEN_HEIGHT / 4;
        while (currY < 0) {
            int xStart = (int) (Math.random() * Constants.SCREEN_WIDTH - playerGap);
            Obstacle currentObstacle = new Obstacle(obstacleHeight, color, xStart - 100, currY, playerGap);
            obstacles.add(currentObstacle);
            currY += obstacleHeight + obstacleGap;
        }
    }
}