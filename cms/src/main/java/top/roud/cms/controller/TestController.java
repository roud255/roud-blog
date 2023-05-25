package top.roud.cms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.roud.cms.common.result.Result;
import top.roud.cms.service.ArticleAndCommentService;
import top.roud.cms.service.UserInformationService;
import top.roud.cms.service.impl.ArticleAndCommentServiceImpl;

import javax.annotation.Resource;

/**
 * @description : TODO
 * @author: guangrui_hu
 * @date: 2023/5/24
 * @version:
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    private UserInformationService userInformationService;
    @GetMapping("/test/{id}")
    public Result test(@PathVariable(value = "id") Long id){
        return Result.success(userInformationService.selectByUserId(id));
    }
    @GetMapping("/testa")
    public Result testa(){
        return Result.success();
    }
}
