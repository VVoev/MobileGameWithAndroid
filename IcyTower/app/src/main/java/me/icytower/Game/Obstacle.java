package me.icytower.Game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import me.icytower.Game.Contracts.GameObject;

public class Obstacle implements GameObject {

    private int color;
    private Rect rectangle;
    private Rect rectangle2;


    public Obstacle(int rectHeight, int color, int startX, int startY, int playerGap) {
        this.color = color;
        //L,R,D,U
        this.rectangle = new Rect(0, startY, startX, startY + rectHeight);
        this.rectangle2 = new Rect(startX + playerGap, startY, Constants.SCREEN_WIDTH, startY + rectHeight);
    }

    public Rect getRectangle() {
        return rectangle;
    }

    public boolean isPlayerColiding(RectPlayer player) {
        if (rectangle.contains(player.getRectangle().left, player.getRectangle().top)
                || rectangle.contains(player.getRectangle().right, player.getRectangle().top)
                || rectangle.contains(player.getRectangle().left, player.getRectangle().bottom)
                || rectangle.contains(player.getRectangle().right, player.getRectangle().bottom)) {
            return true;
        }

        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
        canvas.drawRect(rectangle2, paint);
    }

    public void incrementY(double y) {
        rectangle.top += y;
        rectangle.bottom += y;
        rectangle2.top += y;
        rectangle2.bottom += y;
    }

    @Override
    public void update() {

    }
}
