package org.csu.command;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.csu.domain.LocationCache;
import org.csu.domain.ProductInfo;
import org.csu.utils.HttpClientUtils;

/**
 * Created by lixiang on 2017 07 10 上午9:04.
 */
public class GetProductInfoCommand extends HystrixCommand<ProductInfo>{

    private Long productId;

    public GetProductInfoCommand(Long productId) {
        //GetProductInfoGroup这个名称的线程池
        super(HystrixCommandGroupKey.Factory.asKey("GetProductInfoGroup"));
        this.productId = productId;
    }

    @Override
    protected ProductInfo run() throws Exception {
        String url = "http://127.0.0.1:8081/getProductInfo?productId="+ productId;
        String response = HttpClientUtils.sendGetRequest(url);

        ProductInfo productInfo = JSONObject.parseObject(response, ProductInfo.class);

//        Long cityId = productInfo.getCityId();
//        String cityName = LocationCache.getCityName(cityId);
//        productInfo.setCityName(cityName);
        return productInfo;
    }
}