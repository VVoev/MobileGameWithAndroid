package me.icytower.Game;


import java.util.ArrayList;

public class ObstacleManager {
    private ArrayList<Obstacle> obstacles;

    private int playerGap;
    private int obstacleGap;

    public ObstacleManager(int playerGap) {
        this.playerGap = playerGap;
        obstacles = new ArrayList<>();
        populateObstacles();
    }

    //Legend
    //higher index lower on screen

    private void populateObstacles() {
        int currY = -5 * Constants.SCREEN_HEIGHT / 4;
        while (obstacles.get(obstacles.size() - 1).getRectangle().bottom < 0) {
            int starX = (int)(Math.random()*Constants.SCREEN_WIDTH - playerGap);
        }
    }
}
