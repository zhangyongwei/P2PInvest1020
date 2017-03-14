package com.atguiug.p2pinvest1020.activity.adapter;

import android.view.View;
import android.widget.TextView;

import com.atguiug.p2pinvest1020.R;
import com.atguiug.p2pinvest1020.activity.bean.InvestAllBean;
import com.atguiug.p2pinvest1020.activity.utils.UiUtils;

import java.util.List;

/**
 * Created by 张永卫on 2017/3/14.
 */

public class InvestAllAdapter2 extends BaseInvestAllAdapter02<InvestAllBean.DataBean> {


    public InvestAllAdapter2(List<InvestAllBean.DataBean> list) {
        super(list);
    }

    @Override
    public View initView() {

        return UiUtils.getView(R.layout.adapter_invest_all);
    }

    @Override
    public void setData(InvestAllBean.DataBean dataBean, View view) {

        TextView pname = (TextView) view.findViewById(R.id.p_name);

        pname.setText(dataBean.getName());
    }
}
