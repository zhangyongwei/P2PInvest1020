package com.atguiug.p2pinvest1020.activity.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by 张永卫on 2017/3/11.
 */

public class AppManager {
    /**
     * 单例
     */
    private AppManager(){};

    private static AppManager appManager = new AppManager();

    public static AppManager getInstance(){

        return appManager;
    }

    private Stack<Activity> stack = new Stack<>();

    public void addActivity(Activity activity){

        //校验
        if(activity!=null) {

            stack.add(activity);
        }
    }

}
