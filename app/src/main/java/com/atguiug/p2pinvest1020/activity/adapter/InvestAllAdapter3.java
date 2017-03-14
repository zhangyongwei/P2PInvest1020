package com.atguiug.p2pinvest1020.activity.adapter;

import com.atguiug.p2pinvest1020.activity.bean.InvestAllBean;
import com.atguiug.p2pinvest1020.activity.viewholder.BaseHolder;
import com.atguiug.p2pinvest1020.activity.viewholder.InvestHolder;

import java.util.List;

/**
 * Created by 张永卫on 2017/3/14.
 */

public class InvestAllAdapter3 extends BaseInvestAllAdapter03<InvestAllBean.DataBean> {


    public InvestAllAdapter3(List<InvestAllBean.DataBean> list) {
        super(list);
    }

    @Override
    public BaseHolder getHolder() {

        return new InvestHolder();
    }
}
