package cn.edu.csu.software.newcommand;

import cn.edu.csu.software.domain.ProductInfo;
import cn.edu.csu.software.utils.HttpClientUtils;
import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by lixiang on 2017 07 19 下午4:22.
 */
public class ProductCommands extends HystrixObservableCommand<ProductInfo>{

    private String[] productIds;

    public ProductCommands(String[] productIds) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("productCommand")));
        this.productIds = productIds;
    }
    @Override
    protected Observable<ProductInfo> construct() {
        return Observable.create(new Observable.OnSubscribe<ProductInfo>() {
            @Override
            public void call(Subscriber<? super ProductInfo> observer) {
                try {
                    if (!observer.isUnsubscribed()) {
                        for (String productId : productIds) {
                            String url = "http://127.0.0.1:8081/getProductInfo?productId=" + productId;
                            String response = HttpClientUtils.sendGetRequest(url);
                            ProductInfo productInfo = JSONObject.parseObject(response, ProductInfo.class);
                            observer.onNext(productInfo);
                        }
                        observer.onCompleted();
                    }
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io());
    }
}
