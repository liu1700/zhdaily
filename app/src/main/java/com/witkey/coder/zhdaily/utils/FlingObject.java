package com.witkey.coder.zhdaily.utils;

/**
 * 滑动事件接口
 *
 */
public interface FlingObject {
    void registeListener(FlingListener listener);
    void removeListener(FlingListener listener);
    void notifyListener(int type);
}
