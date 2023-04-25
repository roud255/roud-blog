package top.roud.cms.controller.manage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import top.roud.cms.common.Result;
import top.roud.cms.common.ResultCode;
import top.roud.cms.entity.Article;
import top.roud.cms.entity.ForbidIP;
import top.roud.cms.entity.Tag;
import top.roud.cms.entity.User;
import top.roud.cms.service.ArticleAndTagService;
import top.roud.cms.service.ForBidIPService;
import top.roud.cms.service.UserService;
import top.roud.cms.utils.JwtUtil;
import top.roud.cms.utils.MD5Util;
import top.roud.cms.utils.TimeTransUtil;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static top.roud.cms.common.ResultCode.*;

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
    @Resource
    private ArticleAndTagService articleAndTagService;
    @Resource
    private UserService userService;
    @Resource
    private ForBidIPService forBidIPService;
    @Resource
    private TimeTransUtil timeTransUtil;
    @Resource
    private MD5Util md5Util;

    private ForbidIP forbidIP;
    private Article a;
    @PostMapping("/user/add")
    public Result save(@RequestBody User user){
        User userByPhonenumber = userService.findUserByPhonenumber(user.getPhonenumber());
        Optional<User> op = Optional.ofNullable(userByPhonenumber);
        if(op.isPresent()){
            return Result.failure(EMAIL_HAS_EXISTED);
        }
        user.setPassword(md5Util.md5(user.getPassword()));
        return userService.save(user);
    }
    @GetMapping("/user/select")
    public Result findpages(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10")Integer pageSize, @RequestParam(defaultValue = "")String search){
        return userService.findPage(pageNum, pageSize, search);
    }
    @PutMapping("/user/update")
    public Result update(@RequestBody User user){
        user.setPassword(md5Util.md5(user.getPassword()));
        return userService.updateById(user);
    }
    @DeleteMapping("/user/del/{id}")
    public Result delById(@PathVariable Long id){
        return userService.delById(id);
    }

    @GetMapping("/article/fp")
    public Result fp(@RequestParam(defaultValue = "1") Integer num, @RequestParam(defaultValue = "10")Integer size, @RequestParam(defaultValue = "")String search){
        Page<Article> page =  articleAndTagService.findPage_second(num, size, search);
        return Result.success(page);
    }

    @GetMapping("/article/fps")
    public Result fps(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10")Integer pageSize, @RequestParam(defaultValue = "")String search){
        Page<Article> page =  articleAndTagService.findPage_second(pageNum, pageSize, search);
        return Result.success(page);
    }

    @DeleteMapping("/article/del/{id}")
    public Result del(@PathVariable Long id){
        articleAndTagService.delArticleWithTag(id);
        return Result.success();
    }

    @GetMapping("/tags/select")
    public Result getAllTags(){
        List<Tag> allTags = articleAndTagService.getAllTags();
        return Result.success(allTags);
    }

    @PostMapping("/article/add")
    public Result addArticle(@RequestBody String info) {
        JSONObject jsonObject = JSON.parseObject(info);
        a = new Article();
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
        Date time= null;
        try {
            time = timeTransUtil.transUTCToZ(publishtime);
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

    @GetMapping("/user/info")
    public Result getUserInfo(@RequestParam String token){
        try{
            boolean flag = JwtUtil.checkSign(token);
            if(flag){
                return Result.success(JwtUtil.getInfo(token));
            }
            return Result.failure(TOKEN_INVALID);
        }catch (Exception e){
            return Result.failure(TOKEN_INVALID);
        }
    }

    @GetMapping("/ip/select")
    public Result selectIps(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10")Integer pageSize, @RequestParam(defaultValue = "")String search){
        Result pages = forBidIPService.findPages(pageNum, pageSize, search);
        return pages;
    }
    @DeleteMapping("/ip/del/{id}")
    public Result delIp(@PathVariable Long id){
        Result result = forBidIPService.del(id);
        return result;
    }
    //时间传入重构
    @PostMapping("/ip/add")
    public Result addip(@RequestBody String info){
        JSONObject jsonObject = JSON.parseObject(info);
        forbidIP = new ForbidIP();
        forbidIP.setId(System.currentTimeMillis());
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

    @PutMapping("/ip/update")
    public Result update(@RequestBody ForbidIP f){
        Result result = forBidIPService.update(f);
        return result;
    }

}
