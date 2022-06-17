package top.roud.cms.controller;

import top.roud.cms.common.Result;
import top.roud.cms.entity.User;
import top.roud.cms.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

import static top.roud.cms.common.ResultCode.EMAIL_HAS_EXISTED;

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
    @Resource
    private UserServiceImpl userService;
    @PostMapping
    public Result save(@RequestBody User user){
        User userByPhonenumber = userService.findUserByPhonenumber(user.getPhonenumber());
        Optional<User> op = Optional.ofNullable(userByPhonenumber);
        if(op.isPresent()){
            return Result.failure(EMAIL_HAS_EXISTED);
        }
        return userService.save(user);
    }
    @GetMapping
    public Result findpages(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10")Integer pageSize, @RequestParam(defaultValue = "")String search){
        return userService.findPage(pageNum, pageSize, search);
    }
    @PutMapping
    public Result update(@RequestBody User user){
        return userService.updateById(user);
    }
    @DeleteMapping("/{id}")
    public Result delById(@PathVariable Long id){
        return userService.delById(id);
    }
}
