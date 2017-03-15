package com.atguiug.p2pinvest1020.activity.avtivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguiug.p2pinvest1020.R;
import com.atguiug.p2pinvest1020.activity.utils.AppNetConfig;
import com.atguiug.p2pinvest1020.activity.utils.LoadNet;
import com.atguiug.p2pinvest1020.activity.utils.LoadNetHttp;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;

public class RegesterActivity extends BaseActivity {


    @InjectView(R.id.base_title)
    TextView baseTitle;
    @InjectView(R.id.base_back)
    ImageView baseBack;
    @InjectView(R.id.base_setting)
    ImageView baseSetting;
    @InjectView(R.id.et_register_number)
    EditText etRegisterNumber;
    @InjectView(R.id.et_register_name)
    EditText etRegisterName;
    @InjectView(R.id.et_register_pwd)
    EditText etRegisterPwd;
    @InjectView(R.id.et_register_pwdagain)
    EditText etRegisterPwdagain;
    @InjectView(R.id.btn_register)
    Button btnRegister;

    @Override
    public void initListener() {

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etRegisterName.getText().toString().trim();

                String number = etRegisterNumber.getText().toString().trim();

                String pwd = etRegisterPwd.getText().toString().trim();

                String pwdAgain = etRegisterPwdagain.getText().toString().trim();

                //判断两个密码是否一致  判断密码的长度  判断是否注册过
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(number)
                        || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(pwdAgain)) {

                    showToast("什么都不能为空！!");

                    return;
                }


                Map<String, String> map = new HashMap<String, String>();

                map.put("name",name);

                map.put("password",pwd);

                map.put("phone",number);

                LoadNet.getDataPost(AppNetConfig.REGISTER, map, new LoadNetHttp() {

                    @Override
                    public void success(String context) {

                        Log.i("TAG", "success:" + context);

                        JSONObject jsonObject = JSON.parseObject(context);

                        Boolean isExist = jsonObject.getBoolean("isExist");

                        if (isExist) {

                            showToast("该账号已经被注册！");
                        } else {

                            showToast("注册成功");

                            finish();
                        }

                    }

                    @Override
                    public void failure(String error) {

                    }
                });
            }
        });
    }

    public void isEmpty(String name,String alert){

        if(TextUtils.isEmpty(name)) {

            showToast(alert);

            return;
        }

    }
    @Override
    public void initData() {

    }

    @Override
    public void initTitle() {

        baseBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        baseSetting.setVisibility(View.INVISIBLE);

        baseTitle.setText("注册");
    }

    @Override
    public int getLayoutid() {

        return R.layout.activity_regester;
    }

}
