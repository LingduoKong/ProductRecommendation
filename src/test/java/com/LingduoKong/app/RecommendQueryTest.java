package com.LingduoKong.app;

import com.squareup.okhttp.OkHttpClient;
import junit.framework.TestCase;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by lingduokong on 2/21/16.
 */
public class RecommendQueryTest extends TestCase {

    RecommendationQuery recommendQuery;

    @Before
    public void setUp() throws Exception {
        recommendQuery = new RecommendationQuery(App.KEY,
                App.RECOMMENDATION_URL, new OkHttpClient());
    }

    @Test
    public void testParse() throws Exception {
        JSONArray test1 = new JSONArray();
        Assert.assertEquals(null, recommendQuery.top10Items(test1.toString()));
        JSONArray items = new JSONArray();
        Assert.assertEquals(null, recommendQuery.top10Items(items.toString()));
        for (int i = 0; i < 5; i++) {
            items.put(new JSONObject());
        }
        Assert.assertEquals(items.length(),
                recommendQuery.top10Items(items.toString()).length());

        for (int i = 0 ; i < 10; i++) {
            items.put(new JSONObject());
        }
        Assert.assertEquals(10,
                recommendQuery.top10Items(items.toString()).length());
    }


}
