package com.fm_example.testapplication;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.media.Image;

import java.util.Iterator;

public class GageDisplay {

    private static final int PLAYER_HP = 4;
    public static Region mRegionWholeScreen;
    private Region mRegionGameEnd;
    private Path mGameEnd;
    private Path mBossHpZone;
    private Region mRegionBossHpZone;


    private Bitmap[] mBitmaplife = new Bitmap[PLAYER_HP];
    public static ItemObject[] mPlayerlife = new ItemObject[PLAYER_HP];


    public void playerHpDraw(Resources rsc, int width, int height, int itemwidth) {

        for (int i = 0; i < PLAYER_HP; i++) {
            mBitmaplife[i] = BitmapFactory.decodeResource(rsc, R.drawable.playerlife);
            mBitmaplife[i] = Bitmap.createScaledBitmap(mBitmaplife[i], width / 23,
                    height / 32, false);
        }

        int top = height -itemwidth*4;
        int i = 0;
        for (int left = itemwidth; left <= PLAYER_HP * itemwidth; left += itemwidth) {
            mPlayerlife[i] = new ItemObject(left, top, mBitmaplife[i].getWidth(),
                    mBitmaplife[i].getHeight(), mBitmaplife[i]);
            i++;
        }
    }

    public void wholeScreen(int width, int height) {
        mRegionWholeScreen = new Region(0, 0, width, height);
        mRegionGameEnd = new Region();
        mGameEnd= new Path();
    }




    public void bossHpZone(int x1, int y1, int x2, int y2, Paint paint, Canvas canvas) {
        mBossHpZone = new Path();
        mBossHpZone.addRect(x1, y1, x2, y2, Path.Direction.CW);
        mRegionBossHpZone = new Region();
        mRegionBossHpZone.setPath(mBossHpZone, mRegionWholeScreen);

        setColor(paint, 83, 197, 213);
        canvas.drawPath(mBossHpZone, paint);
    }

    private void setColor(Paint paint, int r, int g, int y) {
        paint.setARGB(255, r, g, y);
    }

}
