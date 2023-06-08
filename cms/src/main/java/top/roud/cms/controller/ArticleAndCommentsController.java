package top.roud.cms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.roud.cms.common.result.Result;
import top.roud.cms.common.annotation.CommentAuth;
import top.roud.cms.common.utils.ConstUtil;
import top.roud.cms.common.utils.RedisUtil;
import top.roud.cms.entity.Comment;
import top.roud.cms.service.ArticleAndCommentService;
import top.roud.cms.common.utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static top.roud.cms.common.result.ResultCode.*;

/**
 * @description : TODO
 * @author: guangrui_hu
 * @date: 2023/5/4
 * @version:
 */
@RestController
@RequestMapping("/aac")
public class ArticleAndCommentsController {
    @Autowired
    private ArticleAndCommentService articleAndCommentService;
    @Autowired
    private RedisUtil redisUtil;
    @RequestMapping
    public Result selectComments(@RequestParam Long id){
        Optional<Long> op = Optional.ofNullable(id);
        if(!op.isPresent()){
            return Result.failure(PARAM_NOT_COMPLETE);
        }
        String key = ConstUtil.REDIS_COMMENTS_KEY+id;
        String cacheStr = (String)redisUtil.get(key);
        if(StringUtils.isNotBlank(cacheStr)){
            List<Comment> commentsByArticle = JSON.parseObject(cacheStr, List.class);
            return Result.success(commentsByArticle);
        }
        List<Comment> commentsByArticle = articleAndCommentService.findCommentByArticle(id);
        redisUtil.set(key, JSON.toJSONString(commentsByArticle), 10, TimeUnit.MINUTES);
        return Result.success(commentsByArticle);
    }

    @CommentAuth
    @PostMapping
    public Result insert(@RequestBody String info, HttpServletRequest request){
        String token = request.getHeader("token");
        boolean flag = JwtUtil.checkSign(token);
        if(!flag){
            return Result.failure(TOKEN_INVALID);
        }
        JSONObject body = JSON.parseObject(info);
        String comment = body.getString("comment");
        Map<String, Object> info_u = JwtUtil.getInfo(token);
        String from = (String) info_u.get("name");
        String to = body.getString("to");
        String p_id = body.getString("parent_id");
        String article_id = body.getString("article_id");
        String headimg = body.getString("headimg");
        String sex = body.getString("sex");
        String motto = body.getString("motto");
        String email = body.getString("email");
        Comment c = new Comment();
        c.setId(System.currentTimeMillis());
        Date date = new Date();
        c.setOp_time(date);
        c.setContent(comment);
        c.setFrom_name(from);
        c.setTo_name(to);
        c.setParent_id(Long.valueOf(p_id));
        c.setArticle_id(Long.valueOf(article_id));
        c.setHeadimg(headimg);
        c.setSex(sex);
        c.setMotto(motto);
        c.setEmail(email);
        Integer res = articleAndCommentService.addComment(c);
        if(1==res){
            return Result.success();
        }
        return Result.failure(SYSTEM_ERROR);
    }
}
