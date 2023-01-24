package com.fpt.edu.herofundbackend.util;

import java.util.List;

public class MyCollectionUtils {

    public static <T> boolean listIsNullOrEmpty(List<T> s) {
        return s == null || s.isEmpty();
    }
}
