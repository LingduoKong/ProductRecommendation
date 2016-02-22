package com.LingduoKong.app;

import com.squareup.okhttp.OkHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by lingduokong on 2/21/16.
 */
public class RecommendQueryTest {

    Query recommendQuery;

    @Before
    public void setUp() throws Exception {
        recommendQuery = new RecommendationQuery(App.KEY,
                App.RECOMMENDATION_URL, new OkHttpClient());
    }

    @Test
    public void testParse() throws Exception {
        JSONObject test1 = new JSONObject();
        Assert.assertEquals(null, recommendQuery.parse(test1.toString()));
        JSONArray items = new JSONArray();
        Assert.assertEquals(null, recommendQuery.parse(items.toString()));
        for (int i = 0; i < 5; i++) {
            items.put(new JSONObject());
        }
        Assert.assertEquals(items.length(),
                recommendQuery.parse(items.toString()).getJSONArray("recommend").length());

        for (int i = 0 ; i < 10; i++) {
            items.put(new JSONObject());
        }
        Assert.assertEquals(10,
                recommendQuery.parse(items.toString()).getJSONArray("recommend").length());
    }


}
