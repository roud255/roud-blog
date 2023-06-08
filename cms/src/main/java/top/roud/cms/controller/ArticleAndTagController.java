package top.roud.cms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.roud.cms.common.result.Result;
import top.roud.cms.common.result.ResultCode;
import top.roud.cms.common.annotation.AccessIPRecord;
import top.roud.cms.common.annotation.NoRepeatRequest;
import top.roud.cms.common.annotation.OperationAuth;
import top.roud.cms.common.utils.ConstUtil;
import top.roud.cms.common.utils.RedisUtil;
import top.roud.cms.entity.Article;
import top.roud.cms.entity.Tag;
import top.roud.cms.service.ArticleAndTagService;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static top.roud.cms.common.result.ResultCode.PARAM_NOT_COMPLETE;

/**
 * @ClassName: ArticleAndTagController
 * @Description:
 * @Author roud
 * @Date 2022/9/3
 * @Version 1.0
 */
@RestController
@RequestMapping("/aat")
public class ArticleAndTagController {
    @Resource
    private ArticleAndTagService articleAndTagService;
    @Resource
    private RedisUtil redisUtil;
    @OperationAuth
    @AccessIPRecord
    @PostMapping("/add")
    public Result addArticle(@RequestBody String info) {
        JSONObject jsonObject = JSON.parseObject(info);
        Article a = new Article();
        a.setId(System.currentTimeMillis());
        String title = jsonObject.getString("title");
        a.setTitle(title);
        String author = jsonObject.getString("author");
        a.setAuthor(author);
        String description = jsonObject.getString("description");
        a.setDescription(description);
        String cover = jsonObject.getString("cover");
        a.setCover(cover);
        String postbody = jsonObject.getString("postbody");
        a.setPostbody(postbody);
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
        articleAndTagService.insertArticle(a);
        Tag tag = new Tag();
        for(Object t : tags){
            if(((String)t).trim().isEmpty()){
                return Result.failure(ResultCode.TAG_NAME_FORMAT_ERROR);
            }
            Tag tag_db = articleAndTagService.getTagByName((String) t);
            Optional<Tag> op = Optional.ofNullable(tag_db);
            if(op.isPresent()){
                articleAndTagService.insertArticleAndTag(System.currentTimeMillis(), a, tag_db);
            }else {
                tag.setId(System.currentTimeMillis());
                tag.setAddtime(time);
                tag.setTagname(((String) t).trim());
                tag.setDescription("");
                articleAndTagService.insertTag(tag);
                articleAndTagService.insertArticleAndTag(System.currentTimeMillis(), a, tag);
            }
        }
        return Result.success();
    }

    @AccessIPRecord
    @GetMapping("/getArticleById")
    public Result getArticleById(Long id){
        if(!Optional.ofNullable(id).isPresent()){
            return Result.failure(PARAM_NOT_COMPLETE);
        }
        String key = ConstUtil.REDIS_ARTICLE_KEY+id;
        String articleCacheStr = (String)redisUtil.get(key);
        if(StringUtils.isNotBlank(articleCacheStr)){
            Article articleCache = JSON.parseObject(articleCacheStr, Article.class);
            return Result.success(articleCache);
        }
        Article articleAndhTag = articleAndTagService.getArticleByIdWithTag(id);
        Optional<Article> op = Optional.ofNullable(articleAndhTag);
        if(op.isPresent()){
            redisUtil.set(key, JSON.toJSONString(articleAndhTag), 10, TimeUnit.MINUTES);
            return Result.success(articleAndhTag);
        }else{
            Article article = articleAndTagService.getArticleById(id);
            Optional<Article> op_a = Optional.ofNullable(article);
            if(op_a.isPresent()){
                redisUtil.set(key, JSON.toJSONString(article), 10, TimeUnit.MINUTES);
                return Result.success(article);
            }
            return Result.failure(ResultCode.DATA_NONE);
        }
    }
    @AccessIPRecord
    @GetMapping("/getAllTags")
    public Result getAllTags(){
        List<Tag> allTags = articleAndTagService.getAllTags();
        return Result.success(allTags);
    }
    @AccessIPRecord
    @GetMapping("/getAllArticleWithTags")
    public Result getAllArticleWithTags(){
        List<Article> allArticleWithTags = articleAndTagService.getAllArticleWithTag();
        return Result.success(allArticleWithTags);
    }
    @NoRepeatRequest(seconds = 60,maxCount = 30)
    @AccessIPRecord
    @GetMapping("/page")
    public Result test(Integer num, Integer size){
        Page<Article> page = articleAndTagService.findPage(num, size);
        return Result.success(page);
    }
    @AccessIPRecord
    @GetMapping("fp")
    public Result fp(@RequestParam(defaultValue = "1") Integer num, @RequestParam(defaultValue = "10")Integer size, @RequestParam(defaultValue = "")String search){
        Page<Article> page =  articleAndTagService.findPage_second(num, size, search);
        return Result.success(page);
    }
    @AccessIPRecord
    @GetMapping("fps")
    public Result findpages(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10")Integer pageSize, @RequestParam(defaultValue = "")String search){
        Page<Article> page =  articleAndTagService.findPage_second(pageNum, pageSize, search);
        return Result.success(page);
    }
    @OperationAuth
    @AccessIPRecord
    @DeleteMapping("/del/{id}")
    public Result del(@PathVariable Long id){
        articleAndTagService.delArticleWithTag(id);
        return Result.success();
    }
}
