package cn.edu.csu.software

import cn.edu.csu.software.collapser.HelloWorldHystrixCollapser
import com.netflix.hystrix.HystrixEventType
import com.netflix.hystrix.HystrixInvokableInfo
import com.netflix.hystrix.HystrixRequestLog
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext
import org.junit.Test

import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

/**
 * Created by lixiang on 2017 07 18 上午12:40.
 */
class HystrixCommand4RequestCollapsingTest {
    @Test
    void testCollapser() throws Exception {
        HystrixRequestContext context = HystrixRequestContext.initializeContext()
        try {

            Future<String> f1 = new HelloWorldHystrixCollapser(1).queue()
            Future<String> f2 = new HelloWorldHystrixCollapser(2).queue()
//               println(new HelloWorldHystrixCollapser(1).execute())	// 这条很可能会合并到f1和f2的批量请求中
//               println(new HelloWorldHystrixCollapser(1).execute())	// 由于上面有IO打印，这条很可能不会合并到f1和f2的批量请求中
            Future<String> f3 = new HelloWorldHystrixCollapser(3).queue()
            Future<String> f4 = new HelloWorldHystrixCollapser(4).queue()

            Future<String> f5 = new HelloWorldHystrixCollapser(5).queue()
            // f5和f6，如果sleep时间够小则会合并，如果sleep时间够大则不会合并，默认10ms
            TimeUnit.MILLISECONDS.sleep(13)
            Future<String> f6 = new HelloWorldHystrixCollapser(6).queue()

           println(f1.get())
           println(f2.get())
           println(f3.get())
           println(f4.get())
           println(f5.get())
           println(f6.get())
            // 下面3条都不在一个批量请求中
//               println(new HelloWorldHystrixCollapser(7).execute())
//               println(new HelloWorldHystrixCollapser(8).queue().get())
//               println(new HelloWorldHystrixCollapser(9).queue().get())

            // note：numExecuted表示共有几个命令执行，1个批量多命令请求算一个，这个实际值可能比代码写的要多，因为due to non-determinism of scheduler since this example uses the real timer
            int numExecuted = HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().size()
           println("num executed: " + numExecuted)
            int numLogs = 0
            for (HystrixInvokableInfo<?> command : HystrixRequestLog.getCurrentRequest().getAllExecutedCommands()) {
                numLogs++

                // assert the command is the one we're expecting
//                    assertEquals("CollepsingKey", command.getCommandKey().name())

                System.err.println(command.getCommandKey().name() + " => command.getExecutionEvents(): " + command.getExecutionEvents())

                // confirm that it was a COLLAPSED command execution
//                    assertTrue(command.getExecutionEvents().contains(HystrixEventType.COLLAPSED))
                assertTrue(command.getExecutionEvents().contains(HystrixEventType.SUCCESS))
            }
            assertEquals(numExecuted, numLogs)
        } finally {
            context.shutdown()
        }
    }
}

