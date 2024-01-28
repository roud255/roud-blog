package top.roud.roudblogcms.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @description : TODO
 * @author: roud
 * @date: 2023/6/8
 * @version:
 */
public class ConstUtil {
    /**
     * User相关
     */
    //根据手机号（邮箱）和密码查找user
    public static final String USER_PHONENUM_PASSWORD_CONTACT = "roudblog.user.phonenum.password.contact.";
    public static final String USER_PHONENUM_CONTACT = "roudblog.user.phonenum.contact.";
    public static final String USER_FINDPAGE = "roudblog.user.findpage.";



    public static final String USER_OUTTOKEN_CONTACT = "roudblog.user.outtoken.contact.";
    public static final String USER_CACHE = "roudblog.user.";
    public static final String USERINFO_FLAG_CACHE = "roudblog.user.info.flag.";
    public static final String USER_UPLOADIMG_FLAG_CACHE = "roudblog.user.uploadimg.flag.";

    /**
     * Article相关
     */
    public static final String ARTICLE_CACHE = "roudblog.article.";
    public static final String ARTICLE_COMMENTS_CONTACT = "roudblog.article.comments.contact.";
    public static final String ARTICLE_TAG_FPWB_CONTACT = "roudblog.article.tag.fpwb.contact.";
    public static final String ARTICLE_VIEWCOUNT = "article.viewcount.";
    public static final String ARTICLE_COMMENTCOUNT = "article.commentcount.";

    /**
     * Comment相关
     */
    public static final String ARTICLE_COMMENT_CACHE = "roudblog.article.comment.";


    //用户当天已经评论标识
    public static final String USER_DAILYCOMMENTSCOUNT = "user.dailycommentscount.";
    //ip当前请求系统接口次数统计-用于防止恶意攻击
    public static final String SYS_PREVENT_VIOLENT_REQUESTS = "sys.prevent.violent.requests.";

    /**
     * captcha validate
     */
    public static final String CAPTCHA_IMAGE_CONTACT = "captcha.image.contact.";

    /**
     * email
     */
    public static final String CAPTCHA_EMAIL_CONTACT = "captcha.email.contact.";
}
