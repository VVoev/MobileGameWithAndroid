package me.icytower.UltimateCop.Core;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

import me.icytower.UltimateCop.Contracts.GameObject;
import me.icytower.UltimateCop.Core.Sound.SoundManager;

public class Coin implements GameObject {
    private Rect rectangle;
    private int color;
    private RectPlayer player;
    private ObstacleManager manager;
    private SoundManager soundManager;

    public Coin(int rectHeight, int startX, int startY, int color, RectPlayer player, ObstacleManager manager) {
        this.color = color;

        //fixed starting position
        //this.rectangle = new Rect(0,startY,startX,startY+rectHeight);

        //random position
        generateCoinRandomPosition();

        this.player = player;
        this.manager = manager;
        this.soundManager = new SoundManager(Constants.CONTEXT);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        if (!isCoinTakenByThePlayer(player)) {
            //BitmapFactory bf = new BitmapFactory();
            //Bitmap image = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.f1);
            //canvas.drawBitmap(image,rectangle.left,rectangle.top,null);
            canvas.drawRect(rectangle, paint);
        } else {
            soundManager.playBonusSound();
            generateCoinRandomPosition();
            int currentScore = manager.getScore();
            int newScore = currentScore + Constants.COIN_PTS;
            manager.setScore(newScore);
        }
    }


    private void generateCoinRandomPosition() {
        Random r = new Random();
        int low = 100 + Constants.BORDER;
        int high = Constants.SCREEN_WIDTH - Constants.BORDER;
        int first = r.nextInt(high - low) + low;
        int second = r.nextInt(high - low) + low;
        this.rectangle = new Rect(first, second, first + Constants.OFFSET, second + Constants.OFFSET);
    }

    @Override
    public void update() {

    }

    public boolean isCoinTakenByThePlayer(RectPlayer player) {
        return Rect.intersects(rectangle, player.getRectangle());
    }
}
