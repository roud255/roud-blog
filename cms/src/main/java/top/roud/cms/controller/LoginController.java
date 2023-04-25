package top.roud.cms.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.web.bind.annotation.*;
import top.roud.cms.common.Result;
import top.roud.cms.common.annotation.AccessIPRecord;
import top.roud.cms.common.annotation.NoRepeatRequest;
import top.roud.cms.entity.User;
import top.roud.cms.service.UserService;
import top.roud.cms.utils.JwtUtil;
import top.roud.cms.utils.MD5Util;
import top.roud.cms.utils.RedisUtil;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static top.roud.cms.common.ResultCode.*;
import static top.roud.cms.utils.CaptchaUtil.doDraw;
import static top.roud.cms.utils.CaptchaUtil.getCode;

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
    @Resource
    private UserService userService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private MD5Util md5Util;
    @AccessIPRecord
    @GetMapping
    public Result getCaptcha(HttpServletRequest request, HttpServletResponse response){
        try{
            int imgWidth = 108;
            int imgHeight = 36;
            int interferenceLineCount = 6;

            response.setContentType("image/jpg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", new Date().getTime());
            String str = getCode();
            String ip = request.getRemoteAddr();
            redisUtil.set("ip-"+ip+"-captcha",str,60);
            BufferedImage img = doDraw(str,imgWidth, imgHeight, interferenceLineCount);
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(img, "JPG", out);
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
        JSONObject jsonObject = JSON.parseObject(info);
        String phonenumber = jsonObject.getString("email");
        String password = jsonObject.getString("password");
        String captcha = jsonObject.getString("vertifycode");
        String ip = request.getRemoteAddr();
        String captcha_sys = (String) redisUtil.get("ip-"+ip+"-captcha");
        if(captcha_sys==null){
            return Result.failure(CAPTCHA_TIMEOUT);
        }
        if(!StrUtil.equals(captcha.toUpperCase(), captcha_sys.toUpperCase())){
            return Result.failure(CAPTCHA_ERROR);
        }
        User byEmailAndPwd = userService.findUserByPhonenumberAndPassword(phonenumber, md5Util.md5(password));
        Optional<User> op = Optional.ofNullable(byEmailAndPwd);
        Map<String, Object> map = new HashMap();
        if(op.isPresent()){
            map.put("name", byEmailAndPwd.getNickname());
            map.put("time", byEmailAndPwd.getRegistertime());
            map.put("phone", byEmailAndPwd.getPhonenumber());
            map.put("power", byEmailAndPwd.getPower());
            map.put("type", byEmailAndPwd.getType());
            String sign = JwtUtil.sign(String.valueOf(byEmailAndPwd.getId()), map);
            return Result.success(new Token(sign));
        }
        return Result.failure(USER_NOT_EXIST);
    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    class Token{
        private String token;
    }
}
