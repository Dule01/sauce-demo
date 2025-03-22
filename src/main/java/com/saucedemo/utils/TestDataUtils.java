package com.saucedemo.utils;

import java.util.Random;

public class TestDataUtils {
    private static final Random random = new Random();

    public static int getRandomNumber(int min, int max){
        return random.nextInt((max - min) + 1) + min;
    }
}
