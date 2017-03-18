package com.atguiug.p2pinvest1020.activity.avtivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.atguiug.p2pinvest1020.R;
import com.atguiug.p2pinvest1020.activity.bean.UpdateBean;
import com.atguiug.p2pinvest1020.activity.utils.AppManager;
import com.atguiug.p2pinvest1020.activity.utils.AppNetConfig;
import com.atguiug.p2pinvest1020.activity.utils.LoadNet;
import com.atguiug.p2pinvest1020.activity.utils.LoadNetHttp;
import com.atguiug.p2pinvest1020.activity.utils.ThreadPool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.InjectView;

public class SplashActivity extends BaseActivity {

    @InjectView(R.id.splash_tv_version)
    TextView splashTvVersion;
    @InjectView(R.id.activity_splash)
    RelativeLayout activitySplash;


    @Override
    public void initListener() {

    }

    public void initData() {

        AppManager.getInstance().addActivity(this);
        //设置版本号
        setVersion();

        //设置动画
        setAnimation();

        checkUpdate();

    }

    private void checkUpdate() {
        
        if(isOnLine()) {
            
            //联网
            LoadNet.getDataPost(AppNetConfig.UPDATE, new LoadNetHttp() {
                @Override
                public void success(String context) {

                    Log.e("TAG", "success:"+context);

                    final UpdateBean updateBean = JSON.parseObject(context, UpdateBean.class);
                    
                    //判断当前的版本号
                    if(!getVersion().equals(updateBean.getVersion())) {

                        //提示有新版本
                        new AlertDialog.Builder(SplashActivity.this)
                                .setTitle("有新版本可以更新了")
                                .setMessage(updateBean.getDesc())
                                .setPositiveButton("下载", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        updateVersion(updateBean.getApkUrl());
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        toMain();

                                    }
                                })
                                .show();

                    }else{

                        //直接进入主界面
                        toMain();
                    }
                }

                @Override
                public void failure(String error) {

                    Log.e("TAG", "error:"+error);
                }
            });
        }else{

            //不要再动画没有执行完之前做进入主界面的动作

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {

                    showToast("当前无网络");

                    toMain();
                }
            },2000);

        }
    }

    /**
     * 更新版本
     * @param apkUrl
     */
    private void updateVersion(final String apkUrl) {

        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        progressDialog.show();

        //下载
        ThreadPool.getInstance().getGlobalThread().execute(new Runnable() {

            private FileOutputStream os;
            private InputStream is;

            @Override
            public void run() {

                try {

                    URL url = new URL(AppNetConfig.BASE_URL+"app_new.apk");

                    //打开连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setReadTimeout(8000);//读取时间

                    conn.setConnectTimeout(8000);//联网时间

                    conn.setRequestMethod("GET");//请求联网方式

                    conn.connect();//连接网络

                    if(conn.getResponseCode()==200) {//联网成功
                        //进度条的总长度
                        progressDialog.setMax(conn.getContentLength());

                        is = conn.getInputStream();

                        File path;

                        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                            path = getExternalFilesDir("");
                        }else{

                            path = getFilesDir();
                        }
                        File file = new File(path,"update.apk");

                        os = new FileOutputStream(file);

                        byte[] b = new byte[1024];

                        int len;

                        while ((len = is.read(b))!=-1){

                            progressDialog.incrementProgressBy(len);

                            os.write(b,0,len);
                        }

                        //下载成功了
                        progressDialog.dismiss();
                        //安装
                        Intent intent = new Intent("android.intent.action.INSTALL_PACKAGE");

                        intent.setData(Uri.parse("file:"+file.getAbsolutePath()));

                        startActivity(intent);


                    }else{//联网失败

                        showToast("联网失败，请重新连接");

                        toMain();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }finally {

                    if(os!=null) {

                        try {
                            os.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if(is!=null) {

                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public void toMain(){

        if(isLogin()) {

            //登录过进入主界面
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            //关闭当前页面
            finish();
        }else{

            //没有登录过进入登录界面
            startActivity(new Intent(SplashActivity.this,LoginActivity.class));

            finish();
        }
    }


    /**
     * 判断是否有网
     * @return
     */
    private boolean isOnLine() {

        boolean connected = false;

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if(networkInfo!=null) {

            connected = networkInfo.isConnected();
        }
        return true;
    }

    @Override
    public void initTitle() {

    }

    @Override
    public int getLayoutid() {

        return R.layout.activity_splash;
    }

    /**
     * 添加动画
     */
    private void setAnimation() {
        //渐变动画
        AlphaAnimation animation = new AlphaAnimation(0,1);
        //设置动画持续时间
        animation.setDuration(2000);

        //添加动画监听
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            //动画执行完
            @Override
            public void onAnimationEnd(Animation animation) {


//                if(isLogin()) {
//
//                    //登录过进入主界面
//                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                    //关闭当前页面
//                    finish();
//                }else{
//
//                    //没有登录过进入登录界面
//                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
//
//                    finish();
//                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        activitySplash.startAnimation(animation);
    }

    private boolean isLogin() {

        String name = getUser().getData().getName();

        if(TextUtils.isEmpty(name)) {

            return false;
        }
        return true;
    }

    /**
     * 设置版本号
     *
     */
    private void setVersion() {

        splashTvVersion.setText(getVersion());
    }

    private String getVersion() {


        try {
            //拿到包的管理器
            PackageManager packageManager = getPackageManager();
            //拿到包的信息
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            //versionCode每次发布一个版本都要加1
            int versionCode = packageInfo.versionCode;
            //当前的版本号
            String versionName = packageInfo.versionName;

            return versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

       // AppManager.getInstance().removeActivity(this);
       // AppManager.getInstance().removeCurrentActivity();
        AppManager.getInstance().remove(this);
    }
}
