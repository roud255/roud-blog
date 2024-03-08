package top.roud.roudblogcms.common.utils;

import cn.hutool.core.collection.ConcurrentHashSet;

/**
 * @author roud
 * @version 1.0.0
 * @describe
 * @date 2024-01-28 2:59
 */
public class ScheduledTaskCollection {
    public static ConcurrentHashSet<String> viewCountSet = new ConcurrentHashSet<>();
    public static ConcurrentHashSet<String> commentCountSet = new ConcurrentHashSet<>();
}
