package top.roud.roudblogcms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.roud.roudblogcms.common.result.Result;
import top.roud.roudblogcms.common.utils.AutoIdUtil;
import top.roud.roudblogcms.common.utils.CacheUtil;
import top.roud.roudblogcms.common.utils.IPUtil;
import top.roud.roudblogcms.entity.User;
import top.roud.roudblogcms.entity.UserInformation;
import top.roud.roudblogcms.mapper.UserInformationMapper;
import top.roud.roudblogcms.mapper.UserMapper;
import top.roud.roudblogcms.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static top.roud.roudblogcms.common.result.ResultCode.EMAIL_HAS_EXISTED;


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

    @Autowired
    private CacheUtil cacheUtil;
    @Override
    public Result save(User user) {
        //用户权限暂时根据type判断，power全部设置为1
        user.setPower("1");
        userMapper.insert(user);
        return Result.success();
    }

    //缓存
    @Override
    public Result findPage(Integer pageNum, Integer pageSize, String search) {
        /*缓存*/
        //        String cacheKey = USER_FINDPAGE + pageNum + "." + pageSize + "." + search;
        //        if(cacheUtil.hasKey(cacheKey)){
        //            return JSONObject.parseObject(cacheUtil.getByCache(cacheKey), Result.class);
        //        }

        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(User::getNickname, search);
        }
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        /*缓存*/
        //        cacheUtil.putToCache(cacheKey, Result.success(userPage), 1, TimeUnit.DAYS);

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
        /*缓存*/
        //        String cacheKey = USER_PHONENUM_PASSWORD_CONTACT + phonenumber + "." + password;
        //        if(cacheUtil.hasKey(cacheKey)){
        //            return JSONObject.parseObject(cacheUtil.getByCache(cacheKey), User.class);
        //        }

        LambdaQueryWrapper<User> lqw = Wrappers.<User>lambdaQuery();
        lqw.eq(User::getPhonenumber, phonenumber).eq(User::getPassword, password);
        User user = userMapper.selectOne(lqw);
        /*缓存*/
        //        cacheUtil.putToCache(cacheKey, user, 1, TimeUnit.MINUTES);

        return user;
    }

    @Override
    public User findUserByPhonenumber(String phonenumber) {
        /*缓存*/
        //        String cacheKey = USER_PHONENUM_CONTACT + phonenumber;
        //        if(cacheUtil.hasKey(cacheKey)){
        //            return JSONObject.parseObject(cacheUtil.getByCache(cacheKey), User.class);
        //        }

        LambdaQueryWrapper<User> lqw = Wrappers.<User>lambdaQuery();
        lqw.eq(User::getPhonenumber, phonenumber);
        User user = userMapper.selectOne(lqw);
        /*缓存*/
        //         cacheUtil.putToCache(cacheKey, user, 1, TimeUnit.MINUTES);

        return user;
    }

    @Override
    public Result saveUser(User user, HttpServletRequest request) {
        LambdaQueryWrapper<User> lqw = Wrappers.<User>lambdaQuery();
        lqw.eq(User::getPhonenumber, user.getPhonenumber());
        User userByPhonenumber = userMapper.selectOne(lqw);
        Optional<User> op = Optional.ofNullable(userByPhonenumber);
        if(op.isPresent()){
            return Result.failure(EMAIL_HAS_EXISTED);
        }
        UserInformation userInformation = new UserInformation().setUser(user).setId(AutoIdUtil.getId()).setRecentlyip(ipUtil.getIpAddr(request));
        userInformationMapper.insert(userInformation);
        return save(user);
    }

}
