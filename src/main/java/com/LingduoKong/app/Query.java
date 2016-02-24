package com.LingduoKong.app;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONObject;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lingduokong on 2/21/16.
 */
public class Query {

    public static String query(String url, OkHttpClient client) throws IOException {

        Request request = new Request.Builder().url(url).build();

        Response response = client.newCall(request).execute();

        return response.body().string();
    }

}
