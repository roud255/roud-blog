package top.roud.cms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.roud.cms.common.result.Result;
import top.roud.cms.common.annotation.CommentAuth;
import top.roud.cms.entity.Comment;
import top.roud.cms.service.ArticleAndCommentService;
import top.roud.cms.common.utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    @RequestMapping
    public Result selectComments(@RequestParam Long id){
        Optional<Long> op = Optional.ofNullable(id);
        if(!op.isPresent()){
            return Result.failure(PARAM_NOT_COMPLETE);
        }
        List<Comment> commentsByArticle = articleAndCommentService.findCommentByArticle(id);
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
        Integer res = articleAndCommentService.addComment(c);
        if(1==res){
            return Result.success();
        }
        return Result.failure(SYSTEM_ERROR);
    }
}
