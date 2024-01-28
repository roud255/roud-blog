package top.roud.roudblogcms.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import top.roud.roudblogcms.common.annotation.AccessIPRecord;
import top.roud.roudblogcms.common.annotation.NoRepeatRequest;
import top.roud.roudblogcms.common.result.Result;
import top.roud.roudblogcms.service.impl.ImageService;
import top.roud.roudblogcms.service.impl.LoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: LoginController
 * @Description:
 * @Author roud
 * @Date 2022/6/15
 * @Version 1.0
 */
@Api(tags = "登录接口")
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private ImageService imageService;

    @Autowired
    private LoginService loginService;
    @ApiOperation("获取登录图片验证码")
    @AccessIPRecord
    @GetMapping(produces = MediaType.IMAGE_PNG_VALUE)
    public Result getCaptcha(HttpServletRequest request, HttpServletResponse response){
        String flag = request.getParameter("flag");
        return imageService.getCaptchaImage(request, response, flag);
    }

    @ApiOperation("请求登录")
    @NoRepeatRequest(seconds = 10, maxCount = 3)
    @AccessIPRecord
    @PostMapping
    public Result login(HttpServletRequest request,@RequestBody String info){
        return loginService.login(request, info);
    }

    @ApiOperation("请求退出登录")
    @NoRepeatRequest(seconds = 10, maxCount = 3)
    @AccessIPRecord
    @GetMapping("/quit")
    public Result signOut(HttpServletRequest request){
        return loginService.signOut(request);
    }

    @ApiOperation("更新token")
    @AccessIPRecord
    @GetMapping("/updatetoken")
    public Result updateToken(HttpServletRequest request){
        return loginService.updateToken(request);
    }
}
