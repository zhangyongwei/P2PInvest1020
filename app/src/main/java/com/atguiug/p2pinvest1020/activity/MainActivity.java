package com.atguiug.p2pinvest1020.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.atguiug.p2pinvest1020.R;
import com.atguiug.p2pinvest1020.activity.fragment.HomeFragment;
import com.atguiug.p2pinvest1020.activity.fragment.InvestFragment;
import com.atguiug.p2pinvest1020.activity.fragment.MoreFragment;
import com.atguiug.p2pinvest1020.activity.fragment.PropertyFragment;
import com.atguiug.p2pinvest1020.activity.utils.AppManager;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.main_rg)
    RadioGroup mainRg;
    private FragmentTransaction transaction;

    private HomeFragment homeFragment;
    private InvestFragment investFragment;
    private PropertyFragment propertyFragment;
    private MoreFragment moreFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        //添加到AppManager
        AppManager.getInstance().addActivity(this);

        initData();

        initListener();
    }

    /**
     * 初始化监听
     */
    private void initListener() {

        mainRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switchFragment(checkedId);
            }
        });


    }

    /**
     * 切换fragment
     * @param checkedId
     */
    private void switchFragment(int checkedId) {

        //开启事务
        transaction = getSupportFragmentManager().beginTransaction();
        
        hiddenFragment(transaction);
        switch (checkedId){

            case R.id.rb_main:
                
                if(homeFragment==null) {

                    homeFragment = new HomeFragment();

                    transaction.add(R.id.main_fl,homeFragment);
                }
                transaction.show(homeFragment);

                break;
            case R.id.rb_invest:

                if(investFragment==null) {

                    investFragment = new InvestFragment();

                    transaction.add(R.id.main_fl,investFragment);

                }
                transaction.show(investFragment);

                break;
            case R.id.rb_propert:

                if(propertyFragment==null) {

                    propertyFragment = new PropertyFragment();

                    transaction.add(R.id.main_fl,propertyFragment);
                }
                transaction.show(propertyFragment);

                break;
            case R.id.rb_more:

                if(moreFragment==null) {

                    moreFragment = new MoreFragment();

                    transaction.add(R.id.main_fl,moreFragment);
                }
                transaction.show(moreFragment);

                break;
        }

        transaction.commit();
    }

    /**
     * 隐藏fragment
     * @param transaction
     */
    private void hiddenFragment(FragmentTransaction transaction) {

        if(homeFragment!=null) {

            transaction.hide(homeFragment);
        }
        if(investFragment!=null) {

            transaction.hide(investFragment);
        }
        if(propertyFragment!=null) {

            transaction.hide(propertyFragment);
        }
        if(moreFragment!=null) {

            transaction.hide(moreFragment);
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //默认选择的fragment
        switchFragment(R.id.rb_main);
    }

    /**
     * 布尔值判断是否双击退出
     */
    private boolean isDouble = false;
    /**
     * 点击两次退出
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode==KeyEvent.KEYCODE_BACK) {

            if(isDouble) {

                //退出
                finish();
            }
            Toast.makeText(MainActivity.this, "请再点击一次，退出应用", Toast.LENGTH_SHORT).show();
            isDouble = true;

            //超过两次就退出
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {

                    isDouble = false;
                }
            },2000);

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
