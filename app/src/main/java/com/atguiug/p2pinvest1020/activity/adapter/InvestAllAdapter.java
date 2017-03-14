package com.atguiug.p2pinvest1020.activity.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atguiug.p2pinvest1020.R;
import com.atguiug.p2pinvest1020.activity.bean.InvestAllBean;
import com.atguiug.p2pinvest1020.activity.ui.MyProgress;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 张永卫on 2017/3/14.
 */

public class InvestAllAdapter extends BaseAdapter {

    private List<InvestAllBean.DataBean> list = new ArrayList<>();

    public InvestAllAdapter(List<InvestAllBean.DataBean> list) {

        if (list != null && list.size() > 0) {

            this.list.clear();
            this.list.addAll(list);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {

            convertView = View.inflate(parent.getContext(), R.layout.adapter_invest_all, null);

            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);

        }else{

            viewHolder = (ViewHolder) convertView.getTag();
        }

        InvestAllBean.DataBean dataBean = list.get(position);

        viewHolder.pName.setText(dataBean.getName());

        viewHolder.pMoney.setText(dataBean.getMoney());

        viewHolder.pYearlv.setText(dataBean.getYearRate());

        viewHolder.pSuodingdays.setText(dataBean.getSuodingDays());

        viewHolder.pMinzouzi.setText(dataBean.getMinTouMoney());

        viewHolder.pMinnum.setText(dataBean.getMemberNum());


        return convertView;
    }

    static class ViewHolder {
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

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
