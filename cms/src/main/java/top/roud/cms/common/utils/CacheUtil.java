package top.roud.cms.common.utils;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class CacheUtil {

    public static Map<String, Integer> intConMap = new ConcurrentHashMap<>();
    public static Map<String, Boolean> booleanConMap = new ConcurrentHashMap<>();

}
