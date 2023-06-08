package top.roud.cms.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: RedisUtil
 * @Description:
 * @Author roud
 * @Date 2022/6/15
 * @Version 1.0
 */
@Component
public class RedisUtil {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public Object get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean set(final String key, String value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean set(final String key, Integer value, long timeout) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean set(final String key, String value, long timeout) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public boolean set(final String key, String value, long timeout, TimeUnit timeUnit) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean set(final String key, Integer value, long offset, int code) {
        boolean result = false;
        if(1001!=code){
            return result;
        }
        try {
            redisTemplate.opsForValue().set(key, value, offset);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean getAndSet(final String key, String value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().getAndSet(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean delete(final String key) {
        boolean result = false;
        try {
            redisTemplate.delete(key);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
