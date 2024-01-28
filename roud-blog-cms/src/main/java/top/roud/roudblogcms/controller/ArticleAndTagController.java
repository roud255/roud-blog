package top.roud.roudblogcms.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.roud.roudblogcms.common.annotation.AccessIPRecord;
import top.roud.roudblogcms.common.annotation.OperationAuth;
import top.roud.roudblogcms.common.result.Result;
import top.roud.roudblogcms.entity.Article;
import top.roud.roudblogcms.entity.Tag;
import top.roud.roudblogcms.service.ArticleAndTagService;
import top.roud.roudblogcms.service.SelfArticleValidateService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @ClassName: ArticleAndTagController
 * @Description:
 * @Author roud
 * @Date 2022/9/3
 * @Version 1.0
 */
@Api(tags = "文章和标签接口")
@RestController
@RequestMapping("/aat")
public class ArticleAndTagController {
    @Autowired
    private ArticleAndTagService articleAndTagService;
    @Autowired
    private SelfArticleValidateService selfArticleValidateService;

    @ApiOperation("添加文章与标签")
    @OperationAuth
    @AccessIPRecord
    @PostMapping("/add")
    public Result addArticle(@RequestBody String info) {
        return articleAndTagService.saveArticle(info);
    }

    @ApiOperation("根据文章id查找文章及标签")
    @AccessIPRecord
    @GetMapping("/getArticleById")
    public Result getArticleById(Long id, HttpServletRequest request){
        String validateCode = request.getParameter("validateCode");
        return articleAndTagService.getArticleById(id, validateCode);
    }

    @ApiOperation("获取所有标签")
    @AccessIPRecord
    @GetMapping("/getAllTags")
    public Result getAllTags(){
        List<Tag> allTags = articleAndTagService.getAllTags();
        return Result.success(allTags);
    }

    @ApiOperation("获取所有文章及其标签")
    @AccessIPRecord
    @GetMapping("/getAllArticleWithTags")
    public Result getAllArticleWithTags(){
        List<Article> allArticleWithTags = articleAndTagService.getAllArticleWithTag();
        return Result.success(allArticleWithTags);
    }

    @ApiOperation("分页获取文章及标签")
    @AccessIPRecord
    @GetMapping("fp")
    public Result fp(@RequestParam(defaultValue = "1") Integer num, @RequestParam(defaultValue = "10")Integer size, @RequestParam(defaultValue = "")String search, @RequestParam(defaultValue = "1")String type){
        return null;
    }


    /**
     * 主页分页查询，type为2时候通过标签查询
     * findPage_second为可以模糊查询的分页查询
     * public为公共对外接口，不返回文章内容这样大的数据
     * @param num
     * @param size
     * @param search
     * @param type
     * @return
     */
    @ApiOperation("分页获取文章及标签-公共对外")
    @AccessIPRecord
    @GetMapping("fp/public")
    public Result fpWithoutBody(@RequestParam(defaultValue = "1") Integer num, @RequestParam(defaultValue = "10")Integer size, @RequestParam(defaultValue = "")String search, @RequestParam(defaultValue = "1")String type){
        return articleAndTagService.findPageWithoutBody(num, size, search, type);
    }

    @ApiOperation("删除文章及标签")
    @OperationAuth
    @AccessIPRecord
    @DeleteMapping("/del/{id}")
    public Result del(@PathVariable Long id){
        return null;
    }

    @ApiOperation("是否专属文章")
    @AccessIPRecord
    @GetMapping("/selfArticle/validate")
    public Result validateSelfArticle(@RequestParam("articleId") Long articleId, @RequestParam("validateCode")String validateCode){
        return selfArticleValidateService.validateSelfArticle(articleId, validateCode);
    }
}
