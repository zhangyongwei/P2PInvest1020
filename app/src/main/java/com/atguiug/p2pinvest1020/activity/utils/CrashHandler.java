package com.atguiug.p2pinvest1020.activity.utils;

import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by 张永卫on 2017/3/11.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private Context mContext;
    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    //私有构造器
    private CrashHandler(){};

    //定义字段
    private static CrashHandler crashHandler = new CrashHandler();

    //提供公共的方法
    public static CrashHandler getInstance(){

        return crashHandler;
    }

    public void init(Context context){
        //把当前的类设置成默认的处理未捕获异常
        this.mContext = context;
        this.defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);

    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {

//        Log.e("TAG", "uncaughtException:");

        if(e==null) {

            defaultUncaughtExceptionHandler.uncaughtException(t,e);
        }else{

            handlerException(t,e);
        }

    }

    private void handlerException(Thread t, Throwable e) {

        //必须在主线程执行Toast
        new Thread(){

            @Override
            public void run() {
                super.run();

                //Android系统当中，默认情况下，线程是没有开启looper消息处理的，但是主线程除外
                Looper.prepare();

                Toast.makeText(mContext, "亲，出现异常了！", Toast.LENGTH_SHORT).show();

                Looper.loop();

            }
        }.start();
        //搜集异常信息
        collectionException(e);

        try {
            //关闭资源
            Thread.sleep(2000);

            AppManager.getInstance().removeAll();

            android.os.Process.killProcess(android.os.Process.myPid());
            //关闭虚拟机，释放所有内存:参数0代表正常退出
            System.exit(0);

        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    private void collectionException(Throwable e) {

        final String deciceInfo = Build.DEVICE + ":" + Build.VERSION.SDK_INT
                + ":" + Build.MODEL + ":" + Build.PRODUCT;

        final String message = e.getMessage();

        new Thread(){

            @Override
            public void run() {
                super.run();
                //可以通过联网将信息发送给后台，所以在分线程执行
                Log.e("TAG", "deviceInfo:" + deciceInfo + ",message:" + message);

            }
        }.start();
    }
}
