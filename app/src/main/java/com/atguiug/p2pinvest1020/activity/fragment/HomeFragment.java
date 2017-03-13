package com.atguiug.p2pinvest1020.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atguiug.p2pinvest1020.R;
import com.atguiug.p2pinvest1020.activity.bean.Index;
import com.atguiug.p2pinvest1020.activity.utils.AppNetConfig;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * Created by 张永卫on 2017/3/10.
 */

public class HomeFragment extends Fragment {

    private Index index;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       // View view = View.inflate(getActivity(),R.layout.fragment_home,null);

        View view = View.inflate(getActivity(), R.layout.fragment_home,null);


        initData();

        return view;

    }

    private void initData() {

        getDataFromNet();
    }

    /**
     * 联网请求数据
     */
    private void getDataFromNet() {

        AsyncHttpClient httpClient = new AsyncHttpClient();

        index = new Index();

        httpClient.post(AppNetConfig.INDEX,new AsyncHttpResponseHandler(){

            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
            }

            @Override
            public void onFailure(Throwable error, String content) {
                super.onFailure(error, content);
            }
        });

    }

}
