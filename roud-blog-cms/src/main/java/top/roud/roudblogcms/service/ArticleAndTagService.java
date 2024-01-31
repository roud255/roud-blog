package top.roud.roudblogcms.service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.roud.roudblogcms.common.result.Result;
import top.roud.roudblogcms.entity.Article;
import top.roud.roudblogcms.entity.ArticleWithValidateCode;
import top.roud.roudblogcms.entity.Tag;
import top.roud.roudblogcms.service.impl.ArticleAndTagServiceImpl;

import java.util.List;

public interface ArticleAndTagService {
    void insertArticle(Article article);
    Result saveArticle(String info);
    void insertTag(Tag tag);
    Result getArticleById(Long id, String validateCode);
    Tag getTagById(Long id);
    void insertArticleAndTag(Long id, Article article, Tag tag);
    Article getArticleByIdWithTag(Long id);
    Tag getTagByName(String name);
    List<Tag> getAllTags();
    Result delTagById(Long id);
    Result updateTagById(Tag tag);
    Page<ArticleAndTagServiceImpl.TagExt> findTagsPage(Integer pageNum, Integer pageSize, String search);
    List<Article> getAllArticleWithTag();
    Page<ArticleWithValidateCode> findPageWithValidatecode(Integer pageNum, Integer pageSize, String search);
    Result findPageWithoutBody(Integer pageNum, Integer pageSize, String search, String type);
    Page<Article> findPageByTag(Integer pageNum, Integer pageSize, String search);
    Page<Article> findPageByTagWithoutBody(Integer pageNum, Integer pageSize, String search);
    Page<Article> findPageByTitleWithoutBody(Integer pageNum, Integer pageSize, String search);
    List<Tag> getTagByArticleId(Long id);
    void delArticleWithTag(Long id);
    Integer updateArticleById(Article article);
    Integer updateViewsnumAndCommentsnumByArticleId(Long id, Integer...arr);
    Integer getViewsnumByArticleId(Long id);
    Integer getCommentsnumByArticleId(Long id);
}
