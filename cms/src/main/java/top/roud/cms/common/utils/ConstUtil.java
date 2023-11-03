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
    public static final String REDIS_ARTICLE_KEY = "BLOG.ARTICLE.ID.";
    public static final String REDIS_ARTICLE_VIEWSNUM_KEY = "BLOG.ARTICLE.VIEWSNUM.ID.";
    public static final String REDIS_FP_KEY = "BLOG.FP";
    public static final String REDIS_FPS_KEY = "BLOG.FPS";
    public static final String REDIS_COMMENTS_KEY = "BLOG.ARTICLE.COMMENTS.ID.";
    public static final String REDIS_COMMENTS_COUNT_KEY = "BLOG.ARTICLE.COMMENTS.COUNT.ID";
    public static final String REDIS_USER_DAILYCOMMENTSCOUNT_KEY = "BLOG.ARTICLE.USER.DAILY.COMMENTS.COUNT.ID.";
    public static final String REDIS_PREVENT_VIOLENT_REQUESTS_KEY = "BLOG.PREVENT_VIOLENT_REQUESTS.";
    public static final String ARTICLE_COMMENTS_COUNT_ISNEED_UPDATE_KEY = "BLOG.ARTICLE_COMMENTS_COUNT_ISNEED_UPDATE.";
    public static final String ARTICLE_VIEWSUM_ISNEED_UPDATE_KEY = "BLOG.ARTICLE_COMMENTS_VIEWSUM_ISNEED_UPDATE.";
    public static final String CACHE_ARTCLE_FINDPAGE_PRE = "CACHE.ARTCLE.FINDPAGE.PRE.";
    public static final String CACHE_ARTCLE_FINDPAGES_PRE = "CACHE.ARTCLE.FINDPAGES.PRE.";
    public static final String CACHE_IMGFILE_PRE = "CACHE.IMGFILE.PRE.";
    public static String getRealId(String key, String child){
        String[] arr = StringUtils.split(key, child);
        return arr[0];
    }
}
