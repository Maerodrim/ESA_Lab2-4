package com.ssau.esalab.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ConverterUtils {
    public static <T> List<T> iterableToArrayList(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
    }
}