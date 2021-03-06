package com.LingduoKong.app;

import com.squareup.okhttp.OkHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lingduokong on 2/21/16.
 */
public class ReviewParser {

    public static JSONObject getReviewStats(String response) {
        JSONObject reviewResult;

        try {
            reviewResult = new JSONObject(response);
        } catch (JSONException e) {
            return null;
        }

        if (!reviewResult.has("name")) {
            return null;
        }

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
