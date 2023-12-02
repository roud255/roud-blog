package top.roud.cms.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import top.roud.cms.common.annotation.AccessIPRecord;
import top.roud.cms.common.annotation.NoRepeatRequest;
import top.roud.cms.common.result.Result;
import top.roud.cms.common.utils.*;
import top.roud.cms.entity.User;
import top.roud.cms.entity.UserInformation;
import top.roud.cms.service.UserInformationService;
import top.roud.cms.service.UserService;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static top.roud.cms.common.result.ResultCode.*;
import static top.roud.cms.common.utils.CaptchaUtil.doDraw;

/**
 * @ClassName: LoginController
 * @Description:
 * @Author roud
 * @Date 2022/6/15
 * @Version 1.0
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private MD5Util md5Util;
    @Autowired
    private UserInformationService userInformationService;
    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private IPUtil ipUtil;

    @AccessIPRecord
    @GetMapping(produces = MediaType.IMAGE_PNG_VALUE)
    public Result getCaptcha(HttpServletRequest request, HttpServletResponse response){
        try{
            int imgWidth = 108;
            int imgHeight = 36;
            int interferenceLineCount = 6;

            response.setContentType("image/jpg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", new Date().getTime());
            String str = RandomCodeUtil.getRandomCode(4);
            String ip = ipUtil.getIpAddr(request);
            redisUtil.set("ip-"+ip+"-captcha",str,60);
            BufferedImage img = doDraw(str,imgWidth, imgHeight, interferenceLineCount);
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(img, "PNG", out);
            out.flush();
            out.close();
            return null;
        }catch (Exception e){
            return Result.failure(e.getMessage());
        }
    }
    @NoRepeatRequest(seconds = 10, maxCount = 3)
    @AccessIPRecord
    @PostMapping
    public Result login(HttpServletRequest request,@RequestBody String info){
        try{
            JSONObject jsonObject = JSON.parseObject(info);
            String phonenumber = jsonObject.getString("email");
            String password = jsonObject.getString("password");
            String captcha = jsonObject.getString("vertifycode");
            String ip = ipUtil.getIpAddr(request);
            String captcha_sys = (String) redisUtil.get("ip-"+ip+"-captcha");
            if(StringUtils.isBlank(captcha_sys)){
                return Result.failure(CAPTCHA_TIMEOUT);
            }
            if(!StrUtil.equals(captcha.toUpperCase(), captcha_sys.toUpperCase())){
                return Result.failure(CAPTCHA_ERROR);
            }
            User byEmailAndPwd = userService.findUserByPhonenumberAndPassword(phonenumber, md5Util.md5(password));
            Optional<User> op = Optional.ofNullable(byEmailAndPwd);
            Map<String, Object> map = new HashMap();
            if(op.isPresent()){
                UserInformation userInformation = userInformationService.selectByUserId(byEmailAndPwd.getId());
                userInformation.setRecentlyip(ipUtil.getIpAddr(request));
                map.put("id",byEmailAndPwd.getId());
                map.put("name", byEmailAndPwd.getNickname());
                map.put("time", byEmailAndPwd.getRegistertime());
                map.put("phone", byEmailAndPwd.getPhonenumber());
                map.put("power", byEmailAndPwd.getPower());
                map.put("type", byEmailAndPwd.getType());
                map.put("imgurl",userInformation.getImg_id()==null?"":String.valueOf(userInformation.getImg_id()));
                map.put("sex",userInformation.getSex());
                map.put("motto",userInformation.getMotto()==null?"":userInformation.getMotto());
                map.put("ip",userInformation.getRecentlyip());
                String sign = tokenUtil.getOutToken(JwtUtil.sign(String.valueOf(byEmailAndPwd.getId()), map));
                userInformationService.updateByUserId(userInformation);
                return Result.success(new Token(sign));
            }
            return Result.failure(USER_NOT_EXIST);
        }catch (Exception e){
            return Result.failure(SYSTEM_ERROR);
        }

    }

    @NoRepeatRequest(seconds = 10, maxCount = 3)
    @AccessIPRecord
    @GetMapping("/quit")
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

    @AccessIPRecord
    @GetMapping("/updatetoken")
    public Result updateToken(HttpServletRequest request){
        try{
            String token = tokenUtil.getToken(request);
            if(!JwtUtil.checkSign(token)){
                return Result.failure(TOKEN_INVALID);
            }
            Map<String, Object> info = JwtUtil.getInfo(token);
            Long u_id = (Long)info.get("id");
            UserInformation userInformation = userInformationService.selectByUserId(u_id);
            Optional<UserInformation> op = Optional.ofNullable(userInformation);
            Map<String, Object> map = new HashMap();
            if(op.isPresent()){
                User byEmailAndPwd = userInformation.getUser();
                map.put("id",byEmailAndPwd.getId());
                map.put("name", byEmailAndPwd.getNickname());
                map.put("time", byEmailAndPwd.getRegistertime());
                map.put("phone", byEmailAndPwd.getPhonenumber());
                map.put("power", byEmailAndPwd.getPower());
                map.put("type", byEmailAndPwd.getType());
                map.put("imgurl",userInformation.getImg_id()==null?"":String.valueOf(userInformation.getImg_id()));
                map.put("sex",userInformation.getSex());
                map.put("motto",userInformation.getMotto()==null?"":userInformation.getMotto());
                map.put("ip",userInformation.getRecentlyip());
                String sign = tokenUtil.getOutToken(JwtUtil.sign(String.valueOf(byEmailAndPwd.getId()), map));
                return Result.success(new Token(sign));
            }
            return Result.failure(USER_NOT_EXIST);
        }catch (Exception e){
            return Result.failure(SYSTEM_ERROR);
        }

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    class Token{
        private String token;
    }
}
