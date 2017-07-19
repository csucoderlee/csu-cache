package cn.edu.csu.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lixiang on 2017 07 14 上午12:28.
 */
public class BrandCache {

    private static Map<Long, String> brandMap = new HashMap<Long, String>();
    static {
        brandMap.put(1L, "apple");
    }
    public static String getBrandName(Long brandId) {
        return brandMap.get(brandId);

    }
}
