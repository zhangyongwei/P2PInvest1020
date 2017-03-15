package com.atguiug.p2pinvest1020.activity.avtivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atguiug.p2pinvest1020.R;
import com.atguiug.p2pinvest1020.activity.utils.AppManager;

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
