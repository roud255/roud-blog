package top.roud.roudblogcms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import top.roud.roudblogcms.common.exception.ServiceException;
import top.roud.roudblogcms.common.result.Result;
import top.roud.roudblogcms.common.utils.*;
import top.roud.roudblogcms.entity.Article;
import top.roud.roudblogcms.entity.ArticleWithValidateCode;
import top.roud.roudblogcms.entity.Comment;
import top.roud.roudblogcms.entity.ForbidIP;
import top.roud.roudblogcms.entity.SelfArticle;
import top.roud.roudblogcms.entity.Tag;
import top.roud.roudblogcms.entity.User;
import top.roud.roudblogcms.entity.UserInformation;
import top.roud.roudblogcms.mapper.UserInformationMapper;
import top.roud.roudblogcms.mapper.UserMapper;
import top.roud.roudblogcms.service.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static top.roud.roudblogcms.common.result.ResultCode.*;

/**
 * @description:
 * @author: roud
 * @date: 2024/1/26
 * @version: 1.0.0
 */
@Service
public class ManageServiceImpl implements ManageService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MD5Util md5Util;
    @Autowired
    private IPUtil ipUtil;
    @Autowired
    private TimeTransUtil timeTransUtil;
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private UserInformationMapper userInformationMapper;
    @Autowired
    private ArticleAndTagService articleAndTagService;
    @Autowired
    private SelfArticleValidateService selfArticleValidateService;
    @Autowired
    private ArticleAndCommentService articleAndCommentService;
    @Autowired
    private ForBidIPService forBidIPService;

    @Override
    public Result addUser(User user, HttpServletRequest request) {
        LambdaQueryWrapper<User> lqw = Wrappers.<User>lambdaQuery();
        lqw.eq(User::getPhonenumber, user.getPhonenumber());
        User userByPhonenumber = userMapper.selectOne(lqw);
        Optional<User> op = Optional.ofNullable(userByPhonenumber);
        if(op.isPresent()){
            return Result.failure(EMAIL_HAS_EXISTED);
        }
        UserInformation userInformation = new UserInformation().setUser(user).setId(AutoIdUtil.getId()).setRecentlyip(ipUtil.getIpAddr(request));
        userInformationMapper.insert(userInformation);
        user.setPassword(md5Util.md5(user.getPassword()));
        user.setPower("1");
        int insert = userMapper.insert(user);
        if(1 == insert){
            return Result.success();
        }
        return Result.failure(SYSTEM_ERROR);
    }

    @Override
    public Result selectUser(HttpServletRequest req, Integer pageNum, Integer pageSize, String search) {
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(User::getNickname, search);
        }
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        Map<String, Object> info = tokenUtil.getUserInfoByRequest(req);
        if(0 == info.size()){
            return Result.failure(SYSTEM_INNER_ERROR);
        }
        //暂定根据type判断操作权限
        int type = (int)info.get("type");
        if(0 != type){
            List<User> records = userPage.getRecords();
            for(User u:records){
                u.setId(12345678910L);
                char[] chars = u.getPhonenumber().toCharArray();
                String email = "";
                int a = 0;
                for(char c:chars){
                    email = a > 0 && a < 4 ? email.concat("*") : email.concat(String.valueOf(c));
                    a++;
                }
                u.setPhonenumber(email);
                u.setPassword("************");
            }
        }
        return Result.success(userPage);
    }

    @Override
    public Result updateUser(User user) {
        user.setPassword(md5Util.md5(user.getPassword()));
        if(1 == userMapper.updateById(user)){
            return Result.success();
        }
        return Result.failure(SYSTEM_ERROR);
    }

    @Override
    public Result delUser(Long id) {
        if(1 == userMapper.deleteById(id)){
            return Result.success();
        }
        return null;
    }

    @Override
    public Result getUserInfoByToken(String token) {
        return Result.success(tokenUtil.getUserInfoByOutToken(token));
    }

    @Override
    public Result selectArticle(HttpServletRequest req, Integer pageNum, Integer pageSize, String search) {
        Page<ArticleWithValidateCode> page =  articleAndTagService.findPageWithValidatecode(pageNum, pageSize, search);
        Map<String, Object> info = tokenUtil.getUserInfoByRequest(req);
        //暂定根据type判断操作权限
        int type = (int)info.get("type");
        if(0!=type){
            List<ArticleWithValidateCode> records = page.getRecords();
            for(ArticleWithValidateCode awvc : records){
                awvc.setValidateCode("******");
            }
        }
        return Result.success(page);
    }

    private void saveSelfArticleValidateCode(Article article, JSONObject jsonObject){
        if(article.getSelf()==1){
            SelfArticle selfArticle = new SelfArticle();
            selfArticle.setId(AutoIdUtil.getId());
            selfArticle.setArticleId(article.getId());
            String validateCode = StringUtils.isNotBlank((String)jsonObject.get("validateCode"))?(String)jsonObject.get("validateCode"):"Va3xp6";
            selfArticle.setValidateCode(validateCode);
            Integer resInt = selfArticleValidateService.saveSelfArticleValidateCode(selfArticle);
            if(resInt != 1){
                LoggerUtil.ex.error("saveSelfArticleValidateCode|err|{}|{}",article.getId(),validateCode);
            }
        }
    }

    private void updateSelfArticleValidateCode(ArticleWithValidateCode article){
        if(article.getSelf()==1){
            SelfArticle selfArticle = new SelfArticle();
            selfArticle.setId(AutoIdUtil.getId());
            selfArticle.setArticleId(article.getId());
            selfArticle.setValidateCode(article.getValidateCode());
            Integer resInt = selfArticleValidateService.updateSelfArticleValidateCode(selfArticle);
            if(resInt != 1){
                LoggerUtil.ex.error("saveSelfArticleValidateCode|err|{}|{}",article.getId(),article.getValidateCode());
            }
        }
    }

    @Override
    public Result updateArticle(ArticleWithValidateCode articleWithValidateCode) {
        Integer count;
        try{
            Article article = new Article();
            BeanUtils.copyProperties(articleWithValidateCode, article);
            count = articleAndTagService.updateArticleById(article);
            updateSelfArticleValidateCode(articleWithValidateCode);
        }catch (Exception e){
            return Result.failure(SYSTEM_ERROR);
        }
        if(count==1){
            return Result.success();
        }
        return Result.failure(SYSTEM_ERROR);
    }

    @Override
    public Result delArticle(Long id) {
        try{
            articleAndTagService.delArticleWithTag(id);
            articleAndCommentService.delByArticleId(id);
            selfArticleValidateService.delSelfArticleValidateCodeByArticleId(id);
        }catch (Exception e){
            return Result.failure(SYSTEM_ERROR);
        }
        return Result.success();
    }

    @Override
    public Result addArticle(String articleInfo) {
        JSONObject jsonObject = JSON.parseObject(articleInfo);
        if(!jsonObject.containsKey("tags")){
            return Result.failure(SPECIFIED_QUESTIONED_USER_NOT_EXIST);
        }
        JSONArray tags = jsonObject.getJSONArray("tags");
        jsonObject.remove("tags");
        Article article = JSON.parseObject(jsonObject.toString(), Article.class);
        Article article1 = new Article();
        BeanUtils.copyProperties(article, article1);
        article1.setId(AutoIdUtil.getId());
        String publishtime = jsonObject.getString("publishtime");
        Date time;
        try {
            time = timeTransUtil.transUTCToZ(publishtime);
        } catch (ParseException e) {
            return Result.failure(e.getMessage());
        }
        article1.setPublishtime(time);
        articleAndTagService.insertArticle(article1);
        Tag tag = new Tag();
        for(Object t : tags){
            if(((String)t).trim().isEmpty()){
                return Result.failure(TAG_NAME_FORMAT_ERROR);
            }
            Tag tag_db = articleAndTagService.getTagByName((String) t);
            Optional<Tag> op = Optional.ofNullable(tag_db);
            if(op.isPresent()){
                articleAndTagService.insertArticleAndTag(AutoIdUtil.getId(), article1, tag_db);
                saveSelfArticleValidateCode(article1, jsonObject);
            }else {
                tag.setId(AutoIdUtil.getId());
                tag.setAddtime(time);
                tag.setTagname(((String) t).trim());
                tag.setDescription("");
                articleAndTagService.insertTag(tag);
                articleAndTagService.insertArticleAndTag(AutoIdUtil.getId(), article1, tag);
                saveSelfArticleValidateCode(article1, jsonObject);
            }
        }
        return Result.success();
    }

    @Override
    public Result selectAllTags() {
        List<Tag> allTags = articleAndTagService.getAllTags();
        return Result.success(allTags);
    }

    @Override
    public Result selectIPs(Integer pageNum, Integer pageSize, String search) {
        return forBidIPService.findPages(pageNum, pageSize, search);
    }

    @Override
    public Result delIP(Long id) {
        return forBidIPService.del(id);
    }

    @Override
    public Result updateIP(ForbidIP ip) {
        return forBidIPService.update(ip);
    }

    @Override
    public Result addIP(String ipInfo) {
        JSONObject jsonObject = JSON.parseObject(ipInfo);
        String ip = jsonObject.getString("ip");
        ForbidIP forbidIPSys = forBidIPService.selectForBidIPByIp(ip);
        Optional<ForbidIP> op = Optional.ofNullable(forbidIPSys);
        if(op.isPresent()){
            return Result.failure(DATA_EXISTED);
        }
        String reason = jsonObject.getString("reason");
        Long seconds = Long.parseLong(jsonObject.getString("seconds"));
        String time = jsonObject.getString("time");
        Date t;
        try {
            t = timeTransUtil.transUTCToZ(time);
        } catch (ParseException e) {
            return Result.failure(e.getMessage());
        }
        ForbidIP forbidIP = new ForbidIP();
        forbidIP.setId(AutoIdUtil.getId());
        forbidIP.setIp(ip);
        forbidIP.setReason(reason);
        forbidIP.setSeconds(seconds);
        forbidIP.setTime(t);
        return forBidIPService.save(forbidIP);
    }

    @Override
    public Result delComment(Long id) {
        Long articleIdById = articleAndCommentService.findArticleIdById(id);
        if(null == articleIdById){
            throw new ServiceException(DATA_NONE);
        }
        Integer res = articleAndCommentService.delById(articleIdById, id);
        if(res==1){
            return Result.success();
        }
        return Result.failure(SYSTEM_INNER_ERROR);
    }

    @Override
    public Result selectComment(Integer pageNum, Integer pageSize, String search) {
        Page<Comment>  allComments;
        try{
            allComments = articleAndCommentService.findAllComments(pageNum, pageSize, search);
        }catch (Exception e){
            return Result.failure(SYSTEM_INNER_ERROR);
        }
        return Result.success(allComments);
    }
}
