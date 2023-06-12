package top.roud.cms.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.roud.cms.entity.Article;
import top.roud.cms.entity.Tag;

import java.util.List;

@Repository
public interface ArticleAndTagMapper extends BaseMapper<Article> {
    public void insertArticle(Article article);
    public void insertTag(Tag tag);
    public Article getArticleById(Long id);
    public Tag getTagById(Long id);
    public List<Tag> getTagByArticleId(Long id);
    public void insertArticleAndTag(Long id, Long article_id, Long tag_id);
    public Article getArticleByIdWithTag(Long id);
    public Tag getTagByName(String name);
    public List<Tag> getAllTags();
    public List<Article> getAllArticleWithTag();
    //自定义分页查询
    public Page<Article> selectPage(Page<Article> page, @Param("ew") Wrapper<Article> queryWrapper);
    public void delArticleWithTag(Long id);
    public Integer updateViewsnumAndCommentsnumByArticleId(Long id, Integer viewsnum, Integer commentsnum);
}
