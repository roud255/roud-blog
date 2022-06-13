package top.roud.cms.controller;

import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.*;
import top.roud.cms.common.Result;
import top.roud.cms.entity.EmailUser;
import top.roud.cms.service.impl.UserServiceImpl;
import top.roud.cms.utils.MailUtil;

import javax.annotation.Resource;

import static top.roud.cms.common.ResultCode.SYSTEM_INNER_ERROR;

/**
 * @ClassName: RegisterController
 * @Description:
 * @Author roud
 * @Date 2022/6/13
 * @Version 1.0
 */
@RestController
@RequestMapping("/reg")
public class RegisterController {
    @Resource
    private UserServiceImpl userService;
    @PostMapping("/code")
    public Result getCode(@RequestBody EmailUser emailUser){
        String email = emailUser.getEmail();
        String userVertifyCode = (emailUser.getUserVertifyCode()).toUpperCase();
        String serverVertifyCode = MailUtil.getServerVertifyCode(email);
        if(!StrUtil.equals(serverVertifyCode, userVertifyCode)){
            return Result.failure(6101, "异常请求");
        }
        try{
            String mailRandVertifyCode = MailUtil.getMailRandVertifyCode(4);
            String mailContent = MailUtil.getMailContent(mailRandVertifyCode);
            MailUtil.sendVertify(email, mailContent);
            return Result.success(email);
        }catch(Exception e){
            return Result.failure(e.getMessage());
        }
    }
}
