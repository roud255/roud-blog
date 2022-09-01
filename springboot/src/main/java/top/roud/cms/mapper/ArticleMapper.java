package top.roud.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.roud.cms.entity.Article;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {
    List<Article> selectArticleByTag(Long tagId);
}
