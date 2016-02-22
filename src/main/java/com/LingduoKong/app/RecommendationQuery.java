package com.LingduoKong.app;

import com.squareup.okhttp.OkHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by lingduokong on 2/21/16.
 */
public class RecommendationQuery extends Query {

    public RecommendationQuery(String apiKey, String targetURL, OkHttpClient client) {
        super(apiKey, targetURL, client);
    }

    public JSONArray top10Items(String response) {

        JSONArray items;
        try {
            items = new JSONArray(response);
        } catch (JSONException e) {
            return null;
        }

        if (items.length() == 0) {
            return null;
        }

        JSONArray result = new JSONArray();

        for (int i = 0; i < 10 && i < items.length(); i++) {
            result.put(items.get(i));
        }

        return result;
    }
}
