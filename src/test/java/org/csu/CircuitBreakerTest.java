package org.csu;

import org.csu.utils.HttpClientUtils;

/**
 * Created by lixiang on 2017 07 17 上午00:43.
 */
public class CircuitBreakerTest {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            if (i < 15) {
                String response = HttpClientUtils.sendGetRequest("http://localhost:8081/getProductInfo?productId=1");
            } else if (i>= 15 && i<30) {
                String response = HttpClientUtils.sendGetRequest("http://localhost:8081/getProductInfo?productId=1");
            }
        }
    }
}
