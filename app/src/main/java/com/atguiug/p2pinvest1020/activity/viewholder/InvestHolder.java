package com.atguiug.p2pinvest1020.activity.viewholder;

import android.view.View;
import android.widget.TextView;

import com.atguiug.p2pinvest1020.R;
import com.atguiug.p2pinvest1020.activity.bean.InvestAllBean;
import com.atguiug.p2pinvest1020.activity.ui.MyProgress;
import com.atguiug.p2pinvest1020.activity.utils.UiUtils;

import butterknife.InjectView;

/**
 * Created by 张永卫on 2017/3/14.
 */

public class InvestHolder extends BaseHolder<InvestAllBean.DataBean> {


    @InjectView(R.id.p_name)
    TextView pName;
    @InjectView(R.id.p_money)
    TextView pMoney;
    @InjectView(R.id.p_yearlv)
    TextView pYearlv;
    @InjectView(R.id.p_suodingdays)
    TextView pSuodingdays;
    @InjectView(R.id.p_minzouzi)
    TextView pMinzouzi;
    @InjectView(R.id.p_minnum)
    TextView pMinnum;
    @InjectView(R.id.p_progresss)
    MyProgress pProgresss;

    @Override
    public View initView() {
        return UiUtils.getView(R.layout.adapter_invest_all);
    }

    @Override
    public void setChildData() {

        InvestAllBean.DataBean dataBean = getT();

        pName.setText(dataBean.getName());

        pMoney.setText(dataBean.getMoney());

        pYearlv.setText(dataBean.getYearRate());

        pSuodingdays.setText(dataBean.getSuodingDays());

        pMinzouzi.setText(dataBean.getMinTouMoney());

        pMinnum.setText(dataBean.getMemberNum());

        int parseInt = Integer.parseInt(dataBean.getProgress());

        pProgresss.setZyw(parseInt);


    }
}
