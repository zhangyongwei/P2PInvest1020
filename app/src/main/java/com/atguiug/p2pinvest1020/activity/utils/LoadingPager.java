package com.atguiug.p2pinvest1020.activity.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.atguiug.p2pinvest1020.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * Created by 张永卫on 2017/3/13.
 */

public abstract class LoadingPager extends FrameLayout {

    private Context context;
    private LayoutParams params;
    private View loadingView;
    private View errorView;
    private View emptyView;
    private View successView;
    private ResultState resultState;


    public LoadingPager(Context context) {
        super(context);
        this.context = context;
        init();
    }



    public LoadingPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }


    private int STATE_LOADING = 1;//加载中
    private int STATE_ERROR = 2;//加载失败
    private int STATE_SUCCESS = 3;//加载成功
    private int STATE_EMPTY = 4;//空

    private int current_state = STATE_LOADING;
    /**
     * 添加三个布局
     */
    private void init() {

        //设置全属性
        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);


        //加载布局
        if(loadingView==null) {

            loadingView = View.inflate(context, R.layout.page_loading,null);

            this.addView(loadingView,params);

        }
        if(errorView==null) {

            errorView = View.inflate(context, R.layout.page_error,null);

            this.addView(errorView,params);

        }
        if(emptyView==null) {

            emptyView = View.inflate(context, R.layout.page_empty,null);

            this.addView(emptyView,params);

        }

        if(successView==null) {

            successView = View.inflate(context,getViewId(),null);

            this.addView(successView,params);
        }

        //展示布局
        showSafeView();

    }

    /**
     * 展示布局
     */
    private void showSafeView() {
        //展示View保证在主线程
        UiUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                showView();
            }
        });

    }

    /**
     * 根据状态来展示不同的页面
     */
    private void showView() {

        //是否展示错误页面
        errorView.setVisibility(
                current_state == STATE_ERROR ? View.VISIBLE : View.INVISIBLE);

        //是否展示加载页面
        loadingView.setVisibility(
                current_state == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);

        //是否展示空页面
        emptyView.setVisibility(
                current_state == STATE_EMPTY ? View.VISIBLE : View.INVISIBLE);


        //是否展示成功页面
        successView.setVisibility(
                current_state == STATE_SUCCESS ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * 根据不同的网络状态加载相应的页面
     */
    public void loadData(){

        //加载网络
        AsyncHttpClient httpClient = new AsyncHttpClient();

        String url = getUrl();

        if(TextUtils.isEmpty(url)) {

            //如果是空的默认为不加载网络
           resultState =  ResultState.SUCCESS;//改变状态

            loadImage();

            return;

        }

        httpClient.post(url,new AsyncHttpResponseHandler(){

            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);

                if(TextUtils.isEmpty(content)) {

                    //current_state = STATE_EMPTY;
                    resultState = ResultState.EMPTY;

                    resultState.setJson("");

                }else{

                    resultState = ResultState.SUCCESS;

                    resultState.setJson(content);
                }

                loadImage();
            }

            @Override
            public void onFailure(Throwable error, String content) {
                super.onFailure(error, content);

                resultState = ResultState.ERROR;

                resultState.setJson(content);

                loadImage();
            }
        });
    }

    /**
     * 根据枚举不同的值 来设置不同的状态
     */
    private void loadImage() {

        switch (resultState){

            case ERROR:
                current_state = STATE_ERROR;
                break;
            case EMPTY:
                current_state = STATE_EMPTY;
                break;
            case SUCCESS:
                current_state = STATE_SUCCESS;
                break;
        }

        showSafeView();

        if(current_state == STATE_SUCCESS) {

            //把数据传过去
            onSuccess(resultState,successView);
        }
    }

    protected abstract void onSuccess(ResultState resultState, View successView);

    public enum ResultState{

        //相当于是三个ResultState对象

        ERROR(2),SUCCESS(3),EMPTY(4);

        private int state;

        ResultState(int state){
            this.state = state;
        }
        private String json;

        public void setJson(String json){
            this.json = json;
        }
        public String getJson(){
            return json;
        }

    }
    protected abstract String getUrl();

    protected abstract int getViewId();


}
