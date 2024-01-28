package top.roud.roudblogcms.common.utils;

import com.alibaba.fastjson.JSON;
import com.github.benmanes.caffeine.cache.Cache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author roud
 * @version 1.0.0
 * @describe
 * @date 2023-11-11 0:06
 */
@Component
public class CacheUtil {
    @Autowired
    private Cache<String, String> stringCaffeineCache;
    @Autowired
    private RedisUtil redisUtil;

    public void putToCache(String key, Object o){
        if(String.class == o.getClass()){
            stringCaffeineCache.put(key, (String) o);
            redisUtil.set(key, (String) o, 60, TimeUnit.SECONDS);
        }else{
            stringCaffeineCache.put(key, JSON.toJSONString(o));
            redisUtil.set(key, JSON.toJSONString(o), 60, TimeUnit.SECONDS);
        }
    }

    public void putToCache(String key, Object val, long t, TimeUnit timeUnit){
        if(String.class == val.getClass()){
            stringCaffeineCache.put(key, (String) val);
            redisUtil.set(key, (String) val, t, timeUnit);
        }else {
            stringCaffeineCache.put(key, JSON.toJSONString(val));
            redisUtil.set(key, JSON.toJSONString(val), t, timeUnit);
        }
    }

    public void delCache(String key){
        stringCaffeineCache.invalidate(key);
        redisUtil.delete(key);
    }

    public boolean delAllCacheByPattern(String pattern){
        boolean result = false;
        if(!StringUtils.endsWith(pattern, "*")){
            return result;
        }
        try {
            Set<String> keys = redisUtil.getKeysByPattern(pattern);
            redisUtil.delete(keys);
            stringCaffeineCache.invalidateAll(keys);
            result =  true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getByCache(String key){
        try{
            String res = stringCaffeineCache.asMap().get(key);
            if(StringUtils.isBlank(res)){
                res = redisUtil.get(key);
            }
            if(StringUtils.isNotBlank(res)){
                stringCaffeineCache.put(key, res);
            }
            return res;
        }catch (NullPointerException e){
            return "";
        }
    }

    public boolean hasKey(String key){
        try{
            boolean b1 = stringCaffeineCache.asMap().containsKey(key);
            if(b1){
                return true;
            }
            boolean b2 = redisUtil.hasKey(key);
            return b2;
        }catch (NullPointerException e){
            return false;
        }
    }

}
