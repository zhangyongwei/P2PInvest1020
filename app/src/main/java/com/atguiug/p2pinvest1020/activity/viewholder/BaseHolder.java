package com.atguiug.p2pinvest1020.activity.viewholder;

import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by 张永卫on 2017/3/14.
 */

public abstract class BaseHolder<T> {

    private View rootView;

    public BaseHolder() {

        rootView  = initView();

        ButterKnife.inject(this,rootView);

        rootView.setTag(this);
    }

    public View getView(){

        return  rootView;
    }

    private T t;

    public void setData(T t){

        this.t = t;

        setChildData();
    }

    public T getT() {

        return t;
    }

    protected abstract void setChildData();

    protected abstract View initView();
}
