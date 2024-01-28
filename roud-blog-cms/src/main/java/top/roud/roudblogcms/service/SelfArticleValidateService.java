package top.roud.roudblogcms.service;

import top.roud.roudblogcms.common.result.Result;
import top.roud.roudblogcms.entity.SelfArticle;

/**
 * @author roud
 * @version 1.0.0
 * @describe
 * @date 2023-11-06 1:07
 */
public interface SelfArticleValidateService {
    Result validateSelfArticle(Long articleId, String validateCode);
    String getValidateCodeByArticleId(Long articleId);
    Integer saveSelfArticleValidateCode(SelfArticle selfArticle);
    Integer updateSelfArticleValidateCode(SelfArticle selfArticle);
    Integer delSelfArticleValidateCodeByArticleId(Long articleId);
}
