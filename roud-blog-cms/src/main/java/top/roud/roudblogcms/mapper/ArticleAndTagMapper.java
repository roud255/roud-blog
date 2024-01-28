package top.roud.roudblogcms.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.roud.roudblogcms.entity.Article;
import top.roud.roudblogcms.entity.ArticleWithValidateCode;
import top.roud.roudblogcms.entity.Tag;

import java.util.List;


@Repository
public interface ArticleAndTagMapper extends BaseMapper<Article> {
    void insertArticle(Article article);
    void insertTag(Tag tag);
    Article getArticleById(Long id);
    Tag getTagById(Long id);
    List<Tag> getTagByArticleId(Long id);

    void insertArticleAndTag(Long id, Long articleId, Long tagId);
    Article getArticleByIdWithTag(Long id);
    Tag getTagByName(String name);
    List<Tag> getAllTags();
    List<Article> getAllArticleWithTag();
    //自定义分页查询
    Page<Article> selectPage(Page<Article> page, @Param("ew") Wrapper<Article> queryWrapper);
    //分页查询包含专属文章验证码
    Page<ArticleWithValidateCode> selectPageWithValidateCode(Page<ArticleWithValidateCode> page, @Param("ew") Wrapper<ArticleWithValidateCode> queryWrapper);
    Page<Article> selectPageWithoutBody(Page<Article> page, @Param("ew") Wrapper<Article> queryWrapper);
    List<Article> selectPageByTag(String tagname,Integer pagestart,Integer pagecount);
    List<Article> selectPageByTagWithoutBody(String tagname,Integer pagestart,Integer pagecount);
    void delArticleWithTag(Long id);
    Integer updateViewsnumAndCommentsnumByArticleId(Long id, Integer viewsnum, Integer commentsnum);
    Integer updateViewsnumByArticleId(Long id, Integer viewsnum);
    Integer updateCommentsnumByArticleId(Long id, Integer commentsnum);
}
