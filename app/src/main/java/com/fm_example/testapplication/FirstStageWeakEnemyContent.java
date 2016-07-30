package com.fm_example.testapplication;


import android.graphics.Bitmap;

public class FirstStageWeakEnemyContent extends MoveObject {
    private MoveObject mWeakEnemy;
    private int mCount = 0;
    public static MoveObject[] WeakEnemyList = new MoveObject[20];

    public FirstStageWeakEnemyContent
            (int left, int top, int width, int height, Bitmap bitmap, double xSpeed, double ySpeed) {
        super(left, top, width, height, bitmap, xSpeed, ySpeed);
    }

    public void newEnemy(Bitmap enemyA, Bitmap enemyB) {

        while (mCount != 16) {
            if (mCount <= 3) {
                setLocate(0, 200);
                setSpeed(5.8, 2.0);
                setBitmap(enemyA);
            }

            if (4 <= mCount && mCount <= 7) {
                setLocate(FirstStage.mWidth, 300);
                setSpeed(-5.8, 2.0);
                setBitmap(enemyB);
            }

            if (8 <= mCount && mCount <= 10) {
                setLocate(300, 0);
                setSpeed(0, 5.4);
                setBitmap(enemyA);
            }

            if (11 <= mCount && mCount <= 13) {
                setLocate(870, 0);
                setSpeed(0, 5.4);
                setBitmap(enemyB);
            }

            if (14 <= mCount && mCount == 15) {
                setLocate(-1000, -1000);
                setSpeed(0, 0);
                setBitmap(enemyA);
            }

            mWeakEnemy = new MoveObject((int) getLeft(), (int) getTop(), getWidth(), getHeight(),
                    getBitmap(), getSpeedX(), getSpeedY());

            WeakEnemyList[mCount] = mWeakEnemy;
            mCount++;
        }
    }
}
