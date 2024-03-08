package top.roud.roudblogcms.common.utils;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
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
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 取值
     * @param key
     * @return
     */
    public String get(final String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 是否存在键
     * @param key
     * @return
     */
    public boolean hasKey(final String key){
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(key));
    }

    /**
     * 根据前缀正则获取所有键
     * @param prefix
     * @return
     */
    public Set<String> getKeysByPattern(final String prefix){
        Set<String> keys = this.getKeys(prefix.concat("*"));
        return keys;
    }

    private Set<String> getKeys(String pattern){
        Set<String> keys = stringRedisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            Set<String> keysTmp = new HashSet<>();
            Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match(pattern).count(1000).build());
            while (cursor.hasNext()) {
                keysTmp.add(new String(cursor.next()));
            }
            return keysTmp;
        });
        return keys;
    }

    /**
     * 设值，时间单位为秒
     * @param key
     * @param value
     * @param timeout
     * @return
     */
    public boolean set(final String key, String value, long timeout, TimeUnit timeUnit) {
        boolean result = false;
        try {
            stringRedisTemplate.opsForValue().set(key, value, timeout, timeUnit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除一个键值
     * @param key
     * @return
     */
    public boolean delete(final String key) {
        boolean result = false;
        try {
            stringRedisTemplate.delete(key);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 批量删除键值
     * @param keys
     * @return
     */
    public boolean delete(Collection<String> keys) {
        boolean result = false;
        try {
            stringRedisTemplate.delete(keys);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 自增
     * @param key
     * @param value
     * @param timeout
     * @param timeUnit
     */
    public void increment(String key, String value, long timeout, TimeUnit timeUnit){
        if(hasKey(key)){
           stringRedisTemplate.opsForValue().increment(key, Long.parseLong(value));
        }else {
            set(key, value, timeout, timeUnit);
        }
    }

    /**
     * 根据正则表达式删除所有键值
     * @param pattern
     * @return
     */
    public boolean deleteAllByPattern(final String pattern){
        Set<String> keysByPattern = getKeysByPattern(pattern);
        return delete(keysByPattern);
    }
}
