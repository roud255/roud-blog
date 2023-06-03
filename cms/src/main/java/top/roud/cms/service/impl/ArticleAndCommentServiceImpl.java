package top.roud.cms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.roud.cms.common.result.Result;
import top.roud.cms.entity.Comment;
import top.roud.cms.entity.User;
import top.roud.cms.mapper.ArticleAndCommentMapper;
import top.roud.cms.service.ArticleAndCommentService;

import java.util.List;

/**
 * @description : TODO
 * @author: roud
 * @date: 2023/5/4
 * @version:
 */
@Transactional
@Service
public class ArticleAndCommentServiceImpl implements ArticleAndCommentService {
    @Autowired
    private ArticleAndCommentMapper articleAndCommentMapper;
    @Override
    public Integer addComment(Comment comment) {
        return articleAndCommentMapper.insertComment(comment);
    }

    @Override
    public List<Comment> findCommentByArticle(Long a_id) {
        return articleAndCommentMapper.findAllCommentsByArticle(a_id);
    }

    @Override
    public Page<Comment> findAllComments(Integer pageNum, Integer pageSize, String search) {
        LambdaQueryWrapper<Comment> wrapper = Wrappers.<Comment>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(Comment::getContent, search);
        }
        Page<Comment> commentPage = articleAndCommentMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return commentPage;
    }

    @Override
    public Integer delById(Long id) {
        return articleAndCommentMapper.delById(id);
    }

    @Override
    public Integer delByArticleId(Long article_id) {
        return articleAndCommentMapper.delByArticleId(article_id);
    }
}
