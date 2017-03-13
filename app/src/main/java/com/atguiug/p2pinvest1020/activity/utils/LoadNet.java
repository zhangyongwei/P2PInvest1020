package com.atguiug.p2pinvest1020.activity.utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * Created by 张永卫on 2017/3/13.
 */

public class LoadNet {

    public static void getDataPost(String url, final LoadNetHttp http){

        AsyncHttpClient httpClient = new AsyncHttpClient();

        httpClient.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);

                if (http != null) {
                    http.success(content);
                }
            }
            @Override
            public void onFailure(Throwable error, String content) {
                super.onFailure(error, content);
                if (http != null) {
                    http.failure(content);
                }

            }
        });
    }
}
