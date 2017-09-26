package me.icytower.Game.Core;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.constraint.solver.widgets.Rectangle;

import java.util.Random;

import me.icytower.Game.Contracts.GameObject;

public class Coin implements GameObject{
    private Rect rectangle;
    private int color;
    private RectPlayer player;

    public Coin(int rectHeight,int startX,int startY,int color,RectPlayer player){
        this.color = color;     //right   //down  //right
        this.rectangle = new Rect(0,startY,startX,startY+rectHeight);
        this.player = player;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        if(!isCoinTakenByThePlayer(player)){
            canvas.drawRect(rectangle,paint);
        }
        else{
            generateCoinRandomPosition();
        }
    }


    private void generateCoinRandomPosition(){
        Random r = new Random();
        int low = 100;
        int high = Constants.SCREEN_WIDTH;
        int first = r.nextInt(high-low) + low;
        int second = r.nextInt(high-low)+low;
        this.rectangle = new Rect(first,second,first+Constants.OFFSET,second+Constants.OFFSET);
    }

    @Override
    public void update() {

    }

    public boolean isCoinTakenByThePlayer(RectPlayer player){
        return Rect.intersects(rectangle,player.getRectangle());
    }
}
