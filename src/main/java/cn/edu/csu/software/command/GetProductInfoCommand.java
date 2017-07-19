package cn.edu.csu.software.command;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import cn.edu.csu.software.software.domain.ProductInfo;
import cn.edu.csu.software.utils.HttpClientUtils;

/**
 * Created by lixiang on 2017 07 10 上午9:04.
 */
public class GetProductInfoCommand extends HystrixCommand<ProductInfo>{

    private Long productId;

    public GetProductInfoCommand(Long productId) {
        //GetProductInfoGroup这个名称的线程池
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GetProductInfoGroup"))
            .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                .withCircuitBreakerRequestVolumeThreshold(30)
                .withCircuitBreakerErrorThresholdPercentage(40)
                .withCircuitBreakerSleepWindowInMilliseconds(3000))
        );

        this.productId = productId;
    }

    @Override
    protected ProductInfo run() throws Exception {

        if (productId.equals(-1L)) {
            throw new Exception();
        }

        String url = "http://127.0.0.1:8081/getProductInfo?productId="+ productId;
        String response = HttpClientUtils.sendGetRequest(url);

        ProductInfo productInfo = JSONObject.parseObject(response, ProductInfo.class);

//        Long cityId = productInfo.getCityId();
//        String cityName = LocationCache.getCityName(cityId);
//        productInfo.setCityName(cityName);
        return productInfo;
    }

    @Override
    protected String getCacheKey() {
        return "product_info_" + productId;
    }

    @Override
    protected ProductInfo getFallback() {
        ProductInfo productInfo= new ProductInfo();
        productInfo.setId(0L);
        productInfo.setName("降级");
        productInfo.setPrice(0.0D);
        productInfo.setPictureList("");
        productInfo.setSpecification("");
        productInfo.setService("");
        productInfo.setColor("");
        productInfo.setSize("");
        productInfo.setShopId(0L);
        productInfo.setModifiedTime("");
        productInfo.setCityId(0L);
        productInfo.setCityName("");
        return productInfo;
    }
}
