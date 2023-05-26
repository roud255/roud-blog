package top.roud.cms.mapper;

import org.springframework.stereotype.Repository;
import top.roud.cms.entity.Comment;

import java.util.List;

@Repository
public interface ArticleAndCommentMapper {
    Integer insertComment(Comment comment);
    List<Comment> findAllCommentsByArticle(Long article_id);
    Integer delById(Long id);
    Integer delByArticleId(Long article_id);
}
