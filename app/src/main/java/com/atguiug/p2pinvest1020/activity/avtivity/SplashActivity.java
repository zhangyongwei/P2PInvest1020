package com.atguiug.p2pinvest1020.activity.avtivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atguiug.p2pinvest1020.R;
import com.atguiug.p2pinvest1020.activity.MainActivity;
import com.atguiug.p2pinvest1020.activity.utils.AppManager;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SplashActivity extends AppCompatActivity {

    @InjectView(R.id.splash_tv_version)
    TextView splashTvVersion;
    @InjectView(R.id.activity_splash)
    RelativeLayout activitySplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.inject(this);

        AppManager.getInstance().addActivity(this);

        initData();
    }

    private void initData() {

        //设置版本号
        setVersion();

        //设置动画
        setAnimation();

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

            @Override
            public void onAnimationEnd(Animation animation) {

                //动画执行完后进行跳转
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                //启动动画
                startActivity(intent);
                //关闭当前页面
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        activitySplash.startAnimation(animation);
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
