package cn.edu.csu.software;

import cn.edu.csu.software.domain.ProductInfo;
import cn.edu.csu.software.newcommand.ProductCommandCache;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by lixiang on 2017 07 19 下午5:12.
 */
public class CacheTest {

    @Test
    public void testWithCacheHits() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            ProductCommandCache command2a = new ProductCommandCache(1L);
            ProductCommandCache command2b = new ProductCommandCache(1L);

            command2a.execute();
            // this is the first time we've executed this command with
            // the value of "2" so it should not be from cache
            assertFalse(command2a.isResponseFromCache());

            command2b.execute();
            // this is the second time we've executed this command with
            // the same value so it should return from cache
            assertTrue(command2b.isResponseFromCache());
        } finally {
            context.shutdown();
        }

        // start a new request context
        context = HystrixRequestContext.initializeContext();
        try {
            ProductCommandCache command3b = new ProductCommandCache(13L);
            ProductInfo productInfo = command3b.execute();
            // this is a new request context so this
            // should not come from cache
            System.out.println("获得的商品名字为 fallback降级策略启动的名字"  + productInfo.getName());
//            assertTrue(productInfo.getName().equals("fallback"));
        } finally {
            context.shutdown();
        }
    }
}
