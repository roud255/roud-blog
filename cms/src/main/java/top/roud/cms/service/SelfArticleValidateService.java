package top.roud.cms.service;

import top.roud.cms.common.result.Result;
import top.roud.cms.entity.SelfArticle;

/**
 * @author roud
 * @version 1.0.0
 * @describe
 * @date 2023-11-06 1:07
 */
public interface SelfArticleValidateService {
    public Result validateSelfArticle(Long articleId, String validateCode);
    public String getValidateCodeByArticleId(Long articleId);
    Integer saveSelfArticleValidateCode(SelfArticle selfArticle);

    Integer updateSelfArticleValidateCode(SelfArticle selfArticle);
}
