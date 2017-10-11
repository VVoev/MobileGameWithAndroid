package me.icytower.UltimateCop.Core;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import me.icytower.UltimateCop.Contracts.GameObject;
import me.icytower.UltimateCop.GlobalConstants.Constants;

public class Obstacle implements GameObject {

    private int color;
    private Rect rectangle;
    private Rect rectangle2;
    private long currentDate = System.currentTimeMillis();
    private int speed = 1;

    public Obstacle(int rectHeight, int color, int startX, int startY, int playerGap) {
        //L,R,D,U
        this.color = color;
        this.rectangle = new Rect(0, startY, startX, startY + rectHeight);
        this.rectangle2 = new Rect(startX + playerGap + Constants.LEVEL_ADDITIONALGAP, startY, Constants.SCREEN_WIDTH, startY + rectHeight);
    }

    private long provideMeDate() {
        return System.currentTimeMillis();
    }

    public Rect getRectangle() {
        return rectangle;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
        canvas.drawRect(rectangle2, paint);
    }

    public void incrementY(double y) {
        rectangle.top += y * speed;
        rectangle.bottom += y * speed;
        rectangle2.top += y * speed;
        rectangle2.bottom += y * speed;
    }

    public boolean isPlayerColiding(RectPlayer player) {
        return Rect.intersects(rectangle, player.getRectangle()) ||
                Rect.intersects(rectangle2, player.getRectangle());
    }

    @Override
    public void update() {

    }
}