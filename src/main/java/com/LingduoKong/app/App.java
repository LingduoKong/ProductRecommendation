package com.LingduoKong.app;

import com.squareup.okhttp.OkHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.*;

public class App {

    final static String KEY = "vrwsuvtrns8zqzzrfgcd9hue";
    final static String SEARCH_URL = "http://api.walmartlabs.com/v1/search?";
    final static String RECOMMENDATION_URL = "http://api.walmartlabs.com/v1/nbp?";
    final static String REVIEW_URL = "http://api.walmartlabs.com/v1/reviews/";

    OkHttpClient httpClient;

    /**
     * Entrance of the application.
     *
     */
    public App() {
        httpClient = new OkHttpClient();
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
        searchParams.put("apiKey", KEY);

        String url = URLGenerator.generaeteUrl(SEARCH_URL,searchParams);

        try {
            return SearchParser.getFirstSearchResult(Query.query(url, httpClient));
        } catch (IOException e) {
            System.out.println("No related item!");
        }
        return null;
    }

    /**
     * get top 10 recommended items according to the ID of a product
     * @param productID is the id of the item
     * @return an array of ids with length no more than 10
     */
    public long[] getRecommendationItemIds(long productID) {

        HashMap<String, String> recommendationParams = new HashMap<>();
        recommendationParams.put("itemId",String.valueOf(productID));
        recommendationParams.put("apiKey", KEY);
        String url = URLGenerator.generaeteUrl(RECOMMENDATION_URL, recommendationParams);

        try {
            String result = Query.query(url, httpClient);
            JSONArray items = RecommendationParser.top10Items(result);

            if (items == null) {
                return null;
            }

            long[] ids = new long[items.length()];
            for (int i = 0; i < items.length(); i++) {
                ids[i] = items.getJSONObject(i).getLong("itemId");
            }
            return ids;
        } catch (IOException e) {
            System.out.println("No recommendation for this item!");
        }
        return null;
    }

    /**
     * get review stats of a product
     * @param id is the id of the item
     * @param reviewClient is a http client, open for multithreaded implementation
     * @return a JSON object with all review details of the item
     */
    private JSONObject getReviewStats(long id, OkHttpClient reviewClient) {
        HashMap<String, String> reviewParams = new HashMap<>();
        reviewParams.put("format", "json");
        reviewParams.put("apiKey", KEY);

        String url = URLGenerator.generaeteUrl(REVIEW_URL + id + "?", reviewParams);

        try {
            String result = Query.query(url, httpClient);
            return ReviewParser.getReviewStats(result);

        } catch (IOException e) {
            System.out.println("No review.");
        }
        return null;
    }

    /**
     * rank items by their review stats
     * if no review, the score will be marked as -1 and last recommended
     * @param ids is an array with all items' id we want to rank
     * @return a list of json objects has been ranked by average score
     */
    public List<JSONObject> rankItemsByReviews(long[] ids) {

        ArrayList<JSONObject> items = new ArrayList<>();

        RequestController controller = new RequestController();
        for (long id : ids) {
            HashMap<String, String> params = new HashMap<>();
            params.put("format", "json");
            params.put("apiKey", KEY);

            String baseUrl = REVIEW_URL + id + "?";
            String fullUrl = URLGenerator.generaeteUrl(baseUrl, params);
            controller.addTask(fullUrl);
        }
        List<String> list = controller.getData();
        for (String obj : list) {
            JSONObject object = ReviewParser.getReviewStats(obj);
            items.add(object);
        }

//        for (long id : ids) {
//            JSONObject reviewObject = getReviewStats(id, httpClient);
//            if (reviewObject == null) {
//                continue;
//            }
//            items.add(reviewObject);
//        }

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

        System.out.println("Recommendation Result:");

        for (JSONObject item : items) {
            System.out.print(item.getString("name"));
            System.out.print(" Score : " + item.getDouble("averageOverallRating"));
            System.out.println(" Total : " + item.getInt("totalReviewCount"));
        }

        return items;
    }
}
