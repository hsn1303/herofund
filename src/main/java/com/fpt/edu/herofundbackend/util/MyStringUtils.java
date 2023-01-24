package com.fpt.edu.herofundbackend.util;

public class MyStringUtils {

    public static boolean isNullOrEmptyWithTrim(String s) {
        return s == null || s.trim().isEmpty();
    }
}
