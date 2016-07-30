package com.fm_example.testapplication;

import android.graphics.Bitmap;

public class PlayerChara extends MoveObject {
    private Bitmap mBitmap;

    public PlayerChara(int left, int top, int width, int height, Bitmap bitmap, double xSpeed, double ySpeed) {
        super(left, top, width, height, bitmap, xSpeed, ySpeed);
    }

    public void move(int role, int pitch) {
        super.move(role / 2, -(pitch / 2));
    }

    public void moveRestrict(int height, int width) {
        if (this.getButton() > height) {
            this.setLocate((int) this.getLeft(), height - this.getHeight());
        }
        if (this.getTop() < 0) {
            this.setLocate((int) this.getLeft(), 0);
        }
        if (this.getLeft() < 0) {
            this.setLocate(0, (int) this.getTop());
        }
        if (this.getRight() > width) {
            this.setLocate(width - this.getWidth(), (int) this.getTop());
        }
    }

}
