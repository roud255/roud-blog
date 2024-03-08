package top.roud.roudblogcms.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.roud.roudblogcms.common.annotation.AccessIPRecord;
import top.roud.roudblogcms.common.annotation.OperationAuth;
import top.roud.roudblogcms.common.result.Result;
import top.roud.roudblogcms.entity.User;
import top.roud.roudblogcms.service.UserInformationService;

import javax.servlet.http.HttpServletRequest;


/**
 * @ClassName: UserController
 * @Description:
 * @Author roud
 * @Date 2022/6/8
 * @Version 1.0
 */
@Api(tags = "用户相关接口")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserInformationService userInformationService;
    @ApiOperation("更新用户信息-用户自己修改")
    @AccessIPRecord
    @PostMapping("/updateinfo")
    public Result updateUserInfo(@RequestBody String info,HttpServletRequest request){
        return userInformationService.updateUserInfo(info, request);
    }
}
