package com.atguiug.p2pinvest1020.activity.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 张永卫on 2017/3/14.
 */

public abstract class BaseInvestAllAdapter02<T> extends BaseAdapter {


    public List<T> list = new ArrayList<>();

    public BaseInvestAllAdapter02(List<T> list) {

        if(list!=null && list.size()>0) {

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
        if(convertView==null) {

            convertView = initView();//由子类实现view

            viewHolder = new ViewHolder(convertView);

        }else{

            viewHolder = (ViewHolder) convertView.getTag();
        }

        T t = list.get(position);
        /**
         * 第二层抽出
         * viewholder为了减少findviewbyId
         */

        setData(t,convertView);

        return convertView;
    }


    class ViewHolder{

        ViewHolder(View view){

            view.setTag(this);
        }
    }

    public abstract View initView();

    public abstract void setData(T t, View view);


}
