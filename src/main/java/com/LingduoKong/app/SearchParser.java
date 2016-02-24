package com.LingduoKong.app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lingduokong on 2/21/16.
 */
public class SearchParser {

    public static JSONObject getFirstSearchResult(String response) {

        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(response);
        } catch (JSONException e) {
            return null;
        }

        if (!jsonObject.has("items")) {
            return null;
        }

        JSONArray items = jsonObject.getJSONArray("items");

        if (items.length() == 0) {
            return null;
        }

        return items.getJSONObject(0);
    }

}
