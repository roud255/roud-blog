package top.roud.cms.controller.manage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.roud.cms.common.annotation.AccessIPRecord;
import top.roud.cms.common.annotation.OperationAuth;
import top.roud.cms.common.exception.ServiceException;
import top.roud.cms.common.result.Result;
import top.roud.cms.common.result.ResultCode;
import top.roud.cms.common.utils.*;
import top.roud.cms.entity.*;
import top.roud.cms.service.*;
import top.roud.cms.service.impl.CommonService;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static top.roud.cms.common.result.ResultCode.*;

/**
 * @ClassName: ManageController
 * @Description:
 * @Author roud
 * @Date 2022/9/25
 * @Version 1.0
 */
@RestController
@RequestMapping("/manage")
public class ManageController {
    @Autowired
    private ArticleAndTagService articleAndTagService;
    @Autowired
    private UserService userService;
    @Autowired
    private ForBidIPService forBidIPService;
    @Autowired
    private TimeTransUtil timeTransUtil;
    @Autowired
    private MD5Util md5Util;
    @Autowired
    private UserInformationService userInformationService;
    @Autowired
    private ArticleAndCommentService articleAndCommentService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private TokenUtil tokenUtil;

    private ForbidIP forbidIP;
    private Article a;

    @Autowired
    private ThreeCacheUtil threeCacheUtil;

    @Autowired
    private SelfArticleValidateService selfArticleValidateService;

    @Autowired
    private IPUtil ipUtil;

    @Autowired
    private CommonService commonService;


    @OperationAuth
    @AccessIPRecord
    @PostMapping("/user/add")
    public Result save(@RequestBody User user,HttpServletRequest request){
        User userByPhonenumber = userService.findUserByPhonenumber(user.getPhonenumber());
        Optional<User> op = Optional.ofNullable(userByPhonenumber);
        if(op.isPresent()){
            return Result.failure(EMAIL_HAS_EXISTED);
        }
        UserInformation userInformation = new UserInformation().setUser(user).setId(AutoIdUtil.getId()).setRecentlyip(ipUtil.getIpAddr(request));
        userInformationService.save(userInformation);
        user.setPassword(md5Util.md5(user.getPassword()));
        return userService.save(user);
    }
    @AccessIPRecord
    @GetMapping("/user/select")
    public Result findpages(HttpServletRequest req, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10")Integer pageSize, @RequestParam(defaultValue = "")String search){
        Result page = userService.findPage(pageNum, pageSize, search);
        String token = tokenUtil.getToken(req);
        Map<String, Object> info = JwtUtil.getInfo(token);
        //暂定根据type判断操作权限
        int type = (int)info.get("type");
        if(0!=type){
            Page data = (Page) (page.getData());
            List<User> records = data.getRecords();
            for(User u:records){
                u.setId(123456789L);
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
        return page;
    }
    @OperationAuth
    @AccessIPRecord
    @PutMapping("/user/update")
    public Result update(@RequestBody User user){
        user.setPassword(md5Util.md5(user.getPassword()));
        return userService.updateById(user);
    }
    @OperationAuth
    @AccessIPRecord
    @DeleteMapping("/user/del/{id}")
    public Result delById(@PathVariable Long id){
        return userService.delById(id);
    }
    @AccessIPRecord
    @GetMapping("/article/fp")
    public Result fp(@RequestParam(defaultValue = "1") Integer num, @RequestParam(defaultValue = "10")Integer size, @RequestParam(defaultValue = "")String search){
        Page<Article> page =  articleAndTagService.findPage_second(num, size, search);
        return Result.success(page);
    }
    @AccessIPRecord
    @GetMapping("/article/fps")
    public Result fps(HttpServletRequest req, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10")Integer pageSize, @RequestParam(defaultValue = "")String search){
        Page<ArticleWithValidateCode> page =  articleAndTagService.findPage_three(pageNum, pageSize, search);
        String token = tokenUtil.getToken(req);
        Map<String, Object> info = JwtUtil.getInfo(token);
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

    @OperationAuth
    @AccessIPRecord
    @PutMapping("/article/update")
    public Result updateArticle(@RequestBody ArticleWithValidateCode articleWithValidateCode){
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
            String key = ConstUtil.REDIS_ARTICLE_KEY+articleWithValidateCode.getId();
            String cache = (String) redisUtil.get(key);
            if(Optional.ofNullable(cache).isPresent()){
                redisUtil.delete(key);
            }
            return Result.success();
        }
        return Result.failure(SYSTEM_ERROR);
    }

    private void updateSelfArticleValidateCode(ArticleWithValidateCode a){
        if(a.getSelf()==1){
            SelfArticle selfArticle = new SelfArticle();
            selfArticle.setId(AutoIdUtil.getId());
            selfArticle.setArticleId(a.getId());
            selfArticle.setValidateCode(a.getValidateCode());
            Integer resInt = selfArticleValidateService.updateSelfArticleValidateCode(selfArticle);
            if(resInt != 1){
                LoggerUtil.ex.error("saveSelfArticleValidateCode|err|{}|{}",a.getId(),a.getValidateCode());
            }
        }
    }

    @OperationAuth
    @AccessIPRecord
    @DeleteMapping("/article/del/{id}")
    public Result del(@PathVariable Long id){
        try{
            articleAndTagService.delArticleWithTag(id);
            articleAndCommentService.delByArticleId(id);
            String key = ConstUtil.REDIS_ARTICLE_KEY+id;
            String cache = (String) redisUtil.get(key);
            if(Optional.ofNullable(cache).isPresent()){
                redisUtil.delete(key);
            }
        }catch (Exception e){
            return Result.failure(SYSTEM_ERROR);
        }
        return Result.success();
    }
    @AccessIPRecord
    @GetMapping("/tags/select")
    public Result getAllTags(){
        List<Tag> allTags = articleAndTagService.getAllTags();
        return Result.success(allTags);
    }
    @OperationAuth
    @AccessIPRecord
    @PostMapping("/article/add")
    public Result addArticle(@RequestBody String info) {
        JSONObject jsonObject = JSON.parseObject(info);
        if(!jsonObject.containsKey("tags")){
            return Result.failure(SPECIFIED_QUESTIONED_USER_NOT_EXIST);
        }
        JSONArray tags = jsonObject.getJSONArray("tags");
        jsonObject.remove("tags");
        Article article = JSON.parseObject(jsonObject.toString(), Article.class);
        a = new Article();
        BeanUtils.copyProperties(article, a);
        a.setId(AutoIdUtil.getId());
        String publishtime = jsonObject.getString("publishtime");
        Date time = null;
        try {
            time = timeTransUtil.transUTCToZ(publishtime);
        } catch (ParseException e) {
            return Result.failure(e.getMessage());
        }
        a.setPublishtime(time);
        articleAndTagService.insertArticle(a);
        Tag tag = new Tag();
        for(Object t : tags){
            if(((String)t).trim().isEmpty()){
                return Result.failure(ResultCode.TAG_NAME_FORMAT_ERROR);
            }
            Tag tag_db = articleAndTagService.getTagByName((String) t);
            Optional<Tag> op = Optional.ofNullable(tag_db);
            if(op.isPresent()){
                articleAndTagService.insertArticleAndTag(AutoIdUtil.getId(), a, tag_db);
                saveSelfArticleValidateCode(a, jsonObject);
            }else {
                tag.setId(AutoIdUtil.getId());
                tag.setAddtime(time);
                tag.setTagname(((String) t).trim());
                tag.setDescription("");
                articleAndTagService.insertTag(tag);
                articleAndTagService.insertArticleAndTag(AutoIdUtil.getId(), a, tag);
                saveSelfArticleValidateCode(a, jsonObject);
            }
        }
        return Result.success();
    }
    private void saveSelfArticleValidateCode(Article a, JSONObject jsonObject){
        if(a.getSelf()==1){
            SelfArticle selfArticle = new SelfArticle();
            selfArticle.setId(AutoIdUtil.getId());
            selfArticle.setArticleId(a.getId());
            String validateCode = StringUtils.isNotBlank((String)jsonObject.get("validateCode"))?(String)jsonObject.get("validateCode"):"Va3xp6";
            selfArticle.setValidateCode(validateCode);
            Integer resInt = selfArticleValidateService.saveSelfArticleValidateCode(selfArticle);
            if(resInt != 1){
                LoggerUtil.ex.error("saveSelfArticleValidateCode|err|{}|{}",a.getId(),validateCode);
            }
        }
    }
    @AccessIPRecord
    @GetMapping("/user/info")
    public Result getUserInfo(@RequestParam String token){
        String threeCacheKey = ConstUtil.CACHE_USERINFO_PRE + token;
        String resStringbyThreeCache = threeCacheUtil.getByCache(threeCacheKey);
        if(StringUtils.isNotBlank(resStringbyThreeCache)){
            Map<String,Object> map = JSON.parseObject(resStringbyThreeCache, Map.class);
            LoggerUtil.cacheLog.info("从缓存中获取用户信息|{}", token);
            return Result.success(map);
        }
        String largeToken = tokenUtil.getLargeToken(token);
        boolean flag = JwtUtil.checkSign(largeToken);
        if(flag){
            return Result.success(JwtUtil.getInfo(largeToken));
        }
        return Result.failure(TOKEN_INVALID);
    }
    @AccessIPRecord
    @GetMapping("/ip/select")
    public Result selectIps(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10")Integer pageSize, @RequestParam(defaultValue = "")String search){
        Result pages = forBidIPService.findPages(pageNum, pageSize, search);
        return pages;
    }
    @OperationAuth
    @AccessIPRecord
    @DeleteMapping("/ip/del/{id}")
    public Result delIp(@PathVariable Long id){
        Result result = forBidIPService.del(id);
        return result;
    }
    @AccessIPRecord
    //时间传入重构
    @PostMapping("/ip/add")
    public Result addip(@RequestBody String info){
        JSONObject jsonObject = JSON.parseObject(info);
        forbidIP = new ForbidIP();
        forbidIP.setId(AutoIdUtil.getId());
        String ip = jsonObject.getString("ip");
        ForbidIP forbidIP = forBidIPService.selectForBidIPByIp(ip);
        Optional<ForbidIP> op = Optional.ofNullable(forbidIP);
        if(op.isPresent()){
            return Result.failure(DATA_EXISTED);
        }
        this.forbidIP.setIp(ip);
        String reason = jsonObject.getString("reason");
        this.forbidIP.setReason(reason);

        String time = jsonObject.getString("time");
        Date t = null;
        try {
            t = timeTransUtil.transUTCToZ(time);
        } catch (ParseException e) {
            return Result.failure(e.getMessage());
        }
        this.forbidIP.setTime(t);
        Result result = forBidIPService.save(this.forbidIP);
        return result;
    }
    @OperationAuth
    @AccessIPRecord
    @PutMapping("/ip/update")
    public Result update(@RequestBody ForbidIP f){
        Result result = forBidIPService.update(f);
        return result;
    }

    @OperationAuth
    @AccessIPRecord
    @DeleteMapping("/comment/del/{id}")
    public Result delCommentById(@PathVariable(value = "id") Long id){
        Long articleIdById = articleAndCommentService.findArticleIdById(id);
        if(null == articleIdById){
            throw new ServiceException(DATA_NONE);
        }
        Integer res = articleAndCommentService.delById(id);
        if(res==1){
            String key = ConstUtil.REDIS_COMMENTS_KEY+articleIdById;
            if(Optional.ofNullable(redisUtil.get(key)).isPresent()){
                redisUtil.delete(key);
            }
            commonService.updateCommentCache(articleIdById);
            return Result.success();
        }
        return Result.failure(SYSTEM_INNER_ERROR);
    }

    @AccessIPRecord
    @GetMapping("/comment/findall")
    public Result findAll(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10")Integer pageSize, @RequestParam(defaultValue = "")String search){
        Page<Comment>  allComments;
        try{
            allComments = articleAndCommentService.findAllComments(pageNum, pageSize, search);
        }catch (Exception e){
            return Result.failure(SYSTEM_INNER_ERROR);
        }
        return Result.success(allComments);
    }

}
