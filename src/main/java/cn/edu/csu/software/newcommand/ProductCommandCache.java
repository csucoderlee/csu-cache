package cn.edu.csu.software.newcommand;

import cn.edu.csu.software.domain.ProductInfo;
import cn.edu.csu.software.utils.HttpClientUtils;
import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by lixiang on 2017 07 19 下午4:56.
 */
public class ProductCommandCache extends HystrixCommand<ProductInfo>{

    private Long productId;

    public ProductCommandCache(Long productId) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("productCommand")));
        this.productId = productId;
    }

    @Override
    protected ProductInfo run() throws Exception {
        if (productId == 13) {
            throw new Exception("this command will trigger fallback");
        }
        String url = "http://127.0.0.1:8081/getProductInfo1?productId="+ productId;
        String response = HttpClientUtils.sendGetRequest(url);
        ProductInfo productInfo = JSONObject.parseObject(response, ProductInfo.class);
        return productInfo;
    }

    @Override
    protected String getCacheKey() {
        System.out.println(isResponseFromCache());
        return "product_info_" + productId;
    }

    @Override
    protected ProductInfo getFallback() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setId(1L);
        productInfo.setName("fallback");
        return productInfo;
    }
}
