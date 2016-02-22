package com.LingduoKong.app;

import com.squareup.okhttp.OkHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.*;

public class App 
{
//    public static void main(String[] args) {
//        App app = new App();
//        JSONObject result = app.getSearchResult("abc");
//        long id = result.getLong("itemId");
//        long[] ids = app.getRecommendationItemIds(id);
//        app.rankItemsByReviews(ids);
//    }

    final static String KEY = "vrwsuvtrns8zqzzrfgcd9hue";
    final static String SEARCH_URL = "http://api.walmartlabs.com/v1/search?";
    final static String RECOMMENDATION_URL = "http://api.walmartlabs.com/v1/nbp?";
    final static String REVIEW_URL = "http://api.walmartlabs.com/v1/reviews/";

    OkHttpClient searchClient;
    OkHttpClient recommendClient;

    /**
     * Entrance of the application.
     *
     */
    public App() {
        searchClient = new OkHttpClient();
        recommendClient = new OkHttpClient();
    }

    /**
     * get the search result of a keyword
     * @param itemName is the keyword of a product
     * @return a json object which includes all details of the item.
     *          Will be null if there isn't any match items
     */
    public JSONObject getSearchResult(String itemName) {

        HashMap<String, String> searchParams = new HashMap<>();
        searchParams.put("query", itemName);
        searchParams.put("format", "json");

        Query search = new SearchQuery(KEY, SEARCH_URL, searchClient);

        JSONObject item = null;
        try {
            item = search.parse(search.query(searchParams));
        } catch (IOException e) {
            System.out.println("No related item!");
        }
        return item;
    }

    /**
     * get top 10 recommended items according to the ID of a product
     * @param productID is the id of the item
     * @return an array of ids with length no more than 10
     */
    public long[] getRecommendationItemIds(long productID) {

        HashMap<String, String> recommendationParams = new HashMap<>();
        recommendationParams.put("itemId",String.valueOf(productID));

        Query recommendQuery = new RecommendationQuery(KEY, RECOMMENDATION_URL, recommendClient);
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
            System.out.println("No recommendation for this item!");
        }
        return null;
    }

    /**
     * get review stats of a product
     * @param productID is the id of the item
     * @param reviewClient is a http client, open for multithreaded implementation
     * @return a JSON object with all review details of the item
     */
    private JSONObject getReviews(long productID, OkHttpClient reviewClient) {
        HashMap<String, String> reviewParams = new HashMap<>();
        reviewParams.put("format", "json");

        Query reviewQuery = new ReviewQuery(KEY, REVIEW_URL + productID + "?", reviewClient);
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

    public List<JSONObject> rankItemsByReviews(long[] ids) {

        ArrayList<JSONObject> items = new ArrayList<>();

        for (long id : ids) {
            JSONObject reviewObject = getReviews(id, new OkHttpClient());
            items.add(reviewObject);
        }

        Collections.sort(items, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                double one = o1.getDouble("averageOverallRating");
                double two = o2.getDouble("averageOverallRating");
                if (one > two) {
                    return -1;
                } else if (one < two) {
                    return 1;
                } else {
                    int total1 = o1.getInt("totalReviewCount");
                    int total2 = o2.getInt("totalReviewCount");
                    if (total1 > total2) {
                        return -1;
                    } else if (total1 < total2) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            }
        });

        for (JSONObject item : items) {
            System.out.print(item.getString("name"));
            System.out.print(" Score : " + item.getDouble("averageOverallRating"));
            System.out.println(" Total : " + item.getInt("totalReviewCount"));
        }

        return items;
    }

}
