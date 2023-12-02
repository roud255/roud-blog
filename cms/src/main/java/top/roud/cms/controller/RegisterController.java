package top.roud.cms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.roud.cms.common.result.Result;
import top.roud.cms.common.annotation.AccessIPRecord;
import top.roud.cms.common.annotation.NoRepeatRequest;
import top.roud.cms.common.utils.*;
import top.roud.cms.entity.User;
import top.roud.cms.entity.UserInformation;
import top.roud.cms.service.UserInformationService;
import top.roud.cms.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

import static top.roud.cms.common.result.ResultCode.*;

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
    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private MailUtil mailUtil;
    @Autowired
    private MD5Util md5Util;
    @Autowired
    private UserInformationService userInformationService;

    @Autowired
    private IPUtil ipUtil;

    @NoRepeatRequest(seconds = 60, maxCount = 1)
    @AccessIPRecord
    @PostMapping("/code")
    public Result getCode(@RequestBody String info){
        JSONObject jsonObject = JSON.parseObject(info);
        String email = jsonObject.getString("email");
        String userVertifyCode = (jsonObject.getString("userVertifyCode")).toUpperCase();
        String serverVertifyCode = mailUtil.getServerVertifyCode(email);
        if(!StringUtils.equals(serverVertifyCode, userVertifyCode)){
            return Result.failure(6101, "异常请求");
        }
        String mailRandVertifyCode = mailUtil.getMailRandVertifyCode(4);
        redisUtil.set(email+"vertifycode", mailRandVertifyCode,3*60);
        String mailContent = mailUtil.getMailContent(mailRandVertifyCode);
        boolean s = mailUtil.sendVertify(email, mailContent);
        if(s){
            return Result.success(SEND_VERTIFYCODE_SUCCESS,s);
        }
        return Result.failure(SYSTEM_ERROR);
//        return Result.failure(ResultCode.REGISTER_CLOSED, "系统未开放注册功能，请联系管理员。");
    }
    @AccessIPRecord
    @PostMapping("/do")
    public Result register(@RequestBody String info, HttpServletRequest request){
        JSONObject jsonObject = JSON.parseObject(info);
        String email = jsonObject.getString("email");
        String vertifycode_sys = (String) redisUtil.get(email+"vertifycode");
        Optional<String> op = Optional.ofNullable(vertifycode_sys);
        if(!op.isPresent()){
            return Result.failure("无效验证码");
        }
        String vertifycode = jsonObject.getString("vertifycode");
        if(!StringUtils.equals(vertifycode, vertifycode_sys)){
            return Result.failure(CAPTCHA_ERROR);
        }
        //邮箱重复校验
        User user = userService.findUserByPhonenumber(email);
        Optional<User> op_user = Optional.ofNullable(user);
        if(op_user.isPresent()){
            return Result.failure(EMAIL_HAS_EXISTED);
        }
        String nickname = jsonObject.getString("nickname");
        String password = jsonObject.getString("password");
        user = new User();
        user.setId(AutoIdUtil.getId());
        user.setNickname(nickname);
        user.setPassword(md5Util.md5(password));
        user.setPhonenumber(email);
        user.setRegistertime(new Date());
        user.setType(1);
        user.setPower("1");
        UserInformation userInformation = new UserInformation().setUser(user).setId(AutoIdUtil.getId()).setRecentlyip(ipUtil.getIpAddr(request));
        userInformationService.save(userInformation);
        return userService.save(user);
//        return Result.failure(ResultCode.REGISTER_CLOSED, "系统未开放注册功能，请联系管理员。");
    }
}
