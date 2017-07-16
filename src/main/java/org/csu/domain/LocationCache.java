package org.csu.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lixiang on 2017 07 16 下午10:43.
 */
public class LocationCache {

    private static Map<Long, String> cityMap = new HashMap<Long, String>();

    static {
        cityMap.put(1L, "杭州");
    }

    public static String getCityName(Long cityId) {
        return cityMap.get(cityId);
    }

}
