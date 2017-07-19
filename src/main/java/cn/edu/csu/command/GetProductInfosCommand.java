package cn.edu.csu.command;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import cn.edu.csu.domain.ProductInfo;
import cn.edu.csu.utils.HttpClientUtils;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by lixiang on 2017 07 16 下午9:45.
 */
public class GetProductInfosCommand extends HystrixObservableCommand<ProductInfo> {

    private String[] productIds;

    public GetProductInfosCommand(String[] productIds) {
        super(HystrixCommandGroupKey.Factory.asKey("GetProductInfoGroup"));
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
