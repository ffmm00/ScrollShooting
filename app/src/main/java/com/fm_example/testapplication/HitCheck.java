package com.fm_example.testapplication;

import android.graphics.Bitmap;

import java.util.Iterator;
import java.util.List;

public class HitCheck {

    private int mSafeArea = 76;

    public void outsideDelete(List list, int width, int height) {
        Iterator<MoveObject> bulletIterator = list.iterator();
        while (bulletIterator.hasNext()) {
            MoveObject bullet = bulletIterator.next();
            if (bullet.getRight() < -bullet.getWidth() * 2 ||
                    bullet.getRight() > width + bullet.getWidth() * 2 ||
                    bullet.getButton() < -bullet.getWidth() * 2 ||
                    bullet.getTop() > height + bullet.getWidth() * 2) {
                bulletIterator.remove();
            }
        }

    }

    public boolean bulletHitCheck(List bulletList, MoveObject chara) {
        Iterator<MoveObject> bulletIterator = bulletList.iterator();
        while (bulletIterator.hasNext()) {
            MoveObject bullet = bulletIterator.next();
            if (chara.getLeft() < bullet.getRight() &&
                    chara.getTop() < bullet.getButton() &&
                    chara.getRight() > bullet.getLeft() &&
                    chara.getButton() > bullet.getTop()) {
                bulletIterator.remove();
                return true;
            }
        }
        return false;
    }

    public boolean IsEnemyHitCheck(PlayerChara player, MoveObject enemy) {
        if (player.getLeft() + mSafeArea < enemy.getRight() &&
                player.getTop() + mSafeArea < enemy.getButton() &&
                player.getRight() - mSafeArea > enemy.getLeft() &&
                player.getButton() - mSafeArea > enemy.getTop()) {
            return true;
        }
        return false;
    }

}
