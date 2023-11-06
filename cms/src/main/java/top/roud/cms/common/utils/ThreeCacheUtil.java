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

    public void putToThreeCache(String key, Object o){
        caffeineCache.put(key, JSON.toJSONString(o));
        redisUtil.set(key, JSON.toJSONString(o), 60);
    }

    public String getByThreeCache(String key){
        String res = (String) caffeineCache.asMap().get(key);
        if(StringUtils.isBlank(res)){
            res = (String)redisUtil.get(key);
        }
        return res;
    }

    public void putStringToThreeCache(String key, String o){
        caffeineCache.put(key, o);
        redisUtil.set(key, o, 60);
    }

}
