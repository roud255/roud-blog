package top.roud.roudblogcms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.roud.roudblogcms.common.result.Result;
import top.roud.roudblogcms.common.utils.CacheUtil;
import top.roud.roudblogcms.common.utils.ConstUtil;
import top.roud.roudblogcms.common.utils.RedisUtil;
import top.roud.roudblogcms.common.utils.VisitControlUtil;
import top.roud.roudblogcms.entity.ForbidIP;
import top.roud.roudblogcms.mapper.ForBidIPMapper;
import top.roud.roudblogcms.service.ForBidIPService;

import java.util.Optional;
import java.util.concurrent.TimeUnit;


/**
 * @ClassName: ForbidIPServiceImpl
 * @Description:
 * @Author roud
 * @Date 2022/9/28
 * @Version 1.0
 */
@Transactional
@Service
public class ForbidIPServiceImpl implements ForBidIPService {
    @Autowired
    private ForBidIPMapper forBidIPMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private VisitControlUtil visitControlUtil;
    @Override
    public Result save(ForbidIP forbidIP) {
        int insert = forBidIPMapper.insert(forbidIP);
        if(insert != 1){
            return Result.failure("保存失败！");
        }
        visitControlUtil.ban(forbidIP.getIp(), forbidIP.getSeconds());
        return Result.success();
    }

    @Override
    public ForbidIP selectForBidIPByIp(String ip) {
        LambdaQueryWrapper<ForbidIP> wrapper = Wrappers.<ForbidIP>lambdaQuery();
        wrapper.eq(ForbidIP::getIp, ip);
        return forBidIPMapper.selectOne(wrapper);
    }

    @Override
    public Result del(Long id) {
        ForbidIP forbidIP = forBidIPMapper.selectById(id);
        int i = forBidIPMapper.deleteById(id);
        if(i != 1){
            return Result.failure("删除失败！");
        }
        redisUtil.delete(ConstUtil.SYS_PREVENT_VIOLENT_REQUESTS+forbidIP.getIp());
        visitControlUtil.removeIP(forbidIP.getIp());
        return Result.success();
    }

    @Override
    public Result findPages(Integer pageNum, Integer pageSize, String search) {
        LambdaQueryWrapper<ForbidIP> wrapper = Wrappers.<ForbidIP>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(ForbidIP::getIp, search);
        }
        Page<ForbidIP> page = forBidIPMapper.selectPage(new Page(pageNum, pageSize), wrapper);
        return Result.success(page);
    }

    @Override
    public Result update(ForbidIP forbidIP) {
        int i = forBidIPMapper.updateById(forbidIP);
        if(i != 1){
            return Result.failure("更新失败！");
        }
        redisUtil.delete(ConstUtil.SYS_PREVENT_VIOLENT_REQUESTS+forbidIP.getIp());
        visitControlUtil.removeIP(forbidIP.getIp());
        visitControlUtil.ban(forbidIP.getIp(), forbidIP.getSeconds());
        return Result.success();
    }

    @Override
    public boolean exists(String ip) {
        if(StringUtils.isBlank(ip)){
            return false;
        }
        /**缓存*/
        if(redisUtil.hasKey(ConstUtil.IP_EXISTS_CACHE + ip)){
            return true;
        }
        LambdaQueryWrapper<ForbidIP> wrapper = Wrappers.<ForbidIP>lambdaQuery();
        wrapper.eq(ForbidIP::getIp, ip);
        ForbidIP forbidIP = forBidIPMapper.selectOne(wrapper);
        if(Optional.ofNullable(forbidIP).isPresent()){
            /**缓存*/
            redisUtil.set(ConstUtil.IP_EXISTS_CACHE + ip, "1", 1, TimeUnit.MINUTES);
            return true;
        }
        return false;
    }


}
