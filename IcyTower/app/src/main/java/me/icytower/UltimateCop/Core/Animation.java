package me.icytower.UltimateCop.Core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;


public class Animation {
    private Bitmap[] frames;
    private int frameIndex;

    private float frameTime;
    private long lastFrame;

    private Boolean isPlaying = false;

    private long setLastFrame() {
        return System.currentTimeMillis();
    }

    public Animation(Bitmap[] frames, float animTimes) {
        this.frames = frames;
        this.frameIndex = 0;
        this.frameTime = animTimes / frames.length;
        this.lastFrame = setLastFrame();
    }

    public Boolean isPlaying() {
        return this.isPlaying;
    }

    public void play() {
        this.isPlaying = true;
        this.frameIndex = 0;
        this.lastFrame = setLastFrame();
    }

    public void stop() {
        this.isPlaying = false;
    }

    public void draw(Canvas canvas, Rect destination) {
        //nothing to draw
        if (!isPlaying) {
            return;
        }

        scaleRect(destination);

        canvas.drawBitmap(frames[frameIndex], null, destination, new Paint());
    }

    private void scaleRect(Rect rect) {
        float whRatio = (float)(frames[frameIndex].getWidth()) / frames[frameIndex].getHeight();
        //System.out.println(whRatio);

        if (rect.width() > rect.height()) {
            rect.left = rect.right - (int) (rect.height() * whRatio);
        } else {
            rect.top = rect.bottom - (int) (rect.width() * (1 / whRatio));
        }
    }

    public void update() {
        if (!isPlaying) {
            return;
        }

        if (System.currentTimeMillis() - lastFrame > frameTime * 1000) {
            frameIndex++;
            frameIndex = frameIndex >= frames.length ? 0 : frameIndex;
            lastFrame = setLastFrame();
        }
    }
}