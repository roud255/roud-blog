package top.roud.cms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.roud.cms.common.result.Result;
import top.roud.cms.common.utils.AutoIdUtil;
import top.roud.cms.common.utils.IPUtil;
import top.roud.cms.entity.User;
import top.roud.cms.entity.UserInformation;
import top.roud.cms.mapper.UserInformationMapper;
import top.roud.cms.mapper.UserMapper;
import top.roud.cms.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static top.roud.cms.common.result.ResultCode.EMAIL_HAS_EXISTED;

/**
 * @ClassName: UserServiceImpl
 * @Description:
 * @Author roud
 * @Date 2022/6/8
 * @Version 1.0
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserInformationMapper userInformationMapper;
    @Autowired
    private IPUtil ipUtil;
    @Override
    public Result save(User user) {
        //用户权限暂时根据type判断，power全部设置为1
        user.setPower("1");

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

    @Override
    public User findUserByPhonenumber(String phonenumber) {
        return userMapper.selectUserByPhonenumber(phonenumber);
    }

    @Override
    public Result saveUser(User user, HttpServletRequest request) {
        User userByPhonenumber = userMapper.selectUserByPhonenumber(user.getPhonenumber());
        Optional<User> op = Optional.ofNullable(userByPhonenumber);
        if(op.isPresent()){
            return Result.failure(EMAIL_HAS_EXISTED);
        }
        UserInformation userInformation = new UserInformation().setUser(user).setId(AutoIdUtil.getId()).setRecentlyip(ipUtil.getIpAddr(request));
        userInformationMapper.insert(userInformation);
        return save(user);
    }

}
