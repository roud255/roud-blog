package top.roud.roudblogcms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.roud.roudblogcms.common.result.Result;
import top.roud.roudblogcms.common.result.ResultCode;
import top.roud.roudblogcms.common.utils.*;
import top.roud.roudblogcms.entity.Article;
import top.roud.roudblogcms.entity.ArticleWithValidateCode;
import top.roud.roudblogcms.entity.Tag;
import top.roud.roudblogcms.mapper.ArticleAndTagMapper;
import top.roud.roudblogcms.service.ArticleAndTagService;
import top.roud.roudblogcms.service.SelfArticleValidateService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static top.roud.roudblogcms.common.result.ResultCode.PARAM_NOT_COMPLETE;
import static top.roud.roudblogcms.common.utils.ConstUtil.*;

/**
 * @description:
 * @author: roud
 * @date: 2024/1/26
 * @version: 1.0.0
 */
@Service
public class ArticleAndTagServiceImpl implements ArticleAndTagService {
    @Autowired
    private ArticleAndTagMapper articleAndTagMapper;

    @Autowired
    private SelfArticleValidateService selfArticleValidateService;

    @Autowired
    private CacheUtil cacheUtil;

    @Autowired
    private TaskExecutePoolUtil taskExecutePoolUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void insertArticle(Article article) {
        /*删除缓存*/
        taskExecutePoolUtil.myTaskAsyncPool().execute(()->{
            cacheUtil.delAllCacheByPattern(ARTICLE_TAG_FPWB_CONTACT + "*");
        });

        articleAndTagMapper.insertArticle(article);
    }

    @Override
    public Result saveArticle(String info) {
        Article a = new Article();
        JSONObject jsonObject = JSON.parseObject(info);
        Article article = JSON.parseObject(info, Article.class);
        BeanUtils.copyProperties(article, a);
        a.setId(AutoIdUtil.getId());
        String publishtime = jsonObject.getString("publishtime");
        String dateTime = publishtime .replace("Z", " UTC"); //2019-06-27T16:00:00.000 UTC
        SimpleDateFormat format_z = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//转换时区格式
        SimpleDateFormat format_final = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time= null;
        try {
            time = format_z.parse(dateTime);
        } catch (ParseException e) {
            return Result.failure(e.getMessage());
        }
        a.setPublishtime(time);
        JSONArray tags = jsonObject.getJSONArray("tags");
        insertArticle(a);
        Tag tag = new Tag();
        for(Object t : tags){
            if(((String)t).trim().isEmpty()){
                return Result.failure(ResultCode.TAG_NAME_FORMAT_ERROR);
            }
            Tag tag_db = articleAndTagMapper.getTagByName((String) t);
            Optional<Tag> op = Optional.ofNullable(tag_db);
            if(op.isPresent()){
                insertArticleAndTag(AutoIdUtil.getId(), a, tag_db);
            }else {
                tag.setId(AutoIdUtil.getId());
                tag.setAddtime(time);
                tag.setTagname(((String) t).trim());
                tag.setDescription("");
                articleAndTagMapper.insertTag(tag);
                insertArticleAndTag(AutoIdUtil.getId(), a, tag);
            }
        }
        return Result.success();
    }

    @Override
    public void insertTag(Tag tag) {
        articleAndTagMapper.insertTag(tag);
    }

    public boolean selfArticleValidate(Article article, String code){
        if(StringUtils.isBlank(code)){
            return false;
        }
        String validateCode = selfArticleValidateService.getValidateCodeByArticleId(article.getId());
        if(StringUtils.equals(code, validateCode)){
            return true;
        }
        return false;
    }

    @Override
    public Result getArticleById(Long id, String validateCode) {
        if(!Optional.ofNullable(id).isPresent()){
            return Result.failure(PARAM_NOT_COMPLETE);
        }
        /*缓存*/
        String cacheKey = ARTICLE_CACHE + id;
        if(cacheUtil.hasKey(cacheKey)){
            String res = cacheUtil.getByCache(cacheKey);
            articleViewCountHandler2(res);
            return JSONObject.parseObject(cacheUtil.getByCache(cacheKey), Result.class);
        }
        /*专属文章缓存*/
        String cacheKey2 = ARTICLE_CACHE + id + "." + stringHandler(validateCode);
        if(cacheUtil.hasKey(cacheKey2)){
            //访问数处理
            String res2 = cacheUtil.getByCache(cacheKey2);
            articleViewCountHandler2(res2);
            return JSONObject.parseObject(res2, Result.class);
        }

        Article articleAndhTag = getArticleByIdWithTag(id);
        Optional<Article> op = Optional.ofNullable(articleAndhTag);
        if(op.isPresent()){
            //专属文章
            if(articleAndhTag.getSelf()==1){
                if(StringUtils.isBlank(validateCode)){
                    return Result.failure(ResultCode.VALIDATEF_FAIL);
                }
                if(!selfArticleValidate(articleAndhTag, validateCode)){
                    return Result.failure(ResultCode.VALIDATEF_FAIL);
                }
                /*专属文章缓存*/
                cacheUtil.putToCache(cacheKey2, Result.success(articleAndhTag), 1, TimeUnit.DAYS);
            }else {
                /*缓存*/
                cacheUtil.putToCache(cacheKey, Result.success(articleAndhTag), 1, TimeUnit.DAYS);
            }
            //访问数处理
            articleViewCountHandler(articleAndhTag);

            return Result.success(articleAndhTag);
        }else{
            Article article = articleAndTagMapper.getArticleById(id);
            Optional<Article> op_a = Optional.ofNullable(article);
            if(op_a.isPresent()){
                if(article.getSelf()==1){
                    if(!selfArticleValidate(article, validateCode)){
                        return Result.failure(ResultCode.VALIDATEF_FAIL);
                    }
                    /*专属文章缓存*/
                    cacheUtil.putToCache(cacheKey2, Result.success(article), 1, TimeUnit.DAYS);
                }else {
                    /*缓存*/
                    cacheUtil.putToCache(cacheKey, Result.success(article), 1, TimeUnit.DAYS);
                }
                //访问数处理
                articleViewCountHandler(article);

                return Result.success(article);
            }
            return Result.failure(ResultCode.DATA_NONE);
        }
    }

    private void articleViewCountHandler(Article article){
        try {
            taskExecutePoolUtil.myTaskAsyncPool().execute(()->{
                String cacheKey = ARTICLE_VIEWCOUNT + article.getId();
                if(!redisUtil.hasKey(cacheKey)){
                    redisUtil.set(cacheKey, String.valueOf(article.getViewsnum()+1), 1, TimeUnit.DAYS);
                }
                redisUtil.increment(cacheKey, String.valueOf(1), 1, TimeUnit.DAYS);
                ScheduledTaskCollection.viewCountSet.add(cacheKey);
            });
        }catch (Exception ex){
            LoggerUtil.ex.error("articleViewCountHandler|{}|{}", JSONObject.toJSONString(article), ex.getMessage());
        }
    }

    private void articleViewCountHandler2(String articleStr){
        try {
            Article data = JSONObject.parseObject(JSONObject.toJSONString(JSONObject.parseObject(articleStr).get("data")), Article.class);
            articleViewCountHandler(data);
        }catch (Exception ex){
            LoggerUtil.ex.error("articleViewCountHandle2|{}|{}", articleStr, ex.getMessage());
        }
    }

    @Override
    public Tag getTagById(Long id) {
        return articleAndTagMapper.getTagById(id);
    }

    @Override
    public void insertArticleAndTag(Long id, Article article, Tag tag) {
        articleAndTagMapper.insertArticleAndTag(id, article.getId(), tag.getId());
    }

    @Override
    public Article getArticleByIdWithTag(Long id) {
        return articleAndTagMapper.getArticleByIdWithTag(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return articleAndTagMapper.getTagByName(name);
    }

    @Override
    public List<Tag> getAllTags() {
        return articleAndTagMapper.getAllTags();
    }

    @Override
    public Result delTagById(Long id) {
        List<Long> aids = articleAndTagMapper.selectAllArticleIdByTagId(id);
        if(aids.size()>0){
            StringBuilder sb = new StringBuilder("");
            aids.forEach(o->sb.append(o).append(";"));
            String s = sb.toString();
            return Result.failure(50001, "操作失败，存在关联文章id："+ s );
        }
        if(1 == articleAndTagMapper.delTagById(id)){
            return Result.success();
        }
        return Result.failure(ResultCode.SYSTEM_ERROR);
    }

    @Override
    public Result updateTagById(Tag tag) {
        if(null == tag.getId() || StringUtils.isBlank(tag.getTagname())){
            return Result.failure(ResultCode.PARAM_IS_INVALID);
        }
        if(1 == articleAndTagMapper.updateTagnameById(tag)){
            return Result.success();
        }
        return Result.failure(ResultCode.SYSTEM_ERROR);
    }

    @Override
    public Page<TagExt> findTagsPage(Integer pageNum, Integer pageSize, String search) {
        Integer pagestart = (pageNum-1)*pageSize;
        List<Tag> tagsPage = articleAndTagMapper.getTagsPage(pagestart, pageSize, search);
        List<TagExt> collect = tagsPage.stream().map(o -> {
                    TagExt tagExt = new TagExt();
                    BeanUtils.copyProperties(o, tagExt);
                    StringBuilder sb = new StringBuilder("");
                    o.getArticles().forEach(i -> {
                        if(null != i.getId()){
                            sb.append(i.getId()).append(";");
                        }
                    });
                    tagExt.setInvolveArticles(sb.toString());
                    return tagExt;
                }
        ).collect(Collectors.toList());
        Page<TagExt> result = new Page<>();
        int total = (articleAndTagMapper.getTagsPage(pagestart, 0, search)).size();
        result.setPages((int)Math.ceil((double)total/pageSize));
        result.setTotal(total);
        result.setCurrent(pageNum);
        result.setRecords(collect);
        return result;
    }

    @Data
    public class TagExt extends Tag{
        private String involveArticles;
    }

    @Override
    public List<Article> getAllArticleWithTag() {
        return articleAndTagMapper.getAllArticleWithTag();
    }

    @Override
    public Page<ArticleWithValidateCode> findPageWithValidatecode(Integer pageNum, Integer pageSize, String search) {
        LambdaQueryWrapper<ArticleWithValidateCode> wrapper = Wrappers.<ArticleWithValidateCode>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(ArticleWithValidateCode::getTitle, search);
        }
        Page<ArticleWithValidateCode> result = articleAndTagMapper.selectPageWithValidateCode(new Page<>(pageNum, pageSize), wrapper);
        List<ArticleWithValidateCode> records = result.getRecords();
        if(0 == records.size()){
            return result;
        }
        List<ArticleWithValidateCode> newRecords = new LinkedList<>();
        for(ArticleWithValidateCode article : records){
            List<Tag> tags = articleAndTagMapper.getTagByArticleId(article.getId());
            if(0 == tags.size()){
                continue;
            }
            article.setTags(tags);
            newRecords.add(article);
        }
        result.setRecords(newRecords);
        return result;    }

    @Override
    public Result findPageWithoutBody(Integer pageNum, Integer pageSize, String search, String type) {
        /*缓存*/
        String cacheKey = ARTICLE_TAG_FPWB_CONTACT + pageNum + "." + pageSize + "" + stringHandler(search) + "." + stringHandler(type);
        if(cacheUtil.hasKey(cacheKey)){
            return JSONObject.parseObject(cacheUtil.getByCache(cacheKey), Result.class);
        }

        Page<Article> page;
        if(StringUtils.equals(type, "2")){
            page =  findPageByTagWithoutBody(pageNum, pageSize, search);
        }else {
            page =  findPageByTitleWithoutBody(pageNum, pageSize, search);
        }
        /*缓存*/
        cacheUtil.putToCache(cacheKey, Result.success(page), 10, TimeUnit.SECONDS);

        return Result.success(page);
    }


    @Override
    public Page<Article> findPageByTag(Integer pageNum, Integer pageSize, String search) {
        return null;
    }

    @Override
    public Page<Article> findPageByTagWithoutBody(Integer pageNum, Integer pageSize, String search) {
        Integer pagestart = (pageNum-1)*pageSize;
        List<Article> records = articleAndTagMapper.selectPageByTagWithoutBody(search, pagestart, pageSize);
        Integer total = (articleAndTagMapper.selectPageByTag(search, pagestart, 0)).size();
        Page<Article> result = new Page<>();
        List<Article> records_new = new LinkedList<>();
        for(Article article : records){
            List<Tag> tags = articleAndTagMapper.getTagByArticleId(article.getId());
            if(0 == tags.size()){
                continue;
            }
            article.setTags(tags);
            records_new.add(article);
        }
        result.setPages((int)Math.ceil((double)total/pageSize));
        result.setTotal(total);
        result.setCurrent(pageNum);
        result.setRecords(records_new);
        return result;
    }

    @Override
    public Page<Article> findPageByTitleWithoutBody(Integer pageNum, Integer pageSize, String search) {
        LambdaQueryWrapper<Article> wrapper = Wrappers.<Article>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(Article::getTitle, search);
        }
        Page<Article> result = articleAndTagMapper.selectPageWithoutBody(new Page<>(pageNum, pageSize), wrapper);
        List<Article> records = result.getRecords();
        if(0 == records.size()){
            return result;
        }
        List<Article> records_new = new LinkedList<>();
        for(Article article : records){
            List<Tag> tags = articleAndTagMapper.getTagByArticleId(article.getId());
            if(0 == tags.size()){
                continue;
            }
            article.setTags(tags);
            records_new.add(article);
        }
        result.setRecords(records_new);
        return result;
    }

    @Override
    public List<Tag> getTagByArticleId(Long id) {
        return articleAndTagMapper.getTagByArticleId(id);
    }

    @Override
    public void delArticleWithTag(Long id) {
        /*删除缓存*/
        taskExecutePoolUtil.myTaskAsyncPool().execute(()->{
            cacheUtil.delAllCacheByPattern(ARTICLE_CACHE + id + "*");
            cacheUtil.delAllCacheByPattern(ARTICLE_TAG_FPWB_CONTACT + "*");
            cacheUtil.delAllCacheByPattern(ARTICLE_COMMENT_CACHE + id + "*");
        });

        articleAndTagMapper.delArticleWithTag(id);
    }

    @Override
    public Integer updateArticleById(Article article) {
        /*删除缓存*/
        taskExecutePoolUtil.myTaskAsyncPool().execute(()->{
            cacheUtil.delAllCacheByPattern(ARTICLE_CACHE + article.getId() + "*");
            cacheUtil.delAllCacheByPattern(ARTICLE_TAG_FPWB_CONTACT + "*");
        });

        return articleAndTagMapper.updateById(article);
    }

    @Override
    public Integer updateViewsnumAndCommentsnumByArticleId(Long id, Integer...arr) {
        /*删除缓存*/
        taskExecutePoolUtil.myTaskAsyncPool().execute(()->{
            cacheUtil.delAllCacheByPattern(ARTICLE_CACHE + id + "*");
            cacheUtil.delAllCacheByPattern(ARTICLE_TAG_FPWB_CONTACT + "*");
        });

        if(arr.length == 1){
            return articleAndTagMapper.updateViewsnumByArticleId(id, arr[0]);
        }else if(arr.length == 2) {
            return articleAndTagMapper.updateCommentsnumByArticleId(id, arr[1]);
        }
        return 0;
    }

    @Override
    public Integer getViewsnumByArticleId(Long id) {
        LambdaQueryWrapper<Article> lqw = Wrappers.<Article>lambdaQuery();
        lqw.eq(Article::getId, id);
        Article article = articleAndTagMapper.selectOne(lqw);
        if(Optional.ofNullable(article).isPresent()){
            return article.getViewsnum();
        }
        return 0;
    }

    @Override
    public Integer getCommentsnumByArticleId(Long id) {
        LambdaQueryWrapper<Article> lqw = Wrappers.<Article>lambdaQuery();
        lqw.eq(Article::getId, id);
        Article article = articleAndTagMapper.selectOne(lqw);
        if(Optional.ofNullable(article).isPresent()){
            return article.getCommentsnum();
        }
        return 0;
    }

    private String stringHandler(String str){
        return StringUtils.isBlank(str)? "" : str;
    }
}
