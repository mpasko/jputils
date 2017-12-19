/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.util;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author marcin
 */
public class MapUtils {

    public static Map<String, String> generateMap(Map.Entry<String, String>... entries) {
        return Arrays.stream(entries).collect(Collectors.toMap((Map.Entry<String, String> entry) -> entry.getKey(), (Map.Entry<String, String> entry) -> entry.getValue()));
    }

    public static Map.Entry<String, String> entry(String key, String value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

}
