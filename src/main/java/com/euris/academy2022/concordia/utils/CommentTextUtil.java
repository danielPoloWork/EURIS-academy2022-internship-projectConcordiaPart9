package com.euris.academy2022.concordia.utils;

public class CommentTextUtil {

    public static String parseText(String author, String text) {
        return String.format("@%s: %s", author, text);
    }
}
