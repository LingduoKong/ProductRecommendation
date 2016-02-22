package com.LingduoKong.app;

import com.squareup.okhttp.OkHttpClient;
import junit.framework.TestCase;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by lingduokong on 2/21/16.
 */
public class ReviewQueryTest extends TestCase {

    @Test
    public void testParse() throws Exception {
        Assert.assertEquals(null, ReviewQuery.getReviewStats("abcde"));
        JSONObject review = new JSONObject();
        Assert.assertEquals(null, ReviewQuery.getReviewStats(review.toString()));
        review.put("name", "itemName");
        Assert.assertEquals(ReviewQuery.getReviewStats(review.toString()).toString(),
                review.put("totalReviewCount", 0).put("averageOverallRating",-1.0).toString());


        JSONObject review2 = new JSONObject();
        review2.put("name", "itemName");
        JSONObject stats = new JSONObject();
        stats.put("totalReviewCount", 100);
        stats.put("averageOverallRating", 4.45);
        review2.put("reviewStatistics", stats);

        review.put("totalReviewCount", 100);
        review.put("averageOverallRating", 4.45);
        Assert.assertEquals(review.toString(), ReviewQuery.getReviewStats(review2.toString()).toString());
    }
}
