package top.roud.roudblogcms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.roud.roudblogcms.common.exception.ServiceException;
import top.roud.roudblogcms.common.result.Result;
import top.roud.roudblogcms.common.result.ResultCode;
import top.roud.roudblogcms.entity.SelfArticle;
import top.roud.roudblogcms.mapper.SelfArticleMapper;
import top.roud.roudblogcms.service.SelfArticleValidateService;

import java.util.Optional;

/**
 * @author roud
 * @version 1.0.0
 * @describe
 * @date 2023-11-06 1:07
 */
@Service
public class SelfArticleValidateServicImpl implements SelfArticleValidateService {
    @Autowired
    private SelfArticleMapper selfArticleMapper;

    private SelfArticle getSelfArticleByArticleId(Long articleId){
        LambdaQueryWrapper<SelfArticle> lqw = Wrappers.<SelfArticle>lambdaQuery();
        lqw.eq(SelfArticle::getArticleId, articleId);
        SelfArticle selfArticle = selfArticleMapper.selectOne(lqw);
        return selfArticle;
    }

    @Override
    public Result validateSelfArticle(Long articleId, String validateCode) {
        SelfArticle selfArticle = getSelfArticleByArticleId(articleId);
        Optional<SelfArticle> op = Optional.ofNullable(selfArticle);
        if(!op.isPresent()){
            return Result.failure(ResultCode.VALIDATEF_FAIL);
        }
        if(StringUtils.equals(validateCode, selfArticle.getValidateCode())){
            return Result.success();
        }
        return Result.failure(ResultCode.VALIDATEF_FAIL);
    }

    @Override
    public String getValidateCodeByArticleId(Long articleId) {
        SelfArticle selfArticle = getSelfArticleByArticleId(articleId);
        return selfArticle.getValidateCode();
    }

    @Override
    public Integer saveSelfArticleValidateCode(SelfArticle selfArticle) {
        if(StringUtils.isBlank(selfArticle.getValidateCode())){
            throw new ServiceException(ResultCode.PARAM_NOT_COMPLETE);
        }
        LambdaQueryWrapper<SelfArticle> lqw = Wrappers.<SelfArticle>lambdaQuery();
        lqw.eq(SelfArticle::getArticleId, selfArticle.getArticleId());
        SelfArticle selfArticleByDB = selfArticleMapper.selectOne(lqw);
        Optional<SelfArticle> op = Optional.ofNullable(selfArticleByDB);
        if(op.isPresent()){
            selfArticleByDB.setValidateCode(selfArticle.getValidateCode());
            return selfArticleMapper.updateById(selfArticleByDB);
        }
        return selfArticleMapper.insert(selfArticle);
    }
    @Override
    public Integer updateSelfArticleValidateCode(SelfArticle selfArticle) {
        SelfArticle sa = getSelfArticleByArticleId(selfArticle.getArticleId());
        if(null == sa){
            return selfArticleMapper.insert(selfArticle);
        }
        sa.setValidateCode(selfArticle.getValidateCode());
        return selfArticleMapper.updateById(sa);
    }

    @Override
    public Integer delSelfArticleValidateCodeByArticleId(Long articleId) {
        LambdaQueryWrapper<SelfArticle> lqw = Wrappers.<SelfArticle>lambdaQuery();
        lqw.eq(SelfArticle::getArticleId, articleId);
        return selfArticleMapper.delete(lqw);
    }
}
