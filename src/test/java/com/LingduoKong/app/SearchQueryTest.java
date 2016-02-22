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
public class SearchQueryTest extends TestCase {

    @Test
    public void test() throws Exception {
        JSONObject sample = new JSONObject();
        sample.put("itemId", 15076191);
        Assert.assertEquals(null, SearchQuery.getFirstSearchResult(sample.toString()));
        JSONArray items = new JSONArray();
        sample.put("items", items);
        Assert.assertEquals(null, SearchQuery.getFirstSearchResult(sample.toString()));
        for (int i = 0; i < 10; i++) {
            JSONObject item = new JSONObject();
            item.put("itemId", i);
            items.put(item);
        }
        Assert.assertEquals(items.getJSONObject(0).getInt("itemId"),
                SearchQuery.getFirstSearchResult(sample.toString()).getInt("itemId"));
    }
}
