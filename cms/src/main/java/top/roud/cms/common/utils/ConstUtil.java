package top.roud.cms.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @description : TODO
 * @author: roud
 * @date: 2023/6/8
 * @version:
 */
public class ConstUtil {
    public static final String REDIS_ARTICLE_KEY = "roudblog.article.id.";
    public static final String REDIS_ARTICLE_VIEWSNUM_KEY = "roudblog.article.viewsnum.id.";
    public static final String REDIS_FP_KEY = "roudblog.fp";
    public static final String REDIS_FPS_KEY = "roudblog.fps";
    public static final String REDIS_COMMENTS_KEY = "roudblog.article.comments.id.";
    public static final String REDIS_COMMENTS_COUNT_KEY = "roudblog.article.comments.count.id";
    public static final String REDIS_USER_DAILYCOMMENTSCOUNT_KEY = "roudblog.article.user.daily.comments.count.id.";
    public static final String REDIS_PREVENT_VIOLENT_REQUESTS_KEY = "roudblog.prevent_violent_requests.";
    public static final String ARTICLE_COMMENTS_COUNT_ISNEED_UPDATE_KEY = "roudblog.article_comments_count_isneed_update.";
    public static final String ARTICLE_VIEWSUM_ISNEED_UPDATE_KEY = "roudblog.article_comments_viewsum_isneed_update.";
    public static final String CACHE_ARTCLE_FINDPAGE_PRE = "roudblog.cache.artcle.findpage.pre.";
    public static final String CACHE_ARTCLE_FINDPAGES_PRE = "roudblog.cache.artcle.findpages.pre.";
    public static final String CACHE_IMGFILE_PRE = "roudblog.cache.imgfile.pre.";
    public static final String CACHE_USERINFO_PRE = "roudblog.cache.userinfo.pre.";
    public static String getRealId(String key, String child){
        String[] arr = StringUtils.split(key, child);
        return arr[0];
    }
}
