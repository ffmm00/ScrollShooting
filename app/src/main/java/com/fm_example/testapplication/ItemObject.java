package com.fm_example.testapplication;


import android.graphics.Bitmap;
import android.graphics.Canvas;

public class ItemObject {

    private double mTop;
    private double mLeft;
    private int mHeight;
    private int mWidth;
    private Bitmap mBitmap;

    public ItemObject(int left, int top, int width, int height, Bitmap bitmap) {
        setLocate(left, top);
        this.mWidth = width;
        this.mHeight = height;
        this.mBitmap = bitmap;
    }

    public void setLocate(int left, int top) {
        this.mLeft = left;
        this.mTop = top;
    }

    public void setBitmap(Bitmap bitmap){
        this.mBitmap=bitmap;
    }

    public Bitmap getBitmap(){
        return mBitmap;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, (int) getLeft(), (int) getTop(), null);
    }

    public void move(double left, double top) {
        this.mLeft = left + getLeft();
        this.mTop = top + getTop();
    }


    public double getLeft() {
        return mLeft;
    }

    public double getRight() {
        return mLeft + mWidth;
    }

    public double getTop() {
        return mTop;
    }

    public int getWidth(){
        return mWidth;
    }

    public int getHeight(){
        return mHeight;
    }


    public double getButton() {
        return mTop + mHeight;
    }

    public double getCenterX() {
        return getLeft() + mWidth / 2;
    }

    public double getCenterY() {
        return getTop() + mHeight / 2;
    }

}
