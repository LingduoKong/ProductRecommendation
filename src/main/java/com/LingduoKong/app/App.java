package com.LingduoKong.app;

import com.squareup.okhttp.OkHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        App app = new App();
        JSONObject item = app.getSearchResult("ipod");
        long[] itemIds = app.getRecommendationItemIds(item.getInt("itemId"));
        long id = itemIds[0];
        System.out.println(id);
        System.out.println(app.getReviews(id));
    }

    final String KEY = "vrwsuvtrns8zqzzrfgcd9hue";
    final String SEARCH_URL = "http://api.walmartlabs.com/v1/search?";
    final String RECOMMENDATION_URL = "http://api.walmartlabs.com/v1/nbp?";
    final String REVIEW_URL = "http://api.walmartlabs.com/v1/reviews/";

    public App() {

    }

    public JSONObject getSearchResult(String itemName) {

        HashMap<String, String> searchParams = new HashMap<>();
        searchParams.put("query", itemName);
        searchParams.put("format", "json");

        Query search = new SearchQuery(KEY, SEARCH_URL, new OkHttpClient());

        JSONObject item = null;
        try {
            item = search.parse(search.query(searchParams));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("No such item!");
        }
        return item;
    }

    public long[] getRecommendationItemIds(long productID) {

        HashMap<String, String> recommendationParams = new HashMap<>();
        recommendationParams.put("itemId",String.valueOf(productID));

        Query recommendQuery = new RecommendationQuery(KEY, RECOMMENDATION_URL, new OkHttpClient());
        try {
            String result = recommendQuery.query(recommendationParams);
            JSONObject items = recommendQuery.parse(result);
            JSONArray array = items.getJSONArray("recommend");
            long[] ids = new long[array.length()];
            for (int i = 0; i < array.length(); i++) {
                ids[i] = array.getJSONObject(i).getLong("itemId");
            }
            return ids;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getReviews(long productID) {
        HashMap<String, String> reviewParams = new HashMap<>();
        reviewParams.put("format", "json");

        Query reviewQuery = new ReviewQuery(KEY, REVIEW_URL + productID + "?", new OkHttpClient());
        try {
            String result = reviewQuery.query(reviewParams);
            JSONObject parseResult = reviewQuery.parse(result);
            parseResult.put("itemId", productID);
            return parseResult;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONArray rankItems() {

        return null;
    }
}
