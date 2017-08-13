package me.icytower.Game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import me.icytower.Game.Contracts.GameObject;

public class Obstacle implements GameObject {

    private int color;
    private Rect rectangle;

    public Obstacle(int color,Rect rectangle){
        this.color = color;
        this.rectangle = rectangle;
    }

    public Rect getRectangle(){
        return rectangle;
    }

    public boolean isPlayerColiding(RectPlayer player){
        if     (rectangle.contains(player.getRectangle().left,player.getRectangle().top)
        ||      rectangle.contains(player.getRectangle().right,player.getRectangle().top)
        ||      rectangle.contains(player.getRectangle().left,player.getRectangle().bottom)
        ||      rectangle.contains(player.getRectangle().right,player.getRectangle().bottom))
        {
            return  true;
        }

        return  false;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle,paint);
    }

    @Override
    public void update() {

    }
}
