package com.atguiug.p2pinvest1020.activity.avtivity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguiug.p2pinvest1020.R;

import butterknife.InjectView;

public class ReChargeActivity extends BaseActivity {


    @InjectView(R.id.base_title)
    TextView baseTitle;
    @InjectView(R.id.base_back)
    ImageView baseBack;
    @InjectView(R.id.base_setting)
    ImageView baseSetting;
    @InjectView(R.id.chongzhi_text)
    TextView chongzhiText;
    @InjectView(R.id.view)
    View view;
    @InjectView(R.id.et_chongzhi)
    EditText etChongzhi;
    @InjectView(R.id.chongzhi_text2)
    TextView chongzhiText2;
    @InjectView(R.id.yue_tv)
    TextView yueTv;
    @InjectView(R.id.btn_chongzhi)
    Button btnChongzhi;

    @Override
    public void initListener() {

        etChongzhi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                Log.e("TAG", "beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Log.e("TAG", "onTextChanged");
            }

            @Override
            public void afterTextChanged(Editable s) {

                String money = s.toString().trim();

                if(TextUtils.isEmpty(money)) {

                    //设置充值不可点击
                    btnChongzhi.setClickable(false);
                    //设置充值的背景颜色
                    btnChongzhi.setBackgroundResource(R.drawable.btn_02);

                }else{

                    //设置充值可点击
                    btnChongzhi.setClickable(true);
                    //设置充值的背景颜色
                    btnChongzhi.setBackgroundResource(R.drawable.btn_01);

                }
            }
        });

        //充值的点击事件
        btnChongzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //调支付宝
            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    public void initTitle() {

    }

    @Override
    public int getLayoutid() {
        return R.layout.activity_re_charge;
    }
}
