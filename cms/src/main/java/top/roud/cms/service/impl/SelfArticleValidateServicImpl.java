package top.roud.cms.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.roud.cms.common.result.Result;
import top.roud.cms.common.result.ResultCode;
import top.roud.cms.entity.SelfArticle;
import top.roud.cms.mapper.SelfArticleMapper;
import top.roud.cms.service.SelfArticleValidateService;

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
    @Override
    public Result validateSelfArticle(Long articleId, String validateCode) {
        SelfArticle selfArticle = selfArticleMapper.selectSelfArticleByArticleId(articleId);
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
        SelfArticle selfArticle = selfArticleMapper.selectSelfArticleByArticleId(articleId);
        return selfArticle.getValidateCode();
    }

    @Override
    public Integer saveSelfArticleValidateCode(SelfArticle selfArticle) {
        return selfArticleMapper.insert(selfArticle);
    }
    @Override
    public Integer updateSelfArticleValidateCode(SelfArticle selfArticle) {
        SelfArticle sa = selfArticleMapper.selectSelfArticleByArticleId(selfArticle.getArticleId());
        if(null == sa){
            return selfArticleMapper.insert(selfArticle);
        }
        sa.setValidateCode(selfArticle.getValidateCode());
        return selfArticleMapper.updateById(sa);
    }
}
