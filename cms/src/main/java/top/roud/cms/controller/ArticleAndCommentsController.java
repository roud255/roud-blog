package top.roud.cms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.roud.cms.common.Result;
import top.roud.cms.entity.Comment;
import top.roud.cms.service.ArticleAndCommentService;

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
    public Result insert(@RequestBody String info){
        JSONObject body = JSON.parseObject(info);
        return Result.failure("");
    }
}
