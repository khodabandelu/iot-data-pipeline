package com.khodabandelu.iot.publisher.util;

import java.util.Random;

public class NumberUtils {
    public static Long randomValue(long minValue, long maxValue) {
        Random random = new Random();
        return random.nextLong(minValue, maxValue);
    }
}
