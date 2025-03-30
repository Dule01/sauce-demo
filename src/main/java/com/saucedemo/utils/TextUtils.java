package com.saucedemo.utils;

import java.util.ArrayList;
import java.util.List;

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

    public static List<Float> convertToFloats(List<String> textList){
        List<Float> floatList = new ArrayList<>();

        for (String text : textList){
            float number = Float.parseFloat(text.replace("$", "").trim());
            floatList.add(number);
        }

        return floatList;
    }
}
