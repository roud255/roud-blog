package top.roud.cms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import top.roud.cms.entity.Article;
import top.roud.cms.entity.Tag;
import top.roud.cms.mapper.ArticleAndTagMapper;
import top.roud.cms.service.ArticleAndTagService;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName: ArticleAndTagServiceImpl
 * @Description:
 * @Author roud
 * @Date 2022/9/3
 * @Version 1.0
 */
@Service
public class ArticleAndTagServiceImpl implements ArticleAndTagService {
    @Resource
    private ArticleAndTagMapper articleAndTagMapper;
    @Override
    public void insertArticle(Article article) {
        articleAndTagMapper.insertArticle(article);
    }

    @Override
    public void insertTag(Tag tag) {
        articleAndTagMapper.insertTag(tag);
    }

    @Override
    public Article getArticleById(Long id) {
        return articleAndTagMapper.getArticleById(id);
    }

    @Override
    public Tag getTagById(Long id) {
        return articleAndTagMapper.getTagById(id);
    }

    @Override
    public void insertArticleAndTag(Long id, Article article, Tag tag) {
        articleAndTagMapper.insertArticleAndTag(id, article.getId(), tag.getId());
    }

    @Override
    public Article getArticleByIdWithTag(Long id) {
        return articleAndTagMapper.getArticleByIdWithTag(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return articleAndTagMapper.getTagByName(name);
    }

    @Override
    public List<Tag> getAllTags() {
        return articleAndTagMapper.getAllTags();
    }

    @Override
    public List<Article> getAllArticleWithTag() {
        return articleAndTagMapper.getAllArticleWithTag();
    }
    /**
    分页查询+表关联，如果直接用关联+分页，总条数变多。只能拆分，先分页查article，再添加tags
    */
    @Override
    public Page<Article> findPage(Integer pageNum, Integer pageSize) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        Page<Article> result = articleAndTagMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        List<Article> records = result.getRecords();
        if(0 == records.size()){
            return result;
        }
        List<Article> records_new = new LinkedList<>();
        for(Article article : records){
            List<Tag> tags = articleAndTagMapper.getTagByArticleId(article.getId());
            if(0 == tags.size()){
                continue;
            }
            article.setTags(tags);
            records_new.add(article);
        }
        result.setRecords(records_new);
        return result;
    }

    @Override
    public Page<Article> findPage_second(Integer pageNum, Integer pageSize, String search) {
        LambdaQueryWrapper<Article> wrapper = Wrappers.<Article>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(Article::getTitle, search);
        }
        Page<Article> result = articleAndTagMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        List<Article> records = result.getRecords();
        if(0 == records.size()){
            return result;
        }
        List<Article> records_new = new LinkedList<>();
        for(Article article : records){
            List<Tag> tags = articleAndTagMapper.getTagByArticleId(article.getId());
            if(0 == tags.size()){
                continue;
            }
            article.setTags(tags);
            records_new.add(article);
        }
        result.setRecords(records_new);
        return result;
    }


    @Override
    public List<Tag> getTagByArticleId(Long id) {
        return articleAndTagMapper.getTagByArticleId(id);
    }

    @Override
    public void delArticleWithTag(Long id) {
        articleAndTagMapper.delArticleWithTag(id);
    }
}
