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
public class SearchQueryTest {

    Query searchQuery;

    @Before
    public void setUp() throws Exception {
        searchQuery = new SearchQuery(App.SEARCH_URL, App.KEY, new OkHttpClient());
    }

    @Test
    public void testParse() throws Exception {
        JSONObject sample = new JSONObject();
        sample.put("itemId", 15076191);
        Assert.assertEquals(null, searchQuery.parse(sample.toString()));
        JSONArray items = new JSONArray();
        sample.put("items", items);
        Assert.assertEquals(null, searchQuery.parse(sample.toString()));
        for (int i = 0; i < 10; i++) {
            JSONObject item = new JSONObject();
            item.put("itemId", i);
            items.put(item);
        }
        Assert.assertEquals(items.getJSONObject(0).getInt("itemId"), searchQuery.parse(sample.toString()).getInt("itemId"));
    }
}
