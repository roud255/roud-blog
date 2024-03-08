package top.roud.roudblogcms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.roud.roudblogcms.common.result.Result;
import top.roud.roudblogcms.common.utils.*;
import top.roud.roudblogcms.entity.User;
import top.roud.roudblogcms.entity.UserInformation;
import top.roud.roudblogcms.service.UserInformationService;
import top.roud.roudblogcms.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static top.roud.roudblogcms.common.result.ResultCode.*;
import static top.roud.roudblogcms.common.utils.ConstUtil.CAPTCHA_IMAGE_CONTACT;

/**
 * @author roud
 * @version 1.0.0
 * @describe
 * @date 2024-01-26 21:54
 */
@Service
public class LoginService {
    @Autowired
    private IPUtil ipUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private MD5Util md5Util;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserInformationService userInformationService;

    public Result login(HttpServletRequest request, String info){
        try{
            JSONObject jsonObject = JSON.parseObject(info);
            String phonenumber = jsonObject.getString("email");
            String password = jsonObject.getString("password");
            String captcha = jsonObject.getString("vertifycode");
            String flag = jsonObject.getString("flag");
            String key;
            if(StringUtils.isBlank(flag)){
                String ip = ipUtil.getIpAddr(request);
                key = CAPTCHA_IMAGE_CONTACT + ip;
            }else {
                key = CAPTCHA_IMAGE_CONTACT + flag;
            }
            String captchaSys = redisUtil.get(key);
            if(StringUtils.isBlank(captchaSys)){
                return Result.failure(CAPTCHA_TIMEOUT);
            }
            if(!StrUtil.equalsAnyIgnoreCase(captcha, captchaSys)){
                return Result.failure(CAPTCHA_ERROR);
            }
            User userByEmailAndPwd = userService.findUserByPhonenumberAndPassword(phonenumber, md5Util.md5(password));
            Optional<User> op = Optional.ofNullable(userByEmailAndPwd);
            Map<String, Object> map = new HashMap();
            if(op.isPresent()){
                UserInformation userInformation = userInformationService.selectByUserId(userByEmailAndPwd.getId());
                userInformation.setRecentlyip(ipUtil.getIpAddr(request));
                map.put("id",userByEmailAndPwd.getId());
                map.put("name", userByEmailAndPwd.getNickname());
                map.put("time", userByEmailAndPwd.getRegistertime());
                map.put("phone", userByEmailAndPwd.getPhonenumber());
                map.put("power", userByEmailAndPwd.getPower());
                map.put("type", userByEmailAndPwd.getType());
                map.put("imgurl",userInformation.getImgId()==null?"":String.valueOf(userInformation.getImgId()));
                map.put("sex",userInformation.getSex());
                map.put("motto",userInformation.getMotto()==null?"":userInformation.getMotto());
                map.put("ip",userInformation.getRecentlyip());
                String sign = tokenUtil.getOutToken(jwtUtil.sign(String.valueOf(userByEmailAndPwd.getId()), map));
                tokenUtil.relevanceAuthorAndOutToken(String.valueOf(userByEmailAndPwd.getId()), sign);
                userInformationService.updateByUserId(userInformation);
                HashMap<String, String> tokenMap = new HashMap<>(1);
                tokenMap.put("token", sign);
                return Result.success(tokenMap);
            }
            return Result.failure(USER_NOT_EXIST);
        }catch (Exception e){
            return Result.failure(SYSTEM_ERROR);
        }

    }

    public Result signOut(HttpServletRequest request){
        try{
            String token = request.getHeader("token");
            if(StringUtils.isBlank(token)){
                return Result.failure(PARAM_NOT_COMPLETE);
            }
            tokenUtil.signOutToken(token);
        }catch (Exception e){
            return Result.failure(SYSTEM_INNER_ERROR);
        }
        return Result.success();
    }

    public Result updateToken(HttpServletRequest request){
        try{
            Map<String, Object> info = tokenUtil.getUserInfoByRequest(request);
            Long u_id = (Long)info.get("id");
            UserInformation userInformation = userInformationService.selectByUserId(u_id);
            Optional<UserInformation> op = Optional.ofNullable(userInformation);
            Map<String, Object> map = new HashMap();
            if(op.isPresent()){
                User userByEmailAndPwd = userInformation.getUser();
                map.put("id",userByEmailAndPwd.getId());
                map.put("name", userByEmailAndPwd.getNickname());
                map.put("time", userByEmailAndPwd.getRegistertime());
                map.put("phone", userByEmailAndPwd.getPhonenumber());
                map.put("power", userByEmailAndPwd.getPower());
                map.put("type", userByEmailAndPwd.getType());
                map.put("imgurl",userInformation.getImgId()==null?"":String.valueOf(userInformation.getImgId()));
                map.put("sex",userInformation.getSex());
                map.put("motto",userInformation.getMotto()==null?"":userInformation.getMotto());
                map.put("ip",userInformation.getRecentlyip());
                String sign = tokenUtil.getOutToken(jwtUtil.sign(String.valueOf(userByEmailAndPwd.getId()), map));
                tokenUtil.relevanceAuthorAndOutToken(String.valueOf(userByEmailAndPwd.getId()), sign);
                HashMap<String, String> tokenMap = new HashMap<>(1);
                tokenMap.put("token", sign);
                return Result.success(tokenMap);
            }
            return Result.failure(USER_NOT_EXIST);
        }catch (Exception e){
            return Result.failure(SYSTEM_ERROR);
        }

    }
}
