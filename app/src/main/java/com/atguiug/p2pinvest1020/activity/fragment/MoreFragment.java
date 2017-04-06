package com.atguiug.p2pinvest1020.activity.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.atguiug.p2pinvest1020.R;
import com.atguiug.p2pinvest1020.activity.avtivity.GestureEditActivity;
import com.atguiug.p2pinvest1020.activity.utils.AppNetConfig;
import com.atguiug.p2pinvest1020.activity.utils.LoadNet;
import com.atguiug.p2pinvest1020.activity.utils.LoadNetHttp;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by 张永卫on 2017/3/10.
 */

public class MoreFragment extends BaseFragment {


    @InjectView(R.id.base_title)
    TextView baseTitle;
    @InjectView(R.id.base_back)
    ImageView baseBack;
    @InjectView(R.id.base_setting)
    ImageView baseSetting;
    @InjectView(R.id.tv_more_regist)
    TextView tvMoreRegist;
    @InjectView(R.id.toggle_more)
    ToggleButton toggleMore;
    @InjectView(R.id.tv_more_reset)
    TextView tvMoreReset;
    @InjectView(R.id.tv_more_phone)
    TextView tvMorePhone;
    @InjectView(R.id.rl_more_contact)
    RelativeLayout rlMoreContact;
    @InjectView(R.id.tv_more_fankui)
    TextView tvMoreFankui;
    @InjectView(R.id.tv_more_share)
    TextView tvMoreShare;
    @InjectView(R.id.tv_more_about)
    TextView tvMoreAbout;

    @Override
    protected void initListener() {

        toggleMore.setChecked(getState());

        toggleMore.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {//打开手势密码

                    saveState(isChecked);

                    if(!getIsSetting()) {//判断是否设置过

                        setSetting(true);//设置过手势

                        startActivity(new Intent(getActivity(),GestureEditActivity.class));
                    }
                }else{//关闭手势密码
                    //存储当前的状态,用来记录是否打开手势密码
                    saveState(isChecked);
                }
            }
        });

        tvMoreReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //重置手势密码
                startActivity(new Intent(getActivity(),GestureEditActivity.class));
            }
        });

        tvMorePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_CALL);

                Uri uri = Uri.parse("tel:010-56253825");

                intent.setData(uri);
            }
        });

        tvMoreFankui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = View.inflate(getActivity(),R.layout.dialog_fankui,null);

                new AlertDialog.Builder(getActivity())

                        .setView(view)

                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Map<String,String> map = new HashMap<String, String>();

                                map.put("department","");

                                map.put("content","");

                                LoadNet.getDataPost(AppNetConfig.FEEDBACK, map, new LoadNetHttp() {
                                    @Override
                                    public void success(String context) {


                                    }

                                    @Override
                                    public void failure(String error) {

                                    }
                                });
                            }
                        })

                        .setNegativeButton("取消",null)

                        .show();
            }
        });

        tvMoreShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showShare();
            }
        });
    }

    public void setSetting(boolean setting) {

        SharedPreferences sp = getActivity().getSharedPreferences("setting", Context.MODE_PRIVATE);

        sp.edit().putBoolean("isSetting",setting).commit();
    }

    public boolean getIsSetting() {

        SharedPreferences sp = getActivity().getSharedPreferences("setting", Context.MODE_PRIVATE);


        return sp.getBoolean("isSetting",false);
    }

    public void saveState(boolean isOpen) {

        SharedPreferences sp = getActivity().getSharedPreferences("tog_state", Context.MODE_PRIVATE);

        sp.edit().putBoolean("isOpen",isOpen).commit();

    }

    public boolean getState(){

        SharedPreferences sp = getActivity().getSharedPreferences("tog_state", Context.MODE_PRIVATE);

        return sp.getBoolean("isOpen",false);
    }

    @Override
    protected void initData(String json) {

        initTitle();

        initListener();

        setTogState();//设置选择状态
//        //设置手势密码
//        startActivity(new Intent(getActivity(), GestureEditActivity.class));
    }

    /**
     * 设置button状态
     */
    private void setTogState() {

        SharedPreferences sp = getActivity().getSharedPreferences("tog_state", Context.MODE_PRIVATE);

        boolean isOpen = sp.getBoolean("isOpen", false);

        toggleMore.setChecked(isOpen);
    }

    private void initTitle() {

        baseBack.setVisibility(View.GONE);

        baseSetting.setVisibility(View.GONE);

        baseTitle.setText("更多设置");
    }

    @Override
    public int getLayoutid() {

        return R.layout.fragment_more;
    }

    @Override
    public String getChildUrl() {

        return null;
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("来自影音先锋");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://atguigu.com/");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("东京热");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=519040293,3245926342&fm=23&gp=0.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://atguigu.com/");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("尚硅谷it教育好");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("尚硅谷it教育");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://atguigu.com/");

        // 启动分享GUI
        oks.show(getActivity());
    }

}
