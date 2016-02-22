package com.LingduoKong.app;

import org.json.JSONObject;

import java.util.Scanner;

/**
 * Created by lingduokong on 2/21/16.
 */
public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String keyWord = null;
        while (keyWord == null || keyWord.length() == 0) {
            System.out.println("Please enter item you want to search:");
            keyWord = scanner.nextLine();
        }
        System.out.println("Searching results, please wait...\n\n");

        App app = new App();
        JSONObject result = app.getSearchResult(keyWord);
        long id = result.getLong("itemId");
        long[] ids = app.getRecommendationItemIds(id);
        app.rankItemsByReviews(ids);
    }

}
