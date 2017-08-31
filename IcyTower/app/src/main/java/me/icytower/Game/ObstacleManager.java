package me.icytower.Game;


import android.app.FragmentManager;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.provider.ContactsContract;

import java.util.ArrayList;

import me.icytower.Game.Contracts.GameObject;

public class ObstacleManager {

    private ArrayList<Obstacle> obstacles;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;

    //speed of dropping the obstacles above
    private float superSpeed = 1;

    private long startTime;

    public ObstacleManager(int playerGap, int obstacleGap, int obstacleHeight, int color) {
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;

        startTime = System.currentTimeMillis();
        obstacles = new ArrayList<>();
        populateObstaces();
    }

    public void update() {
        int elapsedTime = (int) (System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        float speed = (Constants.SCREEN_HEIGHT / 10000.0f);
        for (Obstacle ob : obstacles) {
            superSpeed +=0.0003f;
            ob.incrementY((speed * elapsedTime)+superSpeed);
        }

        if (obstacles.get(obstacles.size() - 1).getRectangle().top >= Constants.SCREEN_HEIGHT) {
            //TODO refactor logic
            int xStart = (int) (Math.random() * Constants.SCREEN_WIDTH - playerGap);
            obstacles.add(0, new Obstacle(
                    obstacleHeight, color, xStart, obstacles.get(0).getRectangle().top -
                    obstacleHeight - obstacleGap, playerGap));
            obstacles.remove(obstacles.size()-1);

        }
    }

    public void draw(Canvas canvas) {
        for (Obstacle ob : obstacles) {
            ob.draw(canvas);
        }
    }

    public boolean playerCollide(RectPlayer player){
        for (Obstacle ob : obstacles){
            if(ob.isPlayerColiding(player)){
                return true;
            }
        }
        return false;
    }

    private void populateObstaces() {
        int currY = -5 * Constants.SCREEN_HEIGHT / 4;
        while (currY < 0) {
            int xStart = (int) (Math.random() * Constants.SCREEN_WIDTH - playerGap);
            obstacles.add(new Obstacle(obstacleHeight, color, xStart, currY, playerGap));
            currY += obstacleHeight + obstacleGap;
        }
    }


}
