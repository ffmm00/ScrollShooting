package com.fm_example.testapplication;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class FirstStage extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private final int mScrollSpeed = 2;
    private final int BOSS_HP = 7;
    private int mPlayerHp = 4;
    private int mLifeCount;
    public static int score = 0;
    private View mDecor;
    private SurfaceHolder mHolder;
    private HitCheck mHitCheck;
    private boolean mIsAttached;
    private Paint mPaint = null;
    public static int mWidth;
    public static int mHeight;
    private Bitmap mBackGround;
    private Bitmap mBackGroundRev;
    private Bitmap mWeakEnemyOne;
    private Bitmap mWeakEnemyTwo;
    private Bitmap mPlayer;
    private Bitmap mPlayerBullet;
    private Bitmap mEnemyBullet;
    private Bitmap mBitmapButton;
    private Bitmap mFirstBoss;
    private int mBossDamage;
    private Path mEndButton;
    private Region mRegionEndButton;
    private boolean mIsClear = false;
    private boolean mIsFailed = false;

    //playerBullet
    private int pbLeft;
    private int pbTop;
    private int pbSpeed;

    private Thread mThread;
    private Canvas mCanvas;

    private MoveObject mBossChara;

    private PlayerChara mPlayerChara;
    private ItemObject mButton;
    private GageDisplay mGageDisplay;
    private BackGroundScroll mBackGroundScroll;
    private FirstStageWeakEnemyContent weakEnemyContent;
    private EndScreen mEndScreen;
    private long mStartTime;
    private long mEndTime;
    private int mEnemyNum = 0;
    private long mPlayerBulletSecondSave;
    private boolean mIsFlag = false;

    //10=1秒
    //16
    private int[] mEnemyTime = {20, 6, 6, 6, 6, 6, 6, 6, 6, 15, 15, 15, 15, 15, 15, 85, 35};
    private int[] mBossEnemyTime = {20, 15, 600000};

    private List<MoveObject> mPlayerBulletList = new ArrayList<MoveObject>();

    public FirstStage(Context context) {
        super(context);

        ((Activity) getContext()).setContentView(this);
        mDecor = ((Activity) getContext()).getWindow().getDecorView();
        mDecor.setSystemUiVisibility(SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE);

        mHolder = getHolder();
        mHolder.addCallback(this);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mWidth = getWidth();
        mHeight = getHeight();

        Resources rsc = getResources();

        mPlayer = BitmapFactory.decodeResource(rsc, R.drawable.player);
        mPlayer = Bitmap.createScaledBitmap(mPlayer, mWidth / 10,
                mHeight / 15, false);

        mPlayerBullet = BitmapFactory.decodeResource(rsc, R.drawable.playerbullet);
        mPlayerBullet = Bitmap.createScaledBitmap(mPlayerBullet, mWidth / 24,
                mHeight / 38, false);

        mBackGround = BitmapFactory.decodeResource(rsc, R.drawable.background);
        mBackGround = Bitmap.createScaledBitmap(mBackGround, mWidth, mHeight, false);
        mBackGroundRev = BitmapFactory.decodeResource(rsc, R.drawable.backgroundb);
        mBackGroundRev = Bitmap.createScaledBitmap(mBackGroundRev, mWidth, mHeight, false);
        mBitmapButton = BitmapFactory.decodeResource(rsc, R.drawable.button);
        mBitmapButton = Bitmap.createScaledBitmap(mBitmapButton, mWidth / 2, mHeight / 19, false);

        mWeakEnemyTwo = BitmapFactory.decodeResource(rsc, R.drawable.weakemeny_2);
        mWeakEnemyTwo = Bitmap.createScaledBitmap(mWeakEnemyTwo, 90, 90, false);
        mWeakEnemyOne = BitmapFactory.decodeResource(rsc, R.drawable.weakenemy_1);
        mWeakEnemyOne = Bitmap.createScaledBitmap(mWeakEnemyOne, 90, 90, false);
        mFirstBoss = BitmapFactory.decodeResource(rsc, R.drawable.firstboss);
        mFirstBoss = Bitmap.createScaledBitmap(mFirstBoss, 250, 250, false);

        mGageDisplay = new GageDisplay();
        mGageDisplay.wholeScreen(mWidth, mHeight);
        mGageDisplay.playerHpDraw(rsc, mWidth, mHeight, mPlayer.getHeight() / 2);

        mEndScreen = new EndScreen();

        mBackGroundScroll = new BackGroundScroll(0, 0, mWidth, mHeight, mBackGround, 0, 0);

        weakEnemyContent = new FirstStageWeakEnemyContent(0, 0, mWeakEnemyOne.getWidth(), mWeakEnemyOne.getHeight(),
                mWeakEnemyOne, 0, 0);

        weakEnemyContent.newEnemy(mWeakEnemyOne, mWeakEnemyTwo);

        mHitCheck = new HitCheck();

        newPlayer();
        newBoss();
        newButton();

        mStartTime = System.currentTimeMillis() / 100;
        mPlayerBulletSecondSave = System.currentTimeMillis() / 100;

        mIsAttached = true;
        mThread = new Thread(this);
        mThread.start();

    }

    @Override
    public void run() {
        while (mIsAttached) {
            drawGameBoard();
        }
    }

    public void drawGameBoard() {
        if (mIsFailed || mIsClear) {
            return;
        }

        if (mPlayerHp <= 0) {
            mIsFailed = true;
        }
        if (mBossDamage >= BOSS_HP) {
            score += 2000;
            mIsClear = true;
        }


        if (!mIsClear && !mIsFailed) {
            mPlayerChara.move(FirstStageMove.role, FirstStageMove.pitch);

            mPlayerChara.moveRestrict(mHeight, mWidth);

            if (mEndTime - mPlayerBulletSecondSave == 4) {
                newPlayerBullet();
                mPlayerBulletSecondSave = mEndTime;
            }

            for (MoveObject moveObject : mPlayerBulletList) {
                if (moveObject != null) {
                    moveObject.move(moveObject.getSpeedX(), moveObject.getSpeedY());
                }
            }
        }

        mHitCheck.outsideDelete(mPlayerBulletList, mWidth, mHeight);

        mEndTime = System.currentTimeMillis() / 100;

        if (!mIsFlag) {
            scrollMode();
        } else {
            bossMode();
        }


    }

    private void scrollMode() {

        if (mEndTime - mStartTime == mEnemyTime[mEnemyNum]) {
            mStartTime = mEndTime;
            if (mEnemyNum != mEnemyTime.length) {
                mEnemyNum++;
            }
        }

        mCanvas = getHolder().lockCanvas();
        if (mCanvas != null) {

            mBackGroundScroll.ScrollMove(mCanvas, mBackGround, mBackGroundRev, mPaint, mHeight);
            mBackGroundScroll.ScrollSpeed();

            if (mIsFailed) {
                bitmapTransparnent();
                endButton();
                mEndScreen.endGame(mButton, mPaint, mCanvas, mWidth);
            }

            if (mEnemyNum < mEnemyTime.length - 1) {
                for (int i = 0; i < mEnemyNum; i++) {
                    FirstStageWeakEnemyContent.WeakEnemyList[i].draw(mCanvas);

                    FirstStageWeakEnemyContent.WeakEnemyList[i].move(
                            FirstStageWeakEnemyContent.WeakEnemyList[i].getSpeedX(),
                            FirstStageWeakEnemyContent.WeakEnemyList[i].getSpeedY());

                    if (mHitCheck.IsEnemyHitCheck(mPlayerChara, FirstStageWeakEnemyContent.WeakEnemyList[i])) {
                        FirstStageWeakEnemyContent.WeakEnemyList[i].setSpeed(0, 0);
                        FirstStageWeakEnemyContent.WeakEnemyList[i].setLocate(-3000, -500);
                        mPlayerHp--;
                    }


                    if (mHitCheck.bulletHitCheck(mPlayerBulletList, FirstStageWeakEnemyContent.WeakEnemyList[i])) {
                        score += 500;
                        FirstStageWeakEnemyContent.WeakEnemyList[i].setSpeed(0, 0);
                        FirstStageWeakEnemyContent.WeakEnemyList[i].setLocate(-3000, -500);
                    }
                }


                for (MoveObject moveObject : mPlayerBulletList) {
                    mCanvas.drawBitmap(mPlayerBullet, (int) moveObject.getLeft(), (int) moveObject.getTop(), null);
                }
            }

            if (mEnemyNum == mEnemyTime.length - 1) {
                mBossChara.move(0, 2);
                mBossChara.draw(mCanvas);
            }

            mPlayerChara.draw(mCanvas);

            for (int i = 0; i < mPlayerHp; i++) {
                mGageDisplay.mPlayerlife[i].draw(mCanvas);
            }
            getHolder().unlockCanvasAndPost(mCanvas);
        }


        if (mEnemyNum == mEnemyTime.length) {
            mEnemyNum = 0;
            mIsFlag = true;
        }
    }

    private void bossMode() {

        if (mEnemyNum != 3) {
            if (mEndTime - mStartTime == mBossEnemyTime[mEnemyNum]) {
                mStartTime = mEndTime;
                if (mEnemyNum != mBossEnemyTime.length) {
                    mEnemyNum++;
                }
            }
        }

        mCanvas = getHolder().lockCanvas();
        if (mCanvas != null) {

            mBackGroundScroll.ScrollMove(mCanvas, mBackGround, mBackGroundRev, mPaint, mHeight);

            if (mIsClear || mIsFailed) {
                bitmapTransparnent();
                endButton();
                mEndScreen.endGame(mButton, mPaint, mCanvas, mWidth);
                if (mIsClear) {
                    mEndScreen.clearView(mPaint, mCanvas, mWidth);
                }
            }

            mPlayerChara.draw(mCanvas);

            if (mPlayerHp != 0) {
                for (int i = 0; i < mPlayerHp; i++) {
                    mGageDisplay.mPlayerlife[i].draw(mCanvas);
                }
            }

            if (mEnemyNum == 2) {
                mBossChara.circleMove(2.5, 8, 1);
                for (MoveObject moveObject : mPlayerBulletList) {
                    mCanvas.drawBitmap(mPlayerBullet, (int) moveObject.getLeft(), (int) moveObject.getTop(), null);
                }
            }


            if (mEnemyNum == 0) {
            }

            if (mEnemyNum == 2) {
                if (mHitCheck.bulletHitCheck(mPlayerBulletList, mBossChara)) {
                    mBossDamage++;
                }
            }

            if (mEnemyNum >= 1) {
                mGageDisplay.bossHpZone(mPlayerChara.getHeight(), mPlayerChara.getHeight(),
                        30 * (BOSS_HP - mBossDamage) + mPlayerChara.getHeight(),
                        mPlayer.getHeight() * 3 / 2, mPaint, mCanvas);
            }

            if (mHitCheck.IsEnemyHitCheck(mPlayerChara, mBossChara)) {
                mPlayerHp = 0;
            }

            mBossChara.draw(mCanvas);

            getHolder().unlockCanvasAndPost(mCanvas);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        bitmapDestroy(mBackGround);
        bitmapDestroy(mBackGroundRev);
        bitmapDestroy(mWeakEnemyOne);
        bitmapDestroy(mPlayer);
        bitmapDestroy(mFirstBoss);
        bitmapDestroy(mPlayerBullet);
        bitmapDestroy(mBitmapButton);
        score = 0;
        mIsAttached = false;
        ((Activity) getContext()).finish();
    }

    private void bitmapDestroy(Bitmap bitmap) {
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
    }

    private void bitmapTransparnent() {
        mPlayer.eraseColor(Color.TRANSPARENT);
        mWeakEnemyOne.eraseColor(Color.TRANSPARENT);
        mWeakEnemyTwo.eraseColor(Color.TRANSPARENT);
        mFirstBoss.eraseColor(Color.TRANSPARENT);
        mPlayerBullet.eraseColor(Color.TRANSPARENT);
    }


    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mIsClear || mIsFailed) {
                    if (mRegionEndButton.contains((int) event.getX(), (int) event.getY())) {
                        ((Activity) getContext()).finish();
                    }
                }
                break;
            default:
                break;
        }
        return true;
    }

    private void newPlayer() {
        mPlayerChara = new PlayerChara(mWidth / 2 - mPlayer.getWidth() / 2,
                mHeight - (2 * mPlayer.getHeight()), mPlayer.getWidth(), mPlayer.getHeight(),
                mPlayer, 0, 0);
    }

    private void newPlayerBullet() {
        MoveObject moveObject;
        pbLeft = (int) mPlayerChara.getCenterX() - mPlayerBullet.getWidth() / 2;
        pbTop = (int) mPlayerChara.getTop() - 15;
        pbSpeed = 20;
        moveObject = new MoveObject(pbLeft, pbTop, mPlayerBullet.getWidth(),
                mPlayerBullet.getHeight(), mPlayerBullet, 0, -pbSpeed);

        mPlayerBulletList.add(moveObject);
    }

    private void newBoss() {
        mBossChara = new MoveObject(mWidth / 2 - mFirstBoss.getWidth() / 2, -mFirstBoss.getWidth(),
                mPlayer.getWidth(), mPlayer.getHeight(), mFirstBoss, 0, 0);
    }

    private void newButton() {
        mButton = new ItemObject(mWidth / 2 - mBitmapButton.getWidth() / 2, mWidth / 2 + 190,
                mBitmapButton.getWidth(), mBitmapButton.getHeight(), mBitmapButton);
    }

    private void endButton() {
        mEndButton = new Path();
        mEndButton.addRect(mWidth / 2 - mBitmapButton.getWidth() / 2, mWidth / 2 + 190,
                mWidth / 2 + mBitmapButton.getWidth(),
                mWidth / 2 + 190 + mBitmapButton.getHeight(), Path.Direction.CW);
        mRegionEndButton = new Region();
        mRegionEndButton.setPath(mEndButton, GageDisplay.mRegionWholeScreen);
    }

}
