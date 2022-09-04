package top.roud.cms.service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.roud.cms.entity.Article;
import top.roud.cms.entity.Tag;

import java.util.List;

public interface ArticleAndTagService {
    public void insertArticle(Article article);
    public void insertTag(Tag tag);
    public Article getArticleById(Long id);
    public Tag getTagById(Long id);
    public void insertArticleAndTag(Long id, Article article, Tag tag);
    public Article getArticleByIdWithTag(Long id);
    public Tag getTagByName(String name);
    public List<Tag> getAllTags();
    public List<Article> getAllArticleWithTag();
    public Page<Article> findPage(Integer pageNum, Integer pageSize);
    public List<Tag> getTagByArticleId(Long id);
}
