package com.witkey.coder.zhdaily.utils;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * 手势监听
 *
 * @see <a href="http://developer.android.com/samples/BasicGestureDetect/src/com.example.android.basicgesturedetect/GestureListener.html">参考</a>
 */
public class GestureListener extends GestureDetector.SimpleOnGestureListener implements FlingObject {
    private static final int FLING_DISTANCE = 50;
    private static final int TO_RIGHT = 1;
    private static final int TO_LEFT = 2;
    private ArrayList<FlingListener> flingListeners = new ArrayList<>();

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX() - e2.getX() > FLING_DISTANCE) {
            notifyListener(TO_LEFT);
            return true;
        } else if(e2.getX() - e1.getX() > FLING_DISTANCE) {
            notifyListener(TO_RIGHT);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void registeListener(FlingListener listener) {
        flingListeners.add(listener);
    }

    @Override
    public void removeListener(FlingListener listener) {
        int index = flingListeners.indexOf(listener);
        if(index != -1) {
            flingListeners.remove(index);
        }
    }

    @Override
    public void notifyListener(int type) {
        for(FlingListener f : flingListeners) {
            f.doAnimation(type);
        }
    }
}
