package cn.edu.csu.software.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import cn.edu.csu.software.software.domain.BrandCache;

/**
 * Created by lixiang on 2017 07 14 上午12:25.
 */
public class GetBrandNameCommand extends HystrixCommand<String>{

    private Long brandId;

    public GetBrandNameCommand(Long brandId) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GetBrandNameGroup"))
            .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                .withFallbackIsolationSemaphoreMaxConcurrentRequests(15)));  //通过信号量 来控制最大的fallback数目
        this.brandId = brandId;
    }

    @Override
    protected String run() throws Exception {
        throw new Exception();
    }

    @Override
    protected String getFallback() {
        return BrandCache.getBrandName(brandId);
    }
}
