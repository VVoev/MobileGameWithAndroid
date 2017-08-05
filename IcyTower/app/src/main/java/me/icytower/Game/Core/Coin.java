package me.icytower.Game.Core;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import me.icytower.Game.Contracts.GameObject;

public class Coin implements GameObject{
    private Rect rectangle;
    private int color;

    public Coin(int rectHeight,int startX,int startY,int color){
        this.color = color;
        this.rectangle = new Rect(0,startY,startX,startY+rectHeight);
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

    public boolean isCoinTakenByThePlayer(RectPlayer player){
        return Rect.intersects(rectangle,player.getRectangle());
    }
}
