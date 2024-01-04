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


    @OperationAuth
    @AccessIPRecord
    @PostMapping
    public Result save(@RequestBody User user, HttpServletRequest request){
        return userService.saveUser(user, request);
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
        return userInformationService.updateUserinfo(info, request);
    }
}
