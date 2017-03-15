package com.atguiug.p2pinvest1020.activity.avtivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atguiug.p2pinvest1020.R;
import com.atguiug.p2pinvest1020.activity.utils.AppNetConfig;
import com.atguiug.p2pinvest1020.activity.utils.LoadNet;
import com.atguiug.p2pinvest1020.activity.utils.LoadNetHttp;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;

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

                //校验
                String phone = loginEtNumber.getText().toString().trim();

                String pw = loginEtPwd.getText().toString().trim();

                if(TextUtils.isEmpty(phone)) {

                    showToast("账号不能为空");

                    return;
                }
                if(TextUtils.isEmpty(pw)) {

                    showToast("密码不能为空");

                    return;
                }

                //去服务器登录
                Map<String,String> map = new HashMap<String, String>();

                map.put("phone",phone);

                map.put("password",pw);

                LoadNet.getDataPost(AppNetConfig.LOGIN,map,new LoadNetHttp(){

                    @Override
                    public void success(String context) {

                        Log.i("TAG", "success:"+context);
                    }

                    @Override
                    public void failure(String error) {

                        Log.e("TAG", "error:"+error);

                    }
                });
            }
        });

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
