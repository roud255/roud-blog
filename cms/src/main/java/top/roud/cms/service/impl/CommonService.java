package top.roud.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.roud.cms.common.utils.CacheUtil;
import top.roud.cms.common.utils.ConstUtil;
import top.roud.cms.common.utils.StaticVarUtil;
import top.roud.cms.common.utils.ThreeCacheUtil;
import top.roud.cms.entity.Comment;
import top.roud.cms.service.ArticleAndCommentService;

import java.util.List;

/**
 * @author roud
 * @version 1.0.0
 * @describe
 * @date 2023-12-03 4:28
 */
@Component
public class CommonService {
    @Autowired
    private ThreeCacheUtil threeCacheUtil;
    @Autowired
    private ArticleAndCommentService articleAndCommentService;

    public void updateCommentCache(Long articleIdById){
        String key = ConstUtil.REDIS_COMMENTS_KEY+articleIdById;
        String commentCountNeedUpdateKey = ConstUtil.ARTICLE_COMMENTS_COUNT_ISNEED_UPDATE_KEY+articleIdById;
        String countKey = ConstUtil.REDIS_COMMENTS_COUNT_KEY+articleIdById;
        List<Comment> commentsByArticle = articleAndCommentService.findCommentByArticle(articleIdById);
        threeCacheUtil.putToCache(key, commentsByArticle);
        CacheUtil.intConMap.put(countKey,commentsByArticle.size());
        StaticVarUtil.updateViewsnumAndCommentsnumFlag.set(true);
        CacheUtil.booleanConMap.put(commentCountNeedUpdateKey, true);
    }

}
