package com.fm_example.testapplication;

import java.util.Iterator;
import java.util.List;

public class OutSideDelete {

    public void OutSideDelete(List list, int width, int height) {
        Iterator<MoveObject> moveList = list.iterator();
        while (moveList.hasNext()) {
            MoveObject mo = moveList.next();
            if (mo.getRight() < -mo.getWidth() * 2 ||
                    mo.getRight() > width + mo.getWidth() * 2 ||
                    mo.getButton() < -mo.getWidth() * 3 ||
                    mo.getTop() > height + mo.getWidth() * 2) {
                moveList.remove();
            }


        }
    }
    }
