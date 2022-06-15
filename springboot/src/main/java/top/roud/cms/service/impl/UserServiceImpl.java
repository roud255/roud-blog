package top.roud.cms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.roud.cms.common.Result;
import top.roud.cms.entity.User;
import top.roud.cms.mapper.UserMapper;
import top.roud.cms.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName: UserServiceImpl
 * @Description:
 * @Author roud
 * @Date 2022/6/8
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public Result save(User user) {
        userMapper.insert(user);
        return Result.success();
    }

    @Override
    public Result findPage(Integer pageNum, Integer pageSize, String search) {
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(User::getNickname, search);
        }
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(userPage);
    }

    @Override
    public Result updateById(User user) {
        userMapper.updateById(user);
        return Result.success();
    }

    @Override
    public Result delById(Long id) {
        userMapper.deleteById(id);
        return Result.success();
    }

    @Override
    public User findUserByPhonenumberAndPassword(String phonenumber, String password) {
        return userMapper.selectUserByPhonenumberAndPassword(phonenumber, password);
    }

}
