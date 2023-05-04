package top.roud.cms.service;

import top.roud.cms.entity.Comment;

/**
 * @description : TODO
 * @author: guangrui_hu
 * @date: 2023/5/4
 * @version:
 */
public interface ArticleAndCommentService {
    public Integer addComment(Comment comment);
    public Comment findCommentByArticle(Long a_id);
}
