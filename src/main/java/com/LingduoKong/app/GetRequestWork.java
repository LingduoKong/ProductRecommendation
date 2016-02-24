package com.LingduoKong.app;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.concurrent.Callable;

/**
 * Created by lingduokong on 2/23/16.
 */
public class GetRequestWork implements Callable<String> {

    final private String URL;

    public GetRequestWork(String url) {
        this.URL = url;
    }

    public String getURL() {
        return URL;
    }

    @Override
    public String call() throws Exception {
        Request request = new Request.Builder().url(getURL()).build();
        Response response = new OkHttpClient().newCall(request).execute();
        return response.body().string();
    }
}
