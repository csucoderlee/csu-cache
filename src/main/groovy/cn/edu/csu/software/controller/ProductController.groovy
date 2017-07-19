package cn.edu.csu.software.controller

import cn.edu.csu.software.domain.ProductInfo
import cn.edu.csu.software.newcommand.ProductCommand
import cn.edu.csu.software.newcommand.ProductCommandCache
import cn.edu.csu.software.newcommand.ProductCommands
import com.netflix.hystrix.HystrixObservableCommand
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import rx.Observable
import rx.Observer

/**
 * Created by lixiang on 2017 07 18 下午11:50.
 */
@RestController
class ProductController {

    //最简单的调用HystrixCommand
    @GetMapping("/command")
    Object productInfo(Long productId) {
        ProductCommand command = new ProductCommand(productId)
        return command.execute()
    }

    @GetMapping("/commands")
    Object productInfos(String productIds) {
        HystrixObservableCommand<ProductInfo> commands = new ProductCommands(productIds.split(","))
        Observable<ProductInfo> observable = commands.observe()

        observable.subscribe(new Observer<ProductInfo>() {
            @Override
            void onCompleted() {
                println("获取完了所有的商品数据");
            }

            @Override
            void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            void onNext(ProductInfo productInfo) {
                println(productInfo.toString());
            }
        })
    }

    @GetMapping("/commandcache")
    Object productInfoCache(Long productId) {
        ProductCommandCache command = new ProductCommandCache(productId)
        return command.execute()
    }

}

