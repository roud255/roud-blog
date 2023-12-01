package top.roud.cms.common.utils;

import com.alibaba.fastjson.JSON;
import com.github.benmanes.caffeine.cache.Cache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author roud
 * @version 1.0.0
 * @describe
 * @date 2023-11-04 1:57
 */
@Component
public class ThreeCacheUtil {
    @Autowired
    private Cache<String, Object> caffeineCache;
    @Autowired
    private RedisUtil redisUtil;

    public void putToCache(String key, Object o){
        caffeineCache.put(key, JSON.toJSONString(o));
        redisUtil.set(key, JSON.toJSONString(o), 60);
    }

    public void putToCache(String key, String val, int t){
        caffeineCache.put(key, val);
        redisUtil.set(key, val, 60L *60*24*t);
    }

    public String getByCache(String key){
        String res = (String) caffeineCache.asMap().get(key);
        if(StringUtils.isBlank(res)){
            res = (String)redisUtil.get(key);
        }
        if(StringUtils.isNotBlank(res)){
            caffeineCache.put(key, res);
        }
        return res;
    }

    public void delCache(String key){
        caffeineCache.invalidate(key);
        redisUtil.delete(key);
    }
    public void putStringToCache(String key, String o){
        caffeineCache.put(key, o);
        redisUtil.set(key, o, 60);
    }

}
