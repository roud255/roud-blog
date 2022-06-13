package top.roud.cms.controller;

import top.roud.cms.common.Result;
import top.roud.cms.entity.User;
import top.roud.cms.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
    public Result delbyid(@PathVariable Long id){
        return userService.delById(id);
    }
}
