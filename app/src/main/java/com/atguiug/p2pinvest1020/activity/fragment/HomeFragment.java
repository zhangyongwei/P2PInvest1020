package com.atguiug.p2pinvest1020.activity.fragment;

import android.content.Context;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.atguiug.p2pinvest1020.R;
import com.atguiug.p2pinvest1020.activity.bean.HomeBean;
import com.atguiug.p2pinvest1020.activity.bean.Index;
import com.atguiug.p2pinvest1020.activity.ui.MyProgress;
import com.atguiug.p2pinvest1020.activity.utils.AppNetConfig;
import com.atguiug.p2pinvest1020.activity.utils.ThreadPool;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 张永卫on 2017/3/10.
 */

public class HomeFragment extends BaseFragment {

    @InjectView(R.id.base_title)
    TextView baseTitle;
    @InjectView(R.id.base_back)
    ImageView baseBack;
    @InjectView(R.id.base_setting)
    ImageView baseSetting;
    @InjectView(R.id.banner)
    Banner banner;
    @InjectView(R.id.tv_home_product)
    TextView tvHomeProduct;
    @InjectView(R.id.tv_home_yearrate)
    TextView tvHomeYearrate;
    @InjectView(R.id.home_progress)
    MyProgress homeProgress;
    private Index index;


    @Override
    public int getLayoutid() {
        return R.layout.fragment_home;
    }

    @Override
    public String getChildUrl() {
        return AppNetConfig.INDEX;
    }

    public void initListener() {

        //初始化title
        baseTitle.setText("首页");

        baseBack.setVisibility(View.INVISIBLE);

        baseSetting.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initData(String json) {

        HomeBean homeBean = JSON.parseObject(json, HomeBean.class);

        tvHomeProduct.setText(homeBean.getProInfo().getName());

        tvHomeYearrate.setText(homeBean.getProInfo().getYearRate()+"%");
        //注意：展示UI一定要判断是不是主线程
        initProgress(homeBean.getProInfo());

        initBanner(homeBean);

    }




    private void initProgress(final HomeBean.ProInfoBean proInfo) {

        ThreadPool.getInstance().getGlobalThread().execute(new Runnable() {
            @Override
            public void run() {

                int progress = Integer.parseInt(proInfo.getProgress());

                for (int i = 0; i <= progress; i++) {

                    SystemClock.sleep(20);

                    homeProgress.setProgress(i);

                }
            }
        });
    }

    private void initBanner(HomeBean homeBean) {

        //设置图片加载器
        banner.setImageLoader(new GlideImageLoder());
        //转化成url集合

        ArrayList<Object> urls = new ArrayList<>();
        for (int i = 0; i < homeBean.getImageArr().size(); i++) {

            urls.add(AppNetConfig.BASE_URL + homeBean.getImageArr().get(i).getIMAURL());
        }
        //设置图片集合
        banner.setImages(urls);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

    }

    public class GlideImageLoder extends ImageLoader {
        /**
         * 注意：
         * 1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
         * 2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
         * 传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
         * 切记不要胡乱强转！
         */
        //Picasso 加载图片简单用法
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            Picasso.with(context).load((String) path).into(imageView);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
