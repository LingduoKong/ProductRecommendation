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

    final String apiKey;
    final String targetURL;
    private OkHttpClient client;

    public Query(String apiKey, String targetURL, OkHttpClient client) {
        this.apiKey = apiKey;
        this.targetURL = targetURL;
        this.client = client;
    }

    public String query(HashMap<String, String> urlParameters) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(targetURL);

        for (Map.Entry<String, String> entry : urlParameters.entrySet()) {
            stringBuilder.append(entry.getKey() + "=" + entry.getValue() + "&");
        }
        stringBuilder.append("apiKey=" + apiKey);

        String url = stringBuilder.toString();

        Request request = new Request.Builder().url(url).build();

        Response response = client.newCall(request).execute();

        return response.body().string();
    }

}
