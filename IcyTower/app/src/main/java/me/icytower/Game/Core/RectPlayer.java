package me.icytower.Game.Core;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import me.icytower.Game.Contracts.GameObject;

public class RectPlayer implements GameObject {

    private Rect rectangle;
    private int color;

    /*
    think about it
    private boolean isGameOver;

    public boolean GetIsGameOver(){
        return this.isGameOver;
    }

    public void SetIsGameOver(boolean value){
        this.isGameOver = value;
    }
    */

    public RectPlayer(Rect rectangle, int color){
        this.rectangle = rectangle;
        this.color = color;
    }
    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle,paint);
    }

    public Rect getRectangle(){
        return  rectangle;
    }

    @Override
    public void update() {

    }

    public void update(Point point){
        rectangle.set(
                point.x-rectangle.width()/2,
                point.y-rectangle.height()/2,
                point.x+rectangle.width()/2,
                point.y+rectangle.height()/2);
    }
}
