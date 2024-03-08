package top.roud.roudblogcms.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.roud.roudblogcms.common.annotation.AccessIPRecord;
import top.roud.roudblogcms.common.annotation.NoRepeatRequest;
import top.roud.roudblogcms.common.result.Result;
import top.roud.roudblogcms.service.impl.RegisterService;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: RegisterController
 * @Description:
 * @Author roud
 * @Date 2022/6/13
 * @Version 1.0
 */
@Api(tags = "注册接口")
@RestController
@RequestMapping("/reg")
public class RegisterController {

    @Autowired
    private RegisterService registerService;
    @ApiOperation("获取邮箱验证码")
    @NoRepeatRequest(seconds = 60, maxCount = 1)
    @AccessIPRecord
    @PostMapping("/code")
    public Result getCode(@RequestBody String info){
        return registerService.getCode(info);

    }

    @ApiOperation("请求注册")
    @AccessIPRecord
    @PostMapping("/do")
    public Result register(@RequestBody String info, HttpServletRequest request){
        return registerService.register(info, request);
    }
}
