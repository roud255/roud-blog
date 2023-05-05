package top.roud.cms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.roud.cms.common.Result;
import top.roud.cms.entity.Comment;
import top.roud.cms.service.ArticleAndCommentService;
import top.roud.cms.utils.IPUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

import static top.roud.cms.common.ResultCode.PARAM_NOT_COMPLETE;

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
        Comment commentByArticle = articleAndCommentService.findCommentByArticle(id);
        return Result.success(commentByArticle);
    }

    @PostMapping
    public Result insert(@RequestBody String info, HttpServletRequest request){
        JSONObject body = JSON.parseObject(info);
        String comment = body.getString("comment");
        String ipAddr = IPUtil.getIpAddr(request);
        String from = IPUtil.getIPAddress(ipAddr);
        String to = body.getString("to");
        String article_id = body.getString("article_id");
        Comment c = new Comment();
        c.setId(System.currentTimeMillis());
        Date date = new Date();
        c.setTime(date);
        c.setContent(comment);
        c.setFrom(from);
        c.setTo(to);
        c.setArticle_id(Long.valueOf(article_id));
        return Result.failure("");
    }
}
