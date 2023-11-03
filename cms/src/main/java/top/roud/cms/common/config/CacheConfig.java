package top.roud.cms.common.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @describe
 * @author roud
 * @version 1.0.0
 * @date 2023-11-04 1:45
 */
@Configuration
public class CacheConfig {
    @Bean(value = "caffeineCache" )
    public Cache<String, Object> caffeineCache(){
        return Caffeine.newBuilder().expireAfterWrite(60, TimeUnit.SECONDS).initialCapacity(1000).maximumSize(2000).build();
    }
}
