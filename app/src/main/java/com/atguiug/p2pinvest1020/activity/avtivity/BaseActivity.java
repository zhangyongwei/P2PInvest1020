package com.atguiug.p2pinvest1020.activity.avtivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.atguiug.p2pinvest1020.activity.bean.DataBean;
import com.atguiug.p2pinvest1020.activity.bean.UserInfo;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutid());
        ButterKnife.inject(this);

        initTitle();

        initData();

        initListener();
    }

    public abstract void initListener();

    public abstract void initData();

    public abstract void initTitle() ;


    public abstract int getLayoutid();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    //弹吐丝
    public void showToast(String context){

        Toast.makeText(this, context, Toast.LENGTH_SHORT).show();
    }

    //保存用户信息
    public void saveUser(UserInfo userInfo){

        SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);

        SharedPreferences.Editor edit = sp.edit();

        edit.putString("imageurl",userInfo.getData().getImageurl());

        edit.putString("iscredit",userInfo.getData().getIscredit());

        edit.putString("name",userInfo.getData().getName());

        edit.putString("phone",userInfo.getData().getPhone());

        edit.commit();
    }

    //获取用户信息
    public UserInfo getUser(){

        SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);

        String imageurl = sp.getString("imageurl", "");

        String iscredit = sp.getString("iscredit", "");

        String name = sp.getString("name", "");

        String phone = sp.getString("phone", "");

        UserInfo userInfo = new UserInfo();

        DataBean dataBean  = new DataBean();

        dataBean.setImageurl(imageurl);

        dataBean.setIscredit(iscredit);

        dataBean.setName(name);

        dataBean.setPhone(phone);

        userInfo.setData(dataBean);

        return userInfo;

    }
}
