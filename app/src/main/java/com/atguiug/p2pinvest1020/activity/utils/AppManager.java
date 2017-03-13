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

    /**
     * 添加
     * @param activity
     */
    public void addActivity(Activity activity){

        //校验
        if(activity!=null) {

            stack.add(activity);
        }
    }

    /**
     * 移除
     * @param activity
     */
    public void removeActivity(Activity activity){

        //校验

        if(activity!=null) {

            for (int i = stack.size()-1; i >=0 ; i--) {

                Activity currentActivity = stack.get(i);

                if(currentActivity.getClass()
                        .equals(activity.getClass())) {

                    currentActivity.finish();

                    stack.remove(currentActivity);

                }
            }
        }

    }

    /**
     * 移除所有
     */
    public void removeAll(){

        for (int i = stack.size()-1; i >=0 ; i--) {

            Activity currentActivity = stack.get(i);

            currentActivity.finish();

            stack.remove(currentActivity);
        }
    }

    /**
     * 移除当前的Activity
     */
    public void removeCurrentActivity(){
        //栈中最上面的Activity
//        Activity activity = stack.get(stack.size() - 1);
        Activity activity = stack.lastElement();
        activity.finish();

        stack.remove(activity);
    }

    public int getStackSize(){

        return stack.size();
    }

    //从stack删除activity
    public void remove(Activity activity) {
        if (activity != null){
            for (int i = stack.size()-1; i >=0; i--) {
                Activity currentActivity = stack.get(i);
                if (currentActivity == activity){
                    //currentActivity.finish();
                    stack.remove(currentActivity);
                }
            }
        }
    }
}
