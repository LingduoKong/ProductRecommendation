package com.LingduoKong.app;

import com.squareup.okhttp.OkHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by lingduokong on 2/21/16.
 */
public class RecommendationQuery extends Query {

    public RecommendationQuery(String apiKey, String targetURL, OkHttpClient client) {
        super(apiKey, targetURL, client);
    }

    @Override
    public JSONObject parse(String response) {

        JSONArray items = new JSONArray(response);

        JSONArray result = new JSONArray();

        for (int i = 0; i < 10 && i < items.length(); i++) {
            result.put(items.get(i));
        }

        JSONObject object = new JSONObject();
        object.put("recommend", result);
        return object;
    }
}
