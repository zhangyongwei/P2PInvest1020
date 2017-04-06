package com.atguiug.p2pinvest1020.activity.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atguiug.p2pinvest1020.R;
import com.atguiug.p2pinvest1020.activity.avtivity.ColumnActivity;
import com.atguiug.p2pinvest1020.activity.avtivity.ImageSettingActivity;
import com.atguiug.p2pinvest1020.activity.avtivity.LineChartActivity;
import com.atguiug.p2pinvest1020.activity.avtivity.MainActivity;
import com.atguiug.p2pinvest1020.activity.avtivity.PieActivity;
import com.atguiug.p2pinvest1020.activity.avtivity.ReChargeActivity;
import com.atguiug.p2pinvest1020.activity.avtivity.WithDrawActivity;
import com.atguiug.p2pinvest1020.activity.bean.UserInfo;
import com.atguiug.p2pinvest1020.activity.utils.AppNetConfig;
import com.atguiug.p2pinvest1020.activity.utils.BitmapUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.InjectView;
import jp.wasabeef.picasso.transformations.BlurTransformation;
import jp.wasabeef.picasso.transformations.ColorFilterTransformation;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by 张永卫on 2017/3/10.
 */

public class PropertyFragment extends BaseFragment {


    @InjectView(R.id.tv_settings)
    TextView tvSettings;
    @InjectView(R.id.iv_me_icon)
    ImageView ivMeIcon;
    @InjectView(R.id.rl_me_icon)
    RelativeLayout rlMeIcon;
    @InjectView(R.id.tv_me_name)
    TextView tvMeName;
    @InjectView(R.id.rl_me)
    RelativeLayout rlMe;
    @InjectView(R.id.recharge)
    ImageView recharge;
    @InjectView(R.id.withdraw)
    ImageView withdraw;
    @InjectView(R.id.ll_touzi)
    TextView llTouzi;
    @InjectView(R.id.ll_touzi_zhiguan)
    TextView llTouziZhiguan;
    @InjectView(R.id.ll_zichan)
    TextView llZichan;

    @Override
    protected void initListener() {
        /**
         * 投资管理
         */
        llTouzi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), LineChartActivity.class));
            }
        });
        /**
         * 投资管理(直观)
         */
        llTouziZhiguan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), ColumnActivity.class));

            }
        });
        /**
         * 资产
         */
        llZichan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), PieActivity.class));

            }
        });
        //充值的监听
        recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), ReChargeActivity.class));
            }
        });
        //提现的监听
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), WithDrawActivity.class));
            }
        });

        //设置的监听
        tvSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), ImageSettingActivity.class));
            }
        });

    }

    @Override

    protected void initData(String json) {

        initListener();

        MainActivity activity = (MainActivity) getActivity();

        UserInfo user = activity.getUser();

        //设置用户名
        tvMeName.setText(user.getData().getName());

//        //设置头像
//        Picasso.with(getActivity())
//                .load(AppNetConfig.BASE_URL+"/images/tx.png")
//                .transform(new Transformation() {
//                    @Override
//                    public Bitmap transform(Bitmap bitmap) {
//
//                        Bitmap circleBitmap = BitmapUtils.circleBitmap(bitmap);
//
//                        bitmap.recycle();//必须把原来的释放掉
//
//                        return circleBitmap;
//                    }
//
//                    @Override
//                    public String key() {
//                        return "";//不能为空否则会报错
//                    }
//                })
//                .into(ivMeIcon);

            Picasso.with(getActivity())
                    .load(AppNetConfig.BASE_URL + "/images/tx.png")
                .transform(new CropCircleTransformation())
                .transform(new ColorFilterTransformation(
                        Color.parseColor("#66FFccFF")))
                //第二个参数值越大越模糊
                .transform(new BlurTransformation(getActivity(), 6))
                .into(ivMeIcon);
    }

    @Override
    public void onResume() {
        super.onResume();

        MainActivity activity = (MainActivity) getActivity();

        Boolean update = activity.isUpdate();

        if (update) {

            File filesDir = null;

            FileInputStream is = null;


            //输出流
            try {

                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                    filesDir = getActivity().getExternalFilesDir("");

                } else {

                    filesDir = getActivity().getFilesDir();
                }

                //全路径
                File path = new File(filesDir, "123.png");


                if (path.exists()) {

                    is = new FileInputStream(path);

                    //第一个参数是图片的格式，第二个参数是图片的质量数值大的大质量高，第三个是输出流
                    Bitmap bitmap = BitmapFactory.decodeStream(is);

                    Bitmap circleBitmap = BitmapUtils.circleBitmap(bitmap);

                    ivMeIcon.setImageBitmap(circleBitmap);

                    activity.saveImage(false);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }finally {

                try {
                    if(is!=null) {

                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public int getLayoutid() {

        return R.layout.fragment_property;
    }

    @Override
    public String getChildUrl() {

        return null;
    }

}
