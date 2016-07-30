package com.fm_example.testapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class BackGroundScroll extends MoveObject {
    private final double SCROLL_SPEED = 20.0;

    public BackGroundScroll(int left, int top, int width, int height, Bitmap bitmap, double xSpeed, double ySpeed) {
        super(left, top, width, height, bitmap, 0, ySpeed);
    }

    public void ScrollMove(Canvas canvas, Bitmap bitmapone, Bitmap bitmaptwo, Paint paint, int Height) {
        canvas.drawBitmap(bitmapone, 0, (int) mSpeedY, paint);
        canvas.drawBitmap(bitmaptwo, 0, (int) (mSpeedY - Height), paint);
        if (mSpeedY >= Height + SCROLL_SPEED) {
            canvas.drawBitmap(bitmapone, 0, (int) (mSpeedY - Height * 2), paint);
        }
        if (mSpeedY >= Height * 2) {
            mSpeedY = 0;
        }
    }


    public void ScrollSpeed() {
        this.mSpeedY += SCROLL_SPEED;
    }

    public void ScrollStop(){
        this.mSpeedY=0;
    }


}
