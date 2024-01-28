package top.roud.roudblogcms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.roud.roudblogcms.common.result.Result;
import top.roud.roudblogcms.entity.Comment;

import javax.servlet.http.HttpServletRequest;

/**
 * @description : TODO
 * @author: guangrui_hu
 * @date: 2023/5/4
 * @version:
 */
public interface ArticleAndCommentService {
    Integer addComment(Comment comment);
    Page<Comment> findAllComments(Integer pageNum, Integer pageSize, String search);
    Integer delById(Long articleId, Long id);
    Integer delByArticleId(Long articleId);
    Long findArticleIdById(Long id);
    Result saveComment(String info, String articleId, HttpServletRequest request);
    Result selectCommentsByArticleId(Long id);

    public Integer getCommentsnumByArticleId(Long id);
}
