package com.LingduoKong.app;

import com.squareup.okhttp.OkHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by lingduokong on 2/21/16.
 */
public class ReviewQuery extends Query {

    public ReviewQuery(String apiKey, String targetURL, OkHttpClient client) {
        super(apiKey, targetURL, client);
    }

    @Override
    public JSONObject parse(String response) {
        JSONObject reviewResult = new JSONObject(response);
        JSONObject result = new JSONObject();
        result.put("name", reviewResult.getString("name"));
        if (reviewResult.has("reviews")) {
            result.put("reviews", reviewResult.getJSONArray("reviews"));
        } else {
            result.put("reviews", new JSONArray());
        }
        return result;
    }
}
