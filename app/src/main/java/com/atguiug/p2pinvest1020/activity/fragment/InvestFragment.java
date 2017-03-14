package com.atguiug.p2pinvest1020.activity.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguiug.p2pinvest1020.R;
import com.atguiug.p2pinvest1020.activity.adapter.InvestAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 张永卫on 2017/3/10.
 */

public class InvestFragment extends BaseFragment {

    @InjectView(R.id.base_title)
    TextView baseTitle;
    @InjectView(R.id.base_back)
    ImageView baseBack;
    @InjectView(R.id.base_setting)
    ImageView baseSetting;
    @InjectView(R.id.invest_vp)
    ViewPager investVp;
    @InjectView(R.id.tv_invest_all)
    TextView tvInvestAll;
    @InjectView(R.id.tv_invest_recommend)
    TextView tvInvestRecommend;
    @InjectView(R.id.tv_invest_hot)
    TextView tvInvestHot;


    protected void initListener() {

        investVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                //偏移量
            }

            @Override
            public void onPageSelected(int position) {

                selectText(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tvInvestAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                investVp.setCurrentItem(0);
            }
        });
        tvInvestRecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                investVp.setCurrentItem(1);
            }
        });
        tvInvestHot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                investVp.setCurrentItem(2);
            }
        });
    }

    private void selectText(int id) {
        //把所有的背景色还原成默认值
        hiddenTextViewState();

        switch (id){

            case 0:
                //改变当前的背景色
                tvInvestAll.setBackgroundColor(Color.BLUE);
               break;
            case 1:
                tvInvestRecommend.setBackgroundColor(Color.BLUE);
                break;
            case 2:
                tvInvestHot.setBackgroundColor(Color.BLUE);
                break;
        }

    }

    /**
     * 恢复默认状态
     */
    private void hiddenTextViewState() {

        tvInvestRecommend.setBackgroundColor(Color.WHITE);

        tvInvestHot.setBackgroundColor(Color.WHITE);

        tvInvestAll.setBackgroundColor(Color.WHITE);
    }

    @Override
    protected void initData(String json) {
        initListener();
        //设置标题
        initTitle();
        //初始化Fragment
        initFragments();
        //初始化viewpager
        initViewPager();
        //设置默认选中的tab
        initTab();

    }

    private void initTab() {
        selectText(0);
    }

    private void initViewPager() {
        investVp.setAdapter(new InvestAdapter(getChildFragmentManager(), fragments));
    }

    private void initTitle() {

        baseSetting.setVisibility(View.GONE);

        baseTitle.setText("投资");
        initFragments();
        baseBack.setVisibility(View.GONE);
    }

    private List<BaseFragment> fragments = new ArrayList<>();

    /**
     * 初始化三个fragment
     */
    private void initFragments() {

        fragments.add(new InvestAllFragment());

        fragments.add(new InvestRecommendFragment());

        fragments.add(new InvestHotFragment());

    }


    @Override
    public int getLayoutid() {

        return R.layout.fragment_invest;
    }

    @Override
    public String getChildUrl() {

        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater,container,savedInstanceState);

        ButterKnife.inject(this,view);

        return  view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
