package me.icytower.UltimateCop.Core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

import me.icytower.R;
import me.icytower.UltimateCop.Contracts.GameObject;
import me.icytower.UltimateCop.Core.Sound.SoundManager;

public class BadGuy implements GameObject {
    private Rect rectangle;
    private int color;
    private RectPlayer player;
    private ObstacleManager manager;
    private SoundManager soundManager;

    private AnimationManager animManager;
    private Animation idle;
    private Animation walkLeft;
    private Animation walkRight;

    public BadGuy(int rectHeight, int startX, int startY, int color, RectPlayer player, ObstacleManager manager) {
        this.color = color;
        BitmapFactory bf = new BitmapFactory();
        Bitmap idleImage = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.m1);
        Bitmap walk1 = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.m2);
        Bitmap walk2 = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.m3);

        idle = new Animation(new Bitmap[]{idleImage}, 2);
        walkRight = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);
        Matrix m = new Matrix();
        m.preScale(-1, 1);
        walk1 = Bitmap.createBitmap(walk1, 0, 0, walk1.getWidth(), walk1.getHeight(), m, false);
        walk2 = Bitmap.createBitmap(walk2, 0, 0, walk2.getWidth(), walk2.getHeight(), m, false);

        walkLeft = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);
        animManager = new AnimationManager(new Animation[]{idle, walkLeft, walkRight});
        generateBadGuyRandomPosition();

        this.player = player;
        this.manager = manager;
        this.soundManager = new SoundManager(Constants.CONTEXT);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        if (!isBadGuyTakenByThePlayer(player)) {
            //debug mode canvas.drawRect(rectangle, paint);
            //Production mode
             animManager.draw(canvas,rectangle);
        } else {
            soundManager.playBonusSound();
            generateBadGuyRandomPosition();
            int currentScore = manager.getScore();
            int newScore = currentScore + Constants.COIN_PTS;
            manager.setScore(newScore);
        }
    }

    @Override
    public void update() {
        float oldLeft = rectangle.left;
        int state = 0;
        if (rectangle.left - oldLeft > 5) {
            state = 1;
        } else if (rectangle.left - oldLeft < -5) {
            state = 2;
        }
        //If you want to add animation on the badguy later stage implement here
        animManager.playAnimation(state);
        animManager.update();
    }

    public boolean isBadGuyTakenByThePlayer(RectPlayer player) {
        return Rect.intersects(rectangle, player.getRectangle());
    }

    private void generateBadGuyRandomPosition() {
        Random r = new Random();
        int low = 100 + Constants.BORDER;
        int high = Constants.SCREEN_WIDTH - Constants.BORDER;
        int first = r.nextInt(high - low) + low;
        int second = r.nextInt(high - low) + low;
        this.rectangle = new Rect(first, second, first + Constants.OFFSET, second + Constants.OFFSET);
    }

}
