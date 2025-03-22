package com.saucedemo.utils;

public class TextUtils {

    // Returns numbers from text, e.g. Tax: $2.40 -> returns 2.40
    public static float extractPrice(String text){
        return Float.parseFloat(text.replaceAll("[^0-9.]", ""));
    }

    public static int extractInteger(String text){
        return Integer.parseInt(text.replaceAll("[^0-9]", ""));
    }

    public static float dollarToFloat(String text){
        return Float.parseFloat(text.replace("$", ""));
    }
}
