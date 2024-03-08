package top.roud.roudblogcms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.roud.roudblogcms.common.annotation.CommentAuth;
import top.roud.roudblogcms.common.result.Result;
import top.roud.roudblogcms.common.result.ResultCode;
import top.roud.roudblogcms.service.ArticleAndCommentService;

import javax.servlet.http.HttpServletRequest;

/**
 * @description : TODO
 * @author: guangrui_hu
 * @date: 2023/5/4
 * @version:
 */
@Api(tags = "文章与评论接口")
@RestController
@RequestMapping("/aac")
public class ArticleAndCommentsController {
    @Autowired
    private ArticleAndCommentService articleAndCommentService;
    @ApiOperation("根据文章id查找评论")
    @RequestMapping
    public Result selectComments(@RequestParam Long id){
        return articleAndCommentService.selectCommentsByArticleId(id);
    }

    @ApiOperation("添加评论")
    @CommentAuth(dailyMaxCount = 100)
    @PostMapping
    public Result insert(@RequestBody String info, HttpServletRequest request){
        String articleId = JSON.parseObject(info).getString("articleId");
        if(StringUtils.isBlank(articleId)){
            return Result.failure(ResultCode.PARAM_NOT_COMPLETE);
        }
        return articleAndCommentService.saveComment(info, articleId, request);
    }
}
