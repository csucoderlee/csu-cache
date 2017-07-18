package org.csu.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;
import org.csu.domain.Category;

/**
 * Created by lixiang on 2017 07 16 上午12:00.
 */
public class FallbackCommand extends HystrixCommand<Category>{

    public FallbackCommand() {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("FallbackService"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("FallbackCommand"))
                    .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("FallbackPool")));
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
