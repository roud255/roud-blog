package top.roud.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import top.roud.cms.entity.SelfArticle;

/**
 * @author roud
 * @version 1.0.0
 * @describe
 * @date 2023-11-06 1:00
 */
@Repository
public interface SelfArticleMapper extends BaseMapper<SelfArticle> {
    SelfArticle selectSelfArticleByArticleId(Long articleId);
}
