package cn.edu.csu.software.newcommand;

import cn.edu.csu.software.domain.ProductInfo;
import cn.edu.csu.software.utils.HttpClientUtils;
import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by lixiang on 2017 07 19 下午4:18.
 */
public class ProductCommand extends HystrixCommand<ProductInfo>{

    private Long productId;

    public ProductCommand(Long productId) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("productCommand")));
        this.productId = productId;
    }

    @Override
    protected ProductInfo run() throws Exception {
        String url = "http://127.0.0.1:8081/getProductInfo?productId="+ productId;
        String response = HttpClientUtils.sendGetRequest(url);
        ProductInfo productInfo = JSONObject.parseObject(response, ProductInfo.class);
        return productInfo;
    }
}
