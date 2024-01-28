package top.roud.roudblogcms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import top.roud.roudblogcms.common.result.Result;
import top.roud.roudblogcms.common.utils.*;
import top.roud.roudblogcms.entity.User;
import top.roud.roudblogcms.entity.UserInformation;
import top.roud.roudblogcms.service.UserInformationService;
import top.roud.roudblogcms.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static top.roud.roudblogcms.common.result.ResultCode.*;
import static top.roud.roudblogcms.common.utils.ConstUtil.CAPTCHA_EMAIL_CONTACT;

/**
 * @author roud
 * @version 1.0.0
 * @describe
 * @date 2024-01-26 22:18
 */
@Service
public class RegisterService {
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

    public Result getCode(@RequestBody String info){
        JSONObject jsonObject = JSON.parseObject(info);
        String email = jsonObject.getString("email");
        String userVertifyCode = (jsonObject.getString("userVertifyCode")).toUpperCase();
        String serverVertifyCode = mailUtil.getServerVertifyCode(email);
        if(!StringUtils.equals(serverVertifyCode, userVertifyCode)){
            return Result.failure(6101, "异常请求");
        }
        String mailRandVertifyCode = mailUtil.getMailRandVertifyCode(4);
        String key = CAPTCHA_EMAIL_CONTACT + email;
        redisUtil.set(key,mailRandVertifyCode, 3, TimeUnit.MINUTES);
        String mailContent = mailUtil.getMailContent(mailRandVertifyCode);
        boolean flag = mailUtil.sendVertify(email, mailContent);
        if(flag){
            return Result.success(SEND_VERTIFYCODE_SUCCESS, flag);
        }
        return Result.failure(SYSTEM_ERROR);
    }

    public Result register(@RequestBody String info, HttpServletRequest request){
        JSONObject jsonObject = JSON.parseObject(info);
        String email = jsonObject.getString("email");
        String key = CAPTCHA_EMAIL_CONTACT + email;
        if(!redisUtil.hasKey(key)){
            return Result.failure("无效验证码");
        }
        String vertifycodeSys = redisUtil.get(key);
        String vertifycode = jsonObject.getString("vertifycode");
        if(!StringUtils.equals(vertifycode, vertifycodeSys)){
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
    }
}
