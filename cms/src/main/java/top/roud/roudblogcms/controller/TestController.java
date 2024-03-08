package top.roud.roudblogcms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.roud.roudblogcms.common.result.Result;
import top.roud.roudblogcms.common.result.ResultCode;
import top.roud.roudblogcms.common.utils.CacheUtil;
import top.roud.roudblogcms.common.utils.RedisUtil;
import top.roud.roudblogcms.common.utils.TaskExecutePoolUtil;
import top.roud.roudblogcms.entity.Tag;
import top.roud.roudblogcms.service.ArticleAndTagService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

import static top.roud.roudblogcms.common.utils.ConstUtil.ARTICLE_COMMENTCOUNT;
import static top.roud.roudblogcms.common.utils.ConstUtil.ARTICLE_COMMENT_CACHE;


/**
 * @description : TODO
 * @author: guangrui_hu
 * @date: 2023/5/24
 * @version:
 */
@ApiOperation("测试接口")
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CacheUtil cacheUtil;

    @Autowired
    private ArticleAndTagService articleAndTagService;

    @Autowired
    private TaskExecutePoolUtil taskExecutePoolUtil;
    @GetMapping("/clearAllCache")
    public Result clearAllCache(HttpServletRequest request){
        String sign = request.getParameter("sign");
        if(!StringUtils.equals(sign,  "ROUDBLOGADMIN")){
            return Result.failure(ResultCode.VALIDATEF_FAIL);
        }
        redisUtil.deleteAllByPattern("*");
        return Result.success();
    }

    @GetMapping("/getAllKeys")
    public Result getAllKeys(HttpServletRequest request){
        String sign = request.getParameter("sign");
        String p = request.getParameter("p");
        if(!StringUtils.equals(sign,  "ROUDBLOGADMIN")){
            return Result.failure(ResultCode.VALIDATEF_FAIL);
        }
        Set<String> keysByPattern = redisUtil.getKeysByPattern(p);
        return Result.success(keysByPattern);
    }

//    @GetMapping("/getTags")
//    public Result getTags(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10")Integer pageSize, @RequestParam(defaultValue = "")String search){
//        articleAndTagService.findTagsPage(pageNum, pageSize, search)
//        return Result.success(tagsPage);
//    }

}
