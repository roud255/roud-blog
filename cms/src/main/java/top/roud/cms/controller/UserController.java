package top.roud.cms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.roud.cms.common.annotation.AccessIPRecord;
import top.roud.cms.common.annotation.OperationAuth;
import top.roud.cms.common.result.Result;
import top.roud.cms.common.utils.*;
import top.roud.cms.entity.User;
import top.roud.cms.entity.UserInformation;
import top.roud.cms.service.UserInformationService;
import top.roud.cms.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

import static top.roud.cms.common.result.ResultCode.*;

/**
 * @ClassName: UserController
 * @Description:
 * @Author roud
 * @Date 2022/6/8
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserInformationService userInformationService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private IPUtil ipUtil;

    @OperationAuth
    @AccessIPRecord
    @PostMapping
    public Result save(@RequestBody User user, HttpServletRequest request){
        User userByPhonenumber = userService.findUserByPhonenumber(user.getPhonenumber());
        Optional<User> op = Optional.ofNullable(userByPhonenumber);
        if(op.isPresent()){
            return Result.failure(EMAIL_HAS_EXISTED);
        }
        UserInformation userInformation = new UserInformation().setUser(user).setId(AutoIdUtil.getId()).setRecentlyip(ipUtil.getIpAddr(request));
        userInformationService.save(userInformation);
        return userService.save(user);
    }
    @AccessIPRecord
    @GetMapping
    public Result findpages(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10")Integer pageSize, @RequestParam(defaultValue = "")String search){
        return userService.findPage(pageNum, pageSize, search);
    }
    @OperationAuth
    @AccessIPRecord
    @PutMapping
    public Result update(@RequestBody User user){
        return userService.updateById(user);
    }
    @OperationAuth
    @AccessIPRecord
    @DeleteMapping("/{id}")
    public Result delById(@PathVariable Long id){
        return userService.delById(id);
    }

    @AccessIPRecord
    @PostMapping("/{id}")
    public Result updateUserInfo(@PathVariable Long id){
        return userService.delById(id);
    }

    @AccessIPRecord
    @PostMapping("/updateinfo")
    public Result updateUserInfo(@RequestBody String info,HttpServletRequest request){
        try {
            String token = tokenUtil.getToken(request);
            if (StringUtils.isBlank(token) || !JwtUtil.checkSign(token)) {
                return Result.failure(TOKEN_INVALID);
            }
            Map<String, Object> tokeninfo = JwtUtil.getInfo(token);
            Long u_id = (Long) tokeninfo.get("id");
            Integer count = (Integer) redisUtil.get(u_id + "-updateinfo");
            Optional<Integer> op = Optional.ofNullable(count);
            if(op.isPresent()){
                return Result.failure(COUNT_LIMIT);
            }
            UserInformation userInformation = userInformationService.selectByUserId(u_id);
            JSONObject jsonObject = JSON.parseObject(info);
            String sex = jsonObject.getString("sex");
            String motto = jsonObject.getString("motto");
            int s = StringUtils.equals("女",sex)?1:0;
            userInformation.setSex(s);
            userInformation.setMotto(motto);
            userInformationService.updateByUserId(userInformation);
            redisUtil.set(u_id+"-updateinfo",1,60*60*24);
            return Result.success();
        }catch (Exception e){
            return Result.failure(SYSTEM_ERROR);
        }
    }
}
