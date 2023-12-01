package top.roud.cms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.roud.cms.common.result.Result;
import top.roud.cms.entity.ForbidIP;
import top.roud.cms.mapper.ForBidIPMapper;
import top.roud.cms.service.ForBidIPService;

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
    @Override
    public Result save(ForbidIP forbidIP) {
        int insert = forBidIPMapper.insert(forbidIP);
        if(insert != 1){
            return Result.failure("保存失败！");
        }
        return Result.success();
    }

    @Override
    public ForbidIP selectForBidIPByIp(String ip) {
        return forBidIPMapper.selectForBidIPByIp(ip);
    }

    @Override
    public Result del(Long id) {
        int i = forBidIPMapper.deleteById(id);
        if(i != 1){
            return Result.failure("删除失败！");
        }
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
        return Result.success();
    }


}
