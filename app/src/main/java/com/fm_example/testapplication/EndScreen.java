package com.fm_example.testapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class EndScreen {

    public void endGame( ItemObject button, Paint paint, Canvas canvas, int width) {

        button.draw(canvas);
        String msg = "score　"+FirstStage.score;
        paint.setTextSize(70);
        paint.setColor(Color.WHITE);
        canvas.drawText(msg, width / 2-220, width / 2, paint);
    }

    public void clearView(Paint paint, Canvas canvas, int width) {
        String msg = "ゲームクリア";
        paint.setTextSize(70);
        paint.setColor(Color.WHITE);
        canvas.drawText(msg, width/2-200, width/2-90, paint);
    }}