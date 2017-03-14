package com.atguiug.p2pinvest1020.activity.fragment;

import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.atguiug.p2pinvest1020.R;
import com.atguiug.p2pinvest1020.activity.adapter.InvestAllAdapter;
import com.atguiug.p2pinvest1020.activity.bean.InvestAllBean;
import com.atguiug.p2pinvest1020.activity.utils.AppNetConfig;

import butterknife.InjectView;

/**
 * Created by 张永卫on 2017/3/14.
 */

public class InvestAllFragment extends BaseFragment {
    @InjectView(R.id.invest_all_lv)
    ListView investAllLv;
    private InvestAllAdapter adapter;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(String json) {

        InvestAllBean investAllBean = JSON.parseObject(json, InvestAllBean.class);

        //设置适配器
        adapter = new InvestAllAdapter(investAllBean.getData());

        investAllLv.setAdapter(adapter);
    }

    @Override
    public int getLayoutid() {
        return R.layout.fragment_invest_all;
    }

    @Override
    public String getChildUrl() {

        return AppNetConfig.PRODUCT;
    }


}
