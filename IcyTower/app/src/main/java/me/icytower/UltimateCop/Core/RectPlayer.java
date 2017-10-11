package me.icytower.UltimateCop.Core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;

import me.icytower.UltimateCop.Contracts.GameObject;
import me.icytower.R;

public class RectPlayer implements GameObject {

    private Rect rectangle;
    private int color;

    private Animation idle;
    private Animation walkLeft;
    private Animation walkRight;


    private AnimationManager animManager;

    public RectPlayer(Rect rectangle, int color) {
        this.rectangle = rectangle;
        this.color = color;


        BitmapFactory bf = new BitmapFactory();

        Bitmap idleImage = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.f1);
        Bitmap walk1 = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.f2);
        Bitmap walk2 = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.f3);


        idle = new Animation(new Bitmap[]{idleImage}, 2);
        walkRight = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);

        Matrix m = new Matrix();
        m.preScale(-1, 1);
        walk1 = Bitmap.createBitmap(walk1, 0, 0, walk1.getWidth(), walk1.getHeight(), m, false);
        walk2 = Bitmap.createBitmap(walk2, 0, 0, walk2.getWidth(), walk2.getHeight(), m, false);


        walkLeft = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);
        animManager = new AnimationManager(new Animation[]{idle, walkLeft, walkRight});
    }

    @Override
    public void draw(Canvas canvas) {
        //test mode 1
        //Paint paint = new Paint();
        //paint.setColor(color);
        //canvas.drawRect(rectangle, paint);
        //production mode 2
        animManager.draw(canvas, rectangle);
    }

    public Rect getRectangle() {
        return rectangle;
    }

    @Override
    public void update() {
        animManager.update();
    }

    public void update(Point point) {
        float oldLeft = rectangle.left;


        rectangle.set(
                point.x - rectangle.width() / 2,
                point.y - rectangle.height() / 2,
                point.x + rectangle.width() / 2,
                point.y + rectangle.height() / 2);

        int state = 0;
        if (rectangle.left - oldLeft > 5) {
            state = 1;
        } else if (rectangle.left - oldLeft < -5) {
            state = 2;
        }

        animManager.playAnimation(state);
        animManager.update();
    }
}
