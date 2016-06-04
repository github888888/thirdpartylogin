package com.bupt.ktc;

/**
 * Created by ktcer on 2016/6/3.
 */
public class OnChage {
    private static OnChage instance = new OnChage();
    public OnChageListener chageListener;

    public static OnChage getInstance() {
        return instance;
    }

    public void setChageListener(OnChageListener chageListener) {
        this.chageListener = chageListener;
    }
}
