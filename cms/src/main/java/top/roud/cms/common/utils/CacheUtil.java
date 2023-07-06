package top.roud.cms.common.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheUtil {
    public static Map<String, Integer> intConMap = new ConcurrentHashMap<>();
    public static Map<String, Boolean> booleanConMap = new ConcurrentHashMap<>();
}
