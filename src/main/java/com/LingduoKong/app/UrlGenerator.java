package com.LingduoKong.app;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lingduokong on 2/23/16.
 */
public class URLGenerator {

    public static String generaeteUrl(String url, HashMap<String, String> params) {
        StringBuilder stringBuilder = new StringBuilder(url);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            stringBuilder.append(entry.getKey() + "=" + entry.getValue() + "&");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }
}
