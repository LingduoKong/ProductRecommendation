package com.LingduoKong.app;

import com.squareup.okhttp.OkHttpClient;
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

        JSONObject result;
        if (reviewResult.has("reviewStatistics")) {
            result = reviewResult.getJSONObject("reviewStatistics");
        } else {
            result = new JSONObject();
            result.put("totalReviewCount", 0);
            result.put("averageOverallRating", -1.0);
        }
        result.put("name", reviewResult.getString("name"));
        return result;
    }
}
