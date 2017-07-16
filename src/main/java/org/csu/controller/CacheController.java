package org.csu.controller;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixObservableCommand;
import org.csu.command.GetCityNameCommand;
import org.csu.command.GetProductInfoCommand;
import org.csu.command.GetProductInfosCommand;
import org.csu.domain.ProductInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;
import rx.Observer;

import java.util.concurrent.Future;

/**
 * Created by lixiang on 2017 07 09 下午11:20.
 *
 * 缓存服务的接口
 */
@RestController
public class CacheController {

    /**
     * 模拟一个消息队列的场景
     * @param productId
     * @return
     */
    @GetMapping("/change/product")
    public String changeProduct(Long productId) {
        //拿到一个商品的id
        //调用商品的服务

        String url  = "http://127.0.0.1:8081/getProductInfo?productId=" + productId;
        return org.csu.utils.HttpClientUtils.sendGetRequest(url);
    }

    /**
     * 从 nginx开始 所有的缓存失效了，直接拿商品服务的信息
     * @param productId
     * @return
     */
    @GetMapping("/getProductInfo")
    public ProductInfo getProductInfo(Long productId) {
        HystrixCommand<ProductInfo> getProductInfo = new GetProductInfoCommand(productId);

        //同步执行
        ProductInfo productInfo = getProductInfo.execute();

//        信号量调用内部逻辑，进行了逻辑控制
//        Long cityId = productInfo.getCityId();
//        GetCityNameCommand getCityNameCommand = new GetCityNameCommand(cityId);
//        String cityName = getCityNameCommand.execute();
//        productInfo.setCityName(cityName);

        //异步执行
//        Future<ProductInfo> future = getProductInfo.queue();
//        try {
//            Thread.sleep(1000);
//            System.out.println(future.get());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return productInfo;
    }

    @GetMapping("/getProductInfos")
    @ResponseBody
    public String getProductInfos(String productIds) {
        HystrixObservableCommand<ProductInfo> productInfoHystrixObservableCommand =
                new GetProductInfosCommand(productIds.split(","));
        Observable<ProductInfo> observable = productInfoHystrixObservableCommand.observe();

//        observable = productInfoHystrixObservableCommand.toObservable();

        observable.subscribe(new Observer<ProductInfo>() {
            @Override
            public void onCompleted() {
                System.out.println("获取完了所有的商品数据");
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onNext(ProductInfo productInfo) {
                System.out.println(productInfo.toString());
            }
        });
        return "success";
    }
}
