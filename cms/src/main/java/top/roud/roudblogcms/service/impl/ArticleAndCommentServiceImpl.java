package top.roud.roudblogcms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.roud.roudblogcms.common.result.Result;
import top.roud.roudblogcms.common.utils.*;
import top.roud.roudblogcms.entity.Comment;
import top.roud.roudblogcms.mapper.ArticleAndCommentMapper;
import top.roud.roudblogcms.mapper.ArticleAndTagMapper;
import top.roud.roudblogcms.service.ArticleAndCommentService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static top.roud.roudblogcms.common.result.ResultCode.*;
import static top.roud.roudblogcms.common.utils.ConstUtil.*;

/**
 * @description : TODO
 * @author: roud
 * @date: 2023/5/4
 * @version:
 */
@Transactional
@Service
public class ArticleAndCommentServiceImpl implements ArticleAndCommentService {
    @Autowired
    private ArticleAndCommentMapper articleAndCommentMapper;
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private IPUtil ipUtil;
    @Autowired
    private CacheUtil cacheUtil;

    @Autowired
    private TaskExecutePoolUtil taskExecutePoolUtil;

    @Autowired
    private RedisUtil redisUtil;
    @Override
    public Integer addComment(Comment comment) {
        /*删除缓存*/
        taskExecutePoolUtil.myTaskAsyncPool().execute(()->{
            cacheUtil.delAllCacheByPattern(ARTICLE_COMMENT_CACHE + comment.getArticleId() + "*");
            cacheUtil.delAllCacheByPattern(ARTICLE_COMMENTCOUNT + comment.getArticleId() + "*");
        });

        return articleAndCommentMapper.insertComment(comment);
    }

    @Override
    public Page<Comment> findAllComments(Integer pageNum, Integer pageSize, String search) {
        LambdaQueryWrapper<Comment> wrapper = Wrappers.<Comment>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(Comment::getContent, search);
        }
        Page<Comment> commentPage = articleAndCommentMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return commentPage;
    }

    @Override
    public Integer delById(Long articleId, Long id) {
        /*删除缓存*/
        taskExecutePoolUtil.myTaskAsyncPool().execute(()->{
            cacheUtil.delAllCacheByPattern(ARTICLE_COMMENT_CACHE + articleId + "*");
            cacheUtil.delAllCacheByPattern(ARTICLE_COMMENTCOUNT + articleId + "*");
        });

        return articleAndCommentMapper.delById(id);
    }

    @Override
    public Integer delByArticleId(Long articleId) {
        /*删除缓存*/
        taskExecutePoolUtil.myTaskAsyncPool().execute(()->{
            cacheUtil.delAllCacheByPattern(ARTICLE_COMMENT_CACHE + articleId + "*");
            cacheUtil.delAllCacheByPattern(ARTICLE_COMMENTCOUNT + articleId + "*");
        });

        return articleAndCommentMapper.delByArticleId(articleId);
    }

    @Override
    public Long findArticleIdById(Long id) {
        return articleAndCommentMapper.findArticleIdById(id);
    }

    @Override
    public Result saveComment(String info, String articleId, HttpServletRequest request) {
        Map<String, Object> userInfo = tokenUtil.getUserInfoByRequest(request);
        JSONObject body = JSON.parseObject(info);
        String comment = body.getString("comment");
        if(StringUtils.isBlank(comment)){
            return Result.failure(PARAM_IS_BLANK);
        }
        if(comment.length()>500){
            return Result.failure(STRING_OVER_LIMMIT);
        }
        Integer commentsnum = articleAndCommentMapper.selectCommentsnumByArticleId(Long.valueOf(articleId));
        if(commentsnum > 99){
            return Result.failure(COMMENTS_OVERFLOW);
        }
        String from = (String) userInfo.get("name");
        String to = body.getString("to");
        String pId = body.getString("parentId");
        //String articleId = body.getString("article_id");
        String headimg = body.getString("headimg");
        String sex = body.getString("sex");
        String motto = body.getString("motto");
        String email = body.getString("email");
        Comment c = new Comment();
        c.setId(AutoIdUtil.getId());
        Date date = new Date();
        c.setOpTime(date);
        c.setContent(comment);
        c.setFromName(from);
        c.setToName(to);
        c.setParentId(Long.parseLong(pId));
        c.setArticleId(Long.parseLong(articleId));
        c.setHeadimg(headimg);
        c.setSex(sex);
        c.setMotto(motto);
        c.setEmail(email);
        c.setAddress(ipUtil.getRealAddr(request));
        Integer res = articleAndCommentMapper.insertComment(c);
        if(1==res){
            /*删除缓存*/
            cacheUtil.delAllCacheByPattern(ARTICLE_COMMENT_CACHE + articleId + "*");
            cacheUtil.delAllCacheByPattern(ARTICLE_COMMENTCOUNT + articleId + "*");

            articleCommentCountHandler(Long.valueOf(articleId), commentsnum);
            return Result.success();
        }
        return Result.failure(SYSTEM_ERROR);
    }

    private void articleCommentCountHandler(Long articleId, Integer commentsnum){
        try {
            taskExecutePoolUtil.myTaskAsyncPool().execute(()->{
                String cacheKey = ARTICLE_COMMENTCOUNT + articleId;
                redisUtil.set(cacheKey, String.valueOf(commentsnum+1), 1, TimeUnit.DAYS);
                ScheduledTaskCollection.commentCountSet.add(cacheKey);
            });
        }catch (Exception ex){
            LoggerUtil.ex.error("articleCommentCountHandler|{}|{}", articleId, ex.getMessage());
        }
    }

    @Override
    public Result selectCommentsByArticleId(Long id) {
        /*缓存*/
        String cacheKey = ARTICLE_COMMENT_CACHE + id;
        if(cacheUtil.hasKey(cacheKey)){
            return JSONObject.parseObject(cacheUtil.getByCache(cacheKey), Result.class);
        }

        Optional<Long> op = Optional.ofNullable(id);
        if(!op.isPresent()){
            return Result.failure(PARAM_NOT_COMPLETE);
        }
        List<Comment> commentsByArticle = articleAndCommentMapper.findAllCommentsByArticle(id);;
        /*缓存*/
        cacheUtil.putToCache(cacheKey, Result.success(commentsByArticle), 1, TimeUnit.HOURS);

        return Result.success(commentsByArticle);
    }

    @Override
    public Integer getCommentsnumByArticleId(Long id) {
        return articleAndCommentMapper.selectCommentsnumByArticleId(id);
    }

}
