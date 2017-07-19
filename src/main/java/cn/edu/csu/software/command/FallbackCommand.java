package cn.edu.csu.software.command;

import com.netflix.hystrix.*;
import cn.edu.csu.software.software.domain.Category;

/**
 * Created by lixiang on 2017 07 16 上午12:00.
 */
public class FallbackCommand extends HystrixCommand<Category>{

    public FallbackCommand() {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("FallbackService"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("FallbackCommand"))
                    .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("FallbackPool"))
                    .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                            .withFallbackIsolationSemaphoreMaxConcurrentRequests(15))); //fallback也是基于信号量来进行资源隔离的
    }

    @Override
    protected Category run() throws Exception {
        throw new Exception();
    }

    @Override
    protected Category getFallback() {
        Category category = new Category();
        category.setId(1L);
        category.setName("降级默认分类");
        return super.getFallback();
    }
}
