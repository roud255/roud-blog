package top.roud.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.roud.cms.entity.Comment;
import top.roud.cms.mapper.ArticleAndCommentMapper;
import top.roud.cms.service.ArticleAndCommentService;

/**
 * @description : TODO
 * @author: guangrui_hu
 * @date: 2023/5/4
 * @version:
 */
@Service
public class ArticleAndCommentServiceImpl implements ArticleAndCommentService {
    @Autowired
    private ArticleAndCommentMapper articleAndCommentMapper;
    @Override
    public Integer addComment(Comment comment) {
        return articleAndCommentMapper.insertComment();
    }

    @Override
    public Comment findCommentByArticle(Long a_id) {
        return articleAndCommentMapper.findAllCommentsByArticle(a_id);
    }
}
