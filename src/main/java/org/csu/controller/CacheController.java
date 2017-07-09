package org.csu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lixiang on 2017 07 09 下午11:20.
 *
 * 缓存服务的接口
 */
@RestController
public class CacheController {

    @GetMapping("/change/product")
    public String changeProduct(Long productId) {
        //拿到一个商品的id
        //调用商品的服务

        String url  = "http://127.0.0.1:8081/getProductInfo?productId=" + productId;
        return org.csu.utils.HttpClientUtils.sendGetRequest(url);
    }
}
