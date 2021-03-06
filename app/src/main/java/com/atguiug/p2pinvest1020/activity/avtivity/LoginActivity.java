package com.atguiug.p2pinvest1020.activity.avtivity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguiug.p2pinvest1020.R;
import com.atguiug.p2pinvest1020.activity.bean.UserInfo;
import com.atguiug.p2pinvest1020.activity.utils.AppNetConfig;
import com.atguiug.p2pinvest1020.activity.view.ILoginP2p;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends BaseActivity {


    @InjectView(R.id.base_title)
    TextView baseTitle;
    @InjectView(R.id.base_back)
    ImageView baseBack;
    @InjectView(R.id.base_setting)
    ImageView baseSetting;
    @InjectView(R.id.tv_login_number)
    TextView tvLoginNumber;
    @InjectView(R.id.login_et_number)
    EditText loginEtNumber;
    @InjectView(R.id.rl_login)
    RelativeLayout rlLogin;
    @InjectView(R.id.tv_login_pwd)
    TextView tvLoginPwd;
    @InjectView(R.id.login_et_pwd)
    EditText loginEtPwd;
    @InjectView(R.id.btn_login)
    Button btnLogin;
    @InjectView(R.id.login_regitster_tv)
    TextView loginRegitsterTv;

    @Override
    public void initListener() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login();

            }
        });

        loginRegitsterTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this,RegesterActivity.class));
            }
        });
    }

    private void login() {

        String phone = loginEtNumber.getText().toString().trim();

        String pw = loginEtPwd.getText().toString().trim();

        if (TextUtils.isEmpty(phone)) {

            showToast("账号不能为空");

            return;
        }
        if (TextUtils.isEmpty(pw)) {

            showToast("密码不能为空");

            return;
        }

        //去服务器登录
        Map<String, String> map = new HashMap<String, String>();

        map.put("phone", phone);

        map.put("password", pw);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppNetConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        ILoginP2p iLoginP2p = retrofit.create(ILoginP2p.class);

        Call<JsonObject> userInfo = iLoginP2p.getUserInfo(AppNetConfig.LOGIN, map);

        userInfo.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                String json = response.body().toString();
                JSONObject jsonObject = JSON.parseObject(json);

                Boolean success = jsonObject.getBoolean("success");

                if(success) {

                    //解析数据
                    UserInfo userInfo = JSON.parseObject(json, UserInfo.class);

                    //保存数据到sp
                    saveUser(userInfo);

                    //跳转
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    //结束当前页面
                    finish();

                }else{

                    showToast("账号不存在或者密码错误！");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });


//        //校验
//        String phone = loginEtNumber.getText().toString().trim();
//
//        String pw = loginEtPwd.getText().toString().trim();
//
//        if (TextUtils.isEmpty(phone)) {
//
//            showToast("账号不能为空");
//
//            return;
//        }
//        if (TextUtils.isEmpty(pw)) {
//
//            showToast("密码不能为空");
//
//            return;
//        }
//
//        //去服务器登录
//        Map<String, String> map = new HashMap<String, String>();
//
//        map.put("phone", phone);
//
//        map.put("password", pw);
//
//        LoadNet.getDataPost(AppNetConfig.LOGIN, map, new LoadNetHttp() {
//
//            @Override
//            public void success(String context) {
//
//                Log.i("TAG", "success:" + context);
//
//                JSONObject jsonObject = JSON.parseObject(context);
//
//                Boolean success = jsonObject.getBoolean("success");
//
//                if(success) {
//
//                    //解析数据
//                    UserInfo userInfo = JSON.parseObject(context, UserInfo.class);
//
//                    //保存数据到sp
//                    saveUser(userInfo);
//
//                    //跳转
//                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
//                    //结束当前页面
//                    finish();
//
//                }else{
//
//                    showToast("账号不存在或者密码错误！");
//                }
//
//            }
//
//            @Override
//            public void failure(String error) {
//
//                Log.e("TAG", "error:" + error);
//
//            }
//        });

    }

    @Override
    public void initData() {

    }

    @Override
    public void initTitle() {

        baseBack.setVisibility(View.INVISIBLE);

        baseSetting.setVisibility(View.INVISIBLE);

        baseTitle.setText("登录");

    }

    @Override
    public int getLayoutid() {

        return R.layout.activity_login;
    }

}
