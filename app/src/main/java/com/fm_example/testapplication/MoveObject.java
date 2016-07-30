package com.fm_example.testapplication;


import android.graphics.Bitmap;

public class MoveObject extends ItemObject {
    protected double mSpeedX;
    protected double mSpeedY;
    private double rot;
    private double rad;

    public MoveObject(int left, int top, int width, int height, Bitmap bitmap, double xSpeed, double ySpeed) {
        super(left, top, width, height, bitmap);
        setSpeed(xSpeed, ySpeed);
    }

    public void move(double SpeedX, double SpeedY) {
        super.move(SpeedX, SpeedY);
    }

    public void circleMove(double speed, double size, int plusMinus) {
        rot += speed;
        rad = Math.toRadians(rot);
        super.move(Math.cos(rad) * size, Math.sin(rad) * size * plusMinus);

    }

    public void setSpeed(double xSpeed, double ySpeed) {
        this.mSpeedX = xSpeed;
        this.mSpeedY = ySpeed;
    }

    public double getSpeedX() {
        return mSpeedX;
    }

    public double getSpeedY() {
        return mSpeedY;
    }
}
