package top.roud.roudblogcms.common.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description : TODO
 * @author: roud
 * @date: 2023/9/6
 * @version:
 */
@Configuration
@EnableAsync
@Component
public class TaskExecutePoolUtil {
    @Bean
    public Executor myTaskAsyncPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程池大小
        executor.setCorePoolSize(5);
        //最大线程数
        executor.setMaxPoolSize(10);
        //队列容量
        executor.setQueueCapacity(30);
        //活跃时间
        executor.setKeepAliveSeconds(300);
        //线程名字前缀
        executor.setThreadNamePrefix("roudblogExecutor-");

        // setRejectedExecutionHandler：当pool已经达到max size的时候，如何处理新任务
        // CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}